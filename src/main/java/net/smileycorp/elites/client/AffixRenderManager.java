package net.smileycorp.elites.client;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.smileycorp.elites.common.Constants;
import net.smileycorp.elites.common.accessors.ILivingEntityRenderer;
import net.smileycorp.elites.common.affixes.Affix;

import java.awt.*;
import java.util.Map;

public class AffixRenderManager {
    
    private static final ResourceLocation OVERLAY = Constants.loc("textures/entity/affix_overlay.png");
    public static final AffixRenderManager INSTANCE = new AffixRenderManager();
    
    private final Map<Affix, AffixRenderer> renderers = Maps.newHashMap();
    
    public <T extends LivingEntity, M extends EntityModel<T>> void render(Affix affix, T entity, LivingEntityRenderer<T, M> renderer, PoseStack poseStack, MultiBufferSource buffers, int packedLight, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        if (entity.isInvisibleTo(minecraft.player)) return;
        M model = renderer.getModel();
        VertexConsumer vertexconsumer = buffers.getBuffer(RenderType.entityTranslucent(OVERLAY));
        Color colour = new Color(affix.getColour());
        poseStack.pushPose();
        ((ILivingEntityRenderer)renderer).applyRotations(entity, poseStack, renderer.getBob(entity, partialTick), Mth.rotLerp(partialTick, entity.yBodyRotO, entity.yBodyRot), partialTick);
        poseStack.scale(1.001f, 1.001f, 1.001f);
        model.renderToBuffer(poseStack, vertexconsumer, packedLight, 0, (float)colour.getRed() / 255f, (float)colour.getGreen() / 255f, (float)colour.getBlue() / 255f, 0.5f);
        if (renderers.containsKey(affix)) renderers.get(model).render(entity, model, poseStack, buffers, packedLight, partialTick);
        poseStack.popPose();
    }
    
    public void registerRenderer(Affix affix, AffixRenderer model) {
        renderers.put(affix, model);
    }
    
}
