package net.smileycorp.elites.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;

public interface AffixRenderer {
    <T extends LivingEntity, M extends EntityModel<T>> void render(T entity, M model, PoseStack poseStack, MultiBufferSource buffers, int packedLight, float partialTick);

}
