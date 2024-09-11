package net.smileycorp.elites.common.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.smileycorp.elites.common.Constants;

import java.util.UUID;

public class EffectCripple extends MobEffect {

    private final UUID SPEED_MOD_UUID = UUID.fromString("b3165c75-3646-46ee-8d31-4f89e7a03485");
    private final String SPEED_MOD_NAME = Constants.name("Cripple");
    private final AttributeModifier SPEED_MOD = new AttributeModifier(SPEED_MOD_NAME, -0.5, AttributeModifier.Operation.MULTIPLY_TOTAL);

    protected EffectCripple() {
        super(MobEffectCategory.HARMFUL, 0x84D5EC);
    }

    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeMap map, int amplifier) {
        AttributeInstance attribute = map.getInstance(Attributes.MOVEMENT_SPEED);
        if (attribute == null) return;
        attribute.removeModifier(SPEED_MOD_UUID);
        attribute.addPermanentModifier(SPEED_MOD);
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entity, AttributeMap map, int amplifier) {
        AttributeInstance attribute = map.getInstance(Attributes.MOVEMENT_SPEED);
        if (attribute != null) attribute.removeModifier(SPEED_MOD_UUID);
    }
    
}
