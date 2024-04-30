package net.smileycorp.elites.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import net.smileycorp.elites.common.affixes.Affix;
import net.smileycorp.elites.common.affixes.AffixHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(Slime.class)
public abstract class MixinSlime extends Mob {
    
    protected MixinSlime(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }
    
    @WrapOperation(method = "remove", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"))
    public boolean addFreshEntity(Level level, Entity slime, Operation<Boolean> original) {
        boolean flag = original.call(level, slime);
        Optional<Affix> affix = Affix.getAffix(this);
        if (!affix.isPresent()) return flag;
        LazyOptional<AffixHolder> optional = slime.getCapability(AffixHolder.CAPABILITY);
        if (!optional.isPresent()) return flag;
        optional.orElse(null).setAffix(affix.get(), (LivingEntity)slime);
        return flag;
    }
    
}
