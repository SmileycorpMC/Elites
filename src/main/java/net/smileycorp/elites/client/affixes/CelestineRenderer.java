package net.smileycorp.elites.client.affixes;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.textures.UnitTextureAtlasSprite;
import net.smileycorp.atlas.api.client.RenderingUtils;
import net.smileycorp.elites.common.ElitesLogger;
import org.joml.Matrix4f;

import java.awt.*;

public class CelestineRenderer implements AffixRenderer{
    
    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(T entity, M model, PoseStack poseStack, MultiBufferSource buffers, int packedLight, float partialTick) {
        RenderingUtils.drawSphere(buffers.getBuffer(RenderType.entityGlint()), poseStack, entity.position().subtract(Minecraft.getInstance().cameraEntity.position()), 30, new Color(87, 177, 152, 100), UnitTextureAtlasSprite.INSTANCE);
    }
    
    public static void drawSphere(PoseStack poseStack, Vec3 center, float radius, Color color) {
        ElitesLogger.logInfo(center);
        BufferBuilder buffer = Tesselator.getInstance().getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        poseStack.pushPose();
        Matrix4f matrix4f = poseStack.last().pose();
        buffer.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
        buffer.vertex(matrix4f, 0.0F, radius, 0.0F).color(color.getRed(), color.getGreen(), color.getGreen(), color.getAlpha()).endVertex();
        for(int j = 0; j <= 16; ++j) {
            float f7 = (float)j * ((float)Math.PI * 2F) / 16.0F;
            float f8 = Mth.sin(f7);
            float f9 = Mth.cos(f7);
            buffer.vertex(matrix4f, (float) (center.x + f8 * 120.0F), (float) (center.y + f9 * 120.0F), (float) (center.z -f9 * 40.0F * radius)).color(color.getGreen(), color.getGreen(), color.getAlpha(), 0.0F).endVertex();
        }
        BufferUploader.drawWithShader(buffer.end());
        poseStack.popPose();
    }
    
}
