package net.smileycorp.elites.common.affixes;

import com.google.common.collect.Maps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.smileycorp.elites.common.effects.ElitesEffects;

import java.util.Map;
import java.util.UUID;

public class AffixPerfected extends Affix {
    
    private static final UUID SPEED_UUID = UUID.fromString("42e2f440-b79f-429c-81c6-c284937508bf");
    private static final UUID HEALTH_UUID = UUID.fromString("3e9553c7-5846-4c7d-a416-b2ea2c6375f6");
    
    private final Map<Attribute, AttributeModifier> modifiers;
    
    public AffixPerfected() {
        modifiers = Maps.newHashMap();
        modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(SPEED_UUID, "perfected-modifier", 1.3, AttributeModifier.Operation.MULTIPLY_BASE));
        modifiers.put(Attributes.MAX_HEALTH, new AttributeModifier(HEALTH_UUID, "perfected-modifier", 1.25, AttributeModifier.Operation.MULTIPLY_BASE));
    }
    
    @Override
    public void tick(LivingEntity entity) {
        //TODO: add bomb spawning
        CompoundTag tag = Affix.getAffixStorage(entity);
        if (tag == null) return;
        if (tag.contains("hurtTime") && tag.getInt("hurtTime") > 0) tag.putInt("hurtTime", tag.getInt("hurtTime") - 1);
        else if (entity.getHealth() < entity.getMaxHealth()) entity.heal(1);
    }
    
    @Override
    public void dealDamage(LivingEntity entity, LivingEntity target, float amount) {
        target.addEffect(new MobEffectInstance(ElitesEffects.CRIPPLE.get(), 30));
    }
    
    @Override
    public float hurt(LivingEntity entity, DamageSource source, float amount) {
        CompoundTag tag = Affix.getAffixStorage(entity);
        if (tag != null) tag.putInt("hurtTime", 140);
        return amount;
    }
    
    @Override
    public Map<Attribute, AttributeModifier> getAttributeModifiers() {
        return modifiers;
    }
    
    @Override
    public float damageMult() {
        return 2;
    }
    
    @Override
    public float healthMult() {
        return 2f;
    }
    
    @Override
    public int getColour() {
        return 0x23A9F2;
    }
    
}
