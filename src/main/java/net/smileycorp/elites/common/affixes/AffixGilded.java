package net.smileycorp.elites.common.affixes;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class AffixGilded extends Affix {
    
    @Override
    public void dealDamage(LivingEntity entity, LivingEntity target, float amount) {
        if (target instanceof Player) ((Player)entity).giveExperiencePoints((int) (10 * amount * amount));
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
