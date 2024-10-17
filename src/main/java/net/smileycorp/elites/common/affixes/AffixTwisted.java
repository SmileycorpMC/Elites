package net.smileycorp.elites.common.affixes;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.smileycorp.elites.common.effects.ElitesEffects;

public class AffixTwisted extends Affix {

    @Override
    public void tick(LivingEntity entity) {
        if (entity.tickCount % 20 != 0) return;
        Vec3 pos = entity.position();
        AABB aabb = new AABB(pos.add(30, 30, 30), pos.add(-30, -30, -30));
        entity.level().getEntitiesOfClass(LivingEntity.class, aabb, e-> canBuff(entity, e)).forEach(e ->
                e.addEffect(new MobEffectInstance(ElitesEffects.TWISTED_CORRUPTION.get(), 40, 0, true, true), entity));
    }

    private boolean canBuff(LivingEntity entity, LivingEntity target) {
        if (entity.equals(target)) return false;
        if (Affix.hasAffix(target, Affixes.TWISTED)) return false;
        if (target == null || entity.distanceToSqr(target) > 900) return false;
        if (target instanceof OwnableEntity && entity.equals(((OwnableEntity) target).getOwner())) return true;
        if (entity.getTeam() != null && entity.getTeam().equals(target.getTeam())) return true;
        if (entity instanceof Enemy &! (target instanceof Enemy)) return false;
        if (target instanceof Enemy &! (entity instanceof Enemy)) return false;
        return true;
    }
    
    @Override
    public float damageMult() {
        return 10;
    }
    
    @Override
    public float healthMult() {
        return 13;
    }
    
    @Override
    public int getColour() {
        return 0x004EEF;
    }
    
}
