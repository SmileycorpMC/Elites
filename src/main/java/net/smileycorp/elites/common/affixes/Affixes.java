package net.smileycorp.elites.common.affixes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.smileycorp.elites.common.Constants;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class Affixes {
    
    public static final Map<ResourceLocation, Affix> AFFIXES = Maps.newLinkedHashMap();
    
    public static final Affix BLAZING = register(Constants.loc("blazing"), new AffixBlazing());
    public static final Affix OVERLOADING = register(Constants.loc("overloading"), new AffixOverloading());
    public static final Affix GLACIAL = register(Constants.loc("glacial"), new AffixGlacial());
    public static final Affix MENDING = register(Constants.loc("mending"), new AffixMending());
    public static final Affix MALACHITE = register(Constants.loc("malachite"), new AffixMalachite());
    public static final Affix CELESTINE = register(Constants.loc("celestine"), new AffixCelestine());
    public static final Affix PERFECTED = register(Constants.loc("perfected"), new AffixPerfected());
    public static final Affix VOIDTOUCHED = register(Constants.loc("voidtouched"), new AffixVoidtouched());
    
    public static Affix register(ResourceLocation name, Affix affix) {
        return AFFIXES.put(name, affix.setRegistryName(name));
    }
    
    public static Optional<Affix> getAffix(ResourceLocation affix) {
        return AFFIXES.containsKey(affix) ? Optional.of(AFFIXES.get(affix)) : Optional.empty();
    }
    
    public static Optional<Affix> getRandomAffix(LivingEntity entity) {
        ArrayList<Affix> values = Lists.newArrayList(AFFIXES.values());
        return Optional.of(values.get(entity.getRandom().nextInt(values.size())));
    }
    
}
