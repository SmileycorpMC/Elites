package net.smileycorp.elites.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.smileycorp.elites.common.Constants;
import net.smileycorp.elites.common.affixes.Affix;
import net.smileycorp.elites.common.affixes.AffixHolder;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = Constants.MODID, value = Dist.CLIENT)
public class ClientHandler {
    
    @SubscribeEvent
    public <T extends LivingEntity, M extends EntityModel<T>> void preRenderLiving(RenderLivingEvent.Pre<T, M> event) {
        T entity = (T) event.getEntity();
        Optional<Affix> optional = Affix.getAffix(entity);
        if (!optional.isPresent()) return;
        int colour = optional.get().getColour();
        float r = (float)((colour >> 16) & 0xFF) / 255f;
        float g = (float) ((colour >> 8) & 0xFF) / 255f;
        float b = (float)(colour & 0xFF) / 255f;
        RenderSystem.setShaderColor(r, g, b, 1);
    }
    
    @SubscribeEvent
    public <T extends LivingEntity, M extends EntityModel<T>> void postRenderLiving(RenderLivingEvent.Post<T, M> event) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        T entity = (T) event.getEntity();
        Optional<Affix> optional = Affix.getAffix(entity);
        if (!optional.isPresent()) return;
        AffixRenderManager.INSTANCE.render(optional.get(), entity, event.getRenderer(), event.getPoseStack(), event.getMultiBufferSource(),
                event.getPackedLight(), event.getPartialTick());
    }
    
    public static void syncAffix(Affix affix, int id) {
        if (affix == null) return;
        Entity entity = Minecraft.getInstance().level.getEntity(id);
        LazyOptional<AffixHolder> optional = entity.getCapability(AffixHolder.CAPABILITY);
        if (!optional.isPresent()) return;
        optional.orElseGet(null).setAffix(affix, null);
    }
    
}
