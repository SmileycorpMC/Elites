package net.smileycorp.elites.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.Team;
import net.smileycorp.elites.common.affixes.Affix;
import net.smileycorp.elites.common.affixes.Affixes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Entity.class)
public abstract class MixinEntity {
    
    @Shadow private Level level;
    
    @Inject(at=@At("HEAD"), method = "getDisplayName", cancellable = true)
    public void getTeam(CallbackInfoReturnable<Team> callback) {
        if (((Object)this) instanceof LivingEntity && Affix.hasAffix((LivingEntity)(Object)this, Affixes.VOIDTOUCHED))
            callback.setReturnValue(level.getScoreboard().getPlayerTeam("Void"));
    }
    
    @Inject(at=@At("TAIL"), method = "getName", cancellable = true)
    public void getName(CallbackInfoReturnable<Component> callback) {
        if (!(((Object)this) instanceof LivingEntity)) return;
        Optional<Affix> affix = Affix.getAffix((LivingEntity)(Object)this);
        if (!affix.isPresent()) return;
        callback.setReturnValue(affix.get().getName().append(" ").append(callback.getReturnValue()));
    }
    
}