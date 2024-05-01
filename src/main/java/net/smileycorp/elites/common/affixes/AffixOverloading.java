package net.smileycorp.elites.common.affixes;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class AffixOverloading extends Affix {
    
    @Override
    public void tick(LivingEntity entity) {
        CompoundTag tag = Affix.getAffixStorage(entity);
        if (tag == null) return;
        if (!tag.contains("shields")) tag.putFloat("shields", entity.getMaxHealth());
        if (tag.contains("hurtTime") && tag.getInt("hurtTime") > 0) tag.putInt("hurtTime", tag.getInt("hurtTime") - 1);
        else {
            float shields = tag.getFloat("shields");
            if (shields < entity.getMaxHealth() * 0.5f) {
                tag.putFloat("shields", Math.min(shields + 1, entity.getMaxHealth() * 0.5f));
                entity.heal(1);
            }
        }
    }
    
    @Override
    public void dealDamage(LivingEntity entity, LivingEntity target, float amount) {
    
    }
    
    @Override
    public float hurt(LivingEntity entity, DamageSource source, float amount) {
        CompoundTag tag = Affix.getAffixStorage(entity);
        if (tag == null) return amount;
        tag.putInt("hurtTime", 140);
        if (!tag.contains("shields")) return amount;
        float shields = tag.getFloat("shields");
        if (shields > 0) {
            shields = shields - amount;
            tag.putFloat("shields", Math.max(0f, shields));
        }
        return amount;
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
        return 0x0C3457;
    }
    
}
