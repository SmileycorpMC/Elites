package net.smileycorp.elites.common;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;

public class ElitesDamageSources {
    
    public static final ResourceKey<DamageType> COLLAPSE = ResourceKey.create(Registries.DAMAGE_TYPE, Constants.loc("collapse"));
    
    /*public static DamageSource collapse(LivingEntity entity) {
        return entity.damageSources().source(COLLAPSE);
    }*/
    
    
}
