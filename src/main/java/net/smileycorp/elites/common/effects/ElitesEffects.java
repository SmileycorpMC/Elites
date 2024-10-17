package net.smileycorp.elites.common.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.smileycorp.elites.common.Constants;

public class ElitesEffects {
    
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Constants.MODID);
    
    public static final RegistryObject<MobEffect> FROZEN = EFFECTS.register("frozen", EffectFrozen::new);
    
    public static final RegistryObject<MobEffect> HEALING_DISABLED = EFFECTS.register("healing_disabled", EffectHealingDisabled::new);
    
    public static final RegistryObject<MobEffect> CRIPPLE = EFFECTS.register("cripple", EffectCripple::new);

    public static final RegistryObject<MobEffect> TWISTED_CORRUPTION = EFFECTS.register("twisted_corruption", EffectTwistedCorruption::new);
    
    public static final RegistryObject<MobEffect> COLLAPSE = EFFECTS.register("collapse", EffectCollapse::new);
    
}
