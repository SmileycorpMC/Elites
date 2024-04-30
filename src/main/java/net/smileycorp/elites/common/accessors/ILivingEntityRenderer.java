package net.smileycorp.elites.common.accessors;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.LivingEntity;

public interface ILivingEntityRenderer {
    
    void applyRotations(LivingEntity entity, PoseStack poseStack, float bob, float rotation, float partialTick);
    
}
