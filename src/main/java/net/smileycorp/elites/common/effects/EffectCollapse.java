package net.smileycorp.elites.common.effects;

import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

public class EffectCollapse extends MobEffect {

    protected EffectCollapse() {
        super(MobEffectCategory.HARMFUL, 0xFF4B4A);
    }
    
    @Override
    public List<ItemStack> getCurativeItems() {
        return Lists.newArrayList();
    }
    
    public static void apply(LivingEntity entity, float amount) {
        int duration = 60;
        int amplifier = (int) Math.floor(amount * 4);
        if (entity.hasEffect(ElitesEffects.COLLAPSE.get())) {
            MobEffectInstance current = entity.getEffect(ElitesEffects.COLLAPSE.get());
            duration = current.getDuration();
            amplifier = Mth.clamp(amplifier + current.getAmplifier(), 9, 127);
        }
        entity.addEffect(new MobEffectInstance(ElitesEffects.COLLAPSE.get(), duration, amplifier));
    }
    
}
