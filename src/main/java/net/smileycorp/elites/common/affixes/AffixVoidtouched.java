package net.smileycorp.elites.common.affixes;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.smileycorp.elites.common.effects.EffectCollapse;

public class AffixVoidtouched extends Affix {
    
    @Override
    public void tick(LivingEntity entity) {
        CompoundTag tag = Affix.getAffixStorage(entity);
        if (tag == null) return;
        if (tag.contains("hurtTime") && tag.getInt("hurtTime") > 0) tag.putInt("hurtTime", tag.getInt("hurtTime") - 1);
        else if (entity.getHealth() < entity.getMaxHealth()) entity.heal(1);
    }
    
    @Override
    public void dealDamage(LivingEntity entity, LivingEntity target, float amount) {
        EffectCollapse.apply(entity, amount);
    }
    
    @Override
    public float hurt(LivingEntity entity, DamageSource source, float amount) {
        CompoundTag tag = Affix.getAffixStorage(entity);
        if (tag != null) tag.putInt("hurtTime", 140);
        return amount;
    }
    
    @Override
    public void die(LivingEntity entity) {
        //TODO: Spawn Void Infestor
    }
    
    @Override
    public float damageMult() {
        return 0.7f;
    }
    
    @Override
    public float healthMult() {
        return 1.5f;
    }
    
    @Override
    public int getColour() {
        return 0x8D02F1;
    }
    
}
