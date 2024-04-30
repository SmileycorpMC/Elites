package net.smileycorp.elites.common.affixes;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;

public class AffixBlazing extends Affix {
    
    @Override
    public void tick(LivingEntity entity) {
        if (entity.tickCount % 10 == 0 && entity.level().getBlockState(entity.blockPosition()).isAir())
            entity.level().setBlock(entity.blockPosition(), Blocks.FIRE.defaultBlockState(), 3);
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
