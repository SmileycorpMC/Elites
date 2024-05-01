package net.smileycorp.elites.mixin;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.smileycorp.elites.common.effects.ElitesEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEffectInstance.class)
public class MixinMobEffectInstance {
    
    @Shadow
    private MobEffect effect;
    
    @Shadow
    private int duration;
    
    
    @Inject(at=@At("HEAD"), method = "tick", cancellable = true)
    public void tick(LivingEntity entity, Runnable onUpdate, CallbackInfoReturnable<Boolean> callback) {
        if (duration <= 1 && effect == ElitesEffects.COLLAPSE.get()) {
            //entity.hurt(ElitesDamageSources.collapse(entity), amplifier);
            callback.setReturnValue(false);
        }
    }
    
}