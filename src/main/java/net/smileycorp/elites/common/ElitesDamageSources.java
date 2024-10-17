package net.smileycorp.elites.common;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;

public class ElitesDamageSources {
    
    public static final ResourceKey<DamageType> COLLAPSE = ResourceKey.create(Registries.DAMAGE_TYPE, Constants.loc("collapse"));
    public static final ResourceKey<DamageType> TWISTED = ResourceKey.create(Registries.DAMAGE_TYPE, Constants.loc("twisted"));
    
    public static DamageSource collapse(LivingEntity entity) {
        return new DamageSource(entity.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(COLLAPSE));
    }
    
    public static DamageSource twisted(LivingEntity entity, LivingEntity attacker) {
        return new DamageSource(entity.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(TWISTED), attacker);
    }
    
}
