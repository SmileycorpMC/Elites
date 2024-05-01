package net.smileycorp.elites.common.affixes;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.PacketDistributor;
import net.smileycorp.elites.common.network.PacketHandler;
import net.smileycorp.elites.common.network.SyncAffixMessage;

import java.util.Optional;
import java.util.UUID;

public interface AffixHolder {
    
    Capability<AffixHolder> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    
    Optional<Affix> getAffix();
    
    CompoundTag getAffixStorage();
    
    void setAffix(Affix affix, LivingEntity entity);
    
    CompoundTag save();
    
    void load(CompoundTag tag);
    
    boolean hasAffix();
    
    class Impl implements AffixHolder {
    
        private static final UUID HEALTH_UUID = UUID.fromString("c917d628-e0e2-4b6b-88f5-98d372b14dba");
        private static final UUID DAMAGE_UUID = UUID.fromString("8ac8e795-743f-4329-aec8-96f24b8366a1");
        
        private CompoundTag storage = new CompoundTag();
        private Optional<Affix> affix = Optional.empty();
    
        @Override
        public Optional<Affix> getAffix() {
            return affix;
        }
    
        @Override
        public CompoundTag getAffixStorage() {
            return storage;
        }
    
        @Override
        public void setAffix(Affix affix, LivingEntity entity) {
            if (entity == null) {
                this.affix = affix == null ? Optional.empty() : Optional.of(affix);
                return;
            }
            if (this.affix.isPresent()) this.affix.get().reset(entity);
            AttributeInstance health = entity.getAttribute(Attributes.MAX_HEALTH);
            health.removeModifier(HEALTH_UUID);
            health.addPermanentModifier(new AttributeModifier(HEALTH_UUID, "affix-modifier",
                    affix.healthMult(), AttributeModifier.Operation.MULTIPLY_BASE));
            AttributeInstance damage = entity.getAttribute(Attributes.ATTACK_DAMAGE);
            damage.removeModifier(DAMAGE_UUID);
            damage.addPermanentModifier(new AttributeModifier(DAMAGE_UUID, "affix-modifier",
                    affix.damageMult(), AttributeModifier.Operation.MULTIPLY_BASE));
            affix.applyModifiers(entity);
            entity.heal(entity.getMaxHealth());
            PacketHandler.send(PacketDistributor.TRACKING_CHUNK.with(()-> entity.level().getChunkAt(entity.getOnPos())),
                    new SyncAffixMessage(affix, entity));
            this.affix = affix == null ? Optional.empty() : Optional.of(affix);
        }
    
        @Override
        public CompoundTag save() {
            CompoundTag tag = new CompoundTag();
            if (affix.isPresent()) tag.putString("affix", affix.get().getRegistryName().toString());
            if (storage != null &! storage.isEmpty()) tag.put("storage", storage);
            return tag;
        }
    
        @Override
        public void load(CompoundTag tag) {
            if (tag.contains("affix")) affix = Affixes.getAffix(new ResourceLocation(tag.getString("affix")));
            if (tag.contains("storage")) storage = tag.getCompound("storage");
        }
    
        @Override
        public boolean hasAffix() {
            return affix.isPresent();
        }
    
    }
    
    class Provider implements ICapabilitySerializable<CompoundTag> {
        
        protected AffixHolder impl = new Impl();
        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction facing) {
            return cap == CAPABILITY ? LazyOptional.of(() -> impl).cast() : LazyOptional.empty();
        }
        
        @Override
        public CompoundTag serializeNBT() {
            return impl.save();
        }
        
        @Override
        public void deserializeNBT(CompoundTag  nbt) {
            impl.load(nbt);
        }
        
    }
    
}
