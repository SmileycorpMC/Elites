package net.smileycorp.elites.common.affixes;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;

public class AffixGilded extends Affix {
    
    @Override
    public void tick(LivingEntity entity) {
        CompoundTag tag = Affix.getAffixStorage(entity);
        if (tag == null) return;
        if (!tag.contains("barrier")) {
            tag.putFloat("barrier", entity.getMaxHealth());
        }
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
    public float damageMult() {
        return 3;
    }
    
    @Override
    public float healthMult() {
        return 6;
    }
    
    @Override
    public int getColour() {
        return 0xFFCD2C;
    }
    
}
