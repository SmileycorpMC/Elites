package net.smileycorp.elites.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.smileycorp.elites.common.accessors.ILivingEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LivingEntityRenderer.class)
public abstract class MixinLivingEntityRenderer implements ILivingEntityRenderer {
    
    @Shadow public abstract void setupRotations(LivingEntity p_115317_, PoseStack p_115318_, float p_115319_, float p_115320_, float p_115321_);
    
    @Override
    public void applyRotations(LivingEntity entity, PoseStack poseStack, float bob, float rotation, float partialTick) {
        setupRotations(entity, poseStack, bob, rotation, partialTick);
    }
}