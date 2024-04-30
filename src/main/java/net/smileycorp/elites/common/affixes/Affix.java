package net.smileycorp.elites.common.affixes;

import com.google.common.collect.Maps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.util.LazyOptional;

import java.util.Map;
import java.util.Optional;

public abstract class Affix {
    
    private ResourceLocation name;
    
    public void tick(LivingEntity entity) {}
    
    public void dealDamage(LivingEntity entity, LivingEntity target, float amount) {}
    
    public void die(LivingEntity entity) {}
    
    public float hurt(LivingEntity entity, DamageSource source, float amount) {
        return amount;
    }
    
    public Map<Attribute, AttributeModifier> getAttributeModifiers() {
        return Maps.newHashMap();
    }
    
    public abstract float damageMult();
    
    public abstract float healthMult();
    
    public abstract int getColour();
    
    public Component getName() {
        return Component.translatable("affix." + name.getNamespace() + "." + name.getPath());
    }
    
    public final Affix setRegistryName(ResourceLocation name) {
        this.name = name;
        return this;
    }
    
    public static boolean hasAffix(LivingEntity entity, Affix affix) {
        Optional<Affix> optional = getAffix(entity);
        return optional.isPresent() && optional.get().equals(affix);
    }
    
    public final ResourceLocation getRegistryName() {
        return name;
    }
    
    public static Optional<Affix> getAffix(LivingEntity entity) {
        LazyOptional<AffixHolder> optional = entity.getCapability(AffixHolder.CAPABILITY);
        return optional.isPresent() ? optional.orElseGet(null).getAffix() : Optional.empty();
    }
    
    public static CompoundTag getAffixStorage(LivingEntity entity) {
        LazyOptional<AffixHolder> optional = entity.getCapability(AffixHolder.CAPABILITY);
        return optional.isPresent() ? optional.orElseGet(null).getAffixStorage() : null;
    }
    
}
