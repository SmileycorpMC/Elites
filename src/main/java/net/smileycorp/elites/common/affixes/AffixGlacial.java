package net.smileycorp.elites.common.affixes;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class AffixGlacial extends Affix {
    
    @Override
    public void dealDamage(LivingEntity entity, LivingEntity target, float amount) {
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 4));
    }
    
    @Override
    public void die(LivingEntity entity) {
        //TODO:ADD ICE BOMB SPAWNING
    }
    
    @Override
    public float hurt(LivingEntity entity, DamageSource source, float amount) {
        return source.is(DamageTypeTags.IS_FREEZING) ? 0 : amount;
    }
    
    @Override
    public float damageMult() {
        return 2;
    }
    
    @Override
    public float healthMult() {
        return 4;
    }
    
    @Override
    public int getColour() {
        return 0x73A1B8;
    }
    
}
