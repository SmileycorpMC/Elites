package net.smileycorp.elites.common.affixes;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class AffixMending extends Affix {
    
    @Override
    public void tick(LivingEntity entity) {
        if (entity.tickCount % 5 != 0) return;
        Vec3 pos = entity.position();
        AABB aabb = new AABB(pos.add(30, 30, 30), pos.add(-30, -30, -30));
        LivingEntity target = entity.level().getNearestEntity(entity.level().getEntitiesOfClass(LivingEntity.class, aabb, e-> canHeal(entity, e)),
                TargetingConditions.DEFAULT, entity, entity.position().x, entity.position().y, entity.position().z);
        if (target == null) return;
        AttributeInstance damage = entity.getAttribute(Attributes.ATTACK_DAMAGE);
        if (damage == null) return;
        target.heal((float) damage.getValue() * 0.5f);
    }
    
    private boolean canHeal(LivingEntity entity, LivingEntity target) {
        if (target == null || entity.distanceToSqr(target) > 900) return false;
        if (hasAffix(target, Affixes.MENDING)) return false;
        if (target instanceof OwnableEntity && entity.equals(((OwnableEntity) target).getOwner())) return true;
        if (entity.getTeam() != null && entity.getTeam().equals(target.getTeam())) return true;
        if (entity instanceof Enemy &! (target instanceof Enemy)) return false;
        if (target instanceof Enemy &! (entity instanceof Enemy)) return false;
        return true;
    }
    
    @Override
    public void die(LivingEntity entity) {
        //TODO:ADD HEALING CORE SPAWNING
    }
    
    @Override
    public float damageMult() {
        return 2;
    }
    
    @Override
    public float healthMult() {
        return 3;
    }
    
    @Override
    public int getColour() {
        return 0x21954C;
    }
    
}
