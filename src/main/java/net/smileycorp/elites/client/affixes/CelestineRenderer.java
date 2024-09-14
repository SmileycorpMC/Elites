package net.smileycorp.elites.client.affixes;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
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
    public <T extends LivingEntity, M extends EntityModel<T>> void render(T entity, M model, EntityRenderer<T> renderer, PoseStack poseStack, MultiBufferSource buffers, int packedLight, float partialTick) {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        Minecraft mc = Minecraft.getInstance();
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderSystem.depthMask(Minecraft.useShaderTransparency());
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();
        PoseStack.Pose pos = posestack.last();
        RenderSystem.applyModelViewMatrix();
        RenderSystem.polygonOffset(-3.0F, -3.0F);
        RenderSystem.enablePolygonOffset();
        RenderSystem.disableCull();
        Vec3 camera = mc.gameRenderer.getMainCamera().getPosition();
        Vec3 vec3 = renderer.getRenderOffset(entity, partialTick);
        double x = Mth.lerp(partialTick, entity.xOld, entity.getX()) + vec3.x();
        double y = Mth.lerp(partialTick, entity.yOld, entity.getY()) + vec3.y();
        double z = Mth.lerp(partialTick, entity.zOld, entity.getZ()) + vec3.z();
        //poseStack.translate(d2, d3, d0);
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        RenderingUtils.drawSphere(bufferbuilder, poseStack, new Vec3(x-camera.x, y-camera.y, z-camera.z), 30, new Color(87, 177, 152, 25), UnitTextureAtlasSprite.INSTANCE);
        BufferUploader.drawWithShader(bufferbuilder.end());
        RenderSystem.enableCull();
        RenderSystem.polygonOffset(0.0F, 0.0F);
        RenderSystem.disablePolygonOffset();
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
        RenderSystem.depthMask(true);
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
