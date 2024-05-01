package net.smileycorp.elites.common.affixes;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class AffixBlazing extends Affix {
    
    @Override
    public void tick(LivingEntity entity) {
        //TODO: add fire trail
    }
    
    @Override
    public void dealDamage(LivingEntity entity, LivingEntity target, float amount) {
        target.setRemainingFireTicks((int)(amount * 10f));
    }
    
    @Override
    public float hurt(LivingEntity entity, DamageSource source, float amount) {
        return source.is(DamageTypeTags.IS_FIRE) ? 0 : amount;
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
        return 0x510E17;
    }
    
}
