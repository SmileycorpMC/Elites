package net.smileycorp.elites.common.affixes;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.smileycorp.elites.common.effects.ElitesEffects;

public class AffixMalachite extends Affix {
    
    @Override
    public void tick(LivingEntity entity) {
        //TODO:ADD URCHIN SPAWNING
    }
    
    @Override
    public void dealDamage(LivingEntity entity, LivingEntity target, float amount) {
        target.addEffect(new MobEffectInstance(ElitesEffects.HEALING_DISABLED.get(), 160), entity);
    }
    
    @Override
    public void die(LivingEntity entity) {
        //TODO:ADD URCHIN SPAWNING
    }
    
    @Override
    public float damageMult() {
        return 6;
    }
    
    @Override
    public float healthMult() {
        return 18;
    }
    
    @Override
    public int getColour() {
        return 0x123F1B;
    }
    
}
