package net.smileycorp.elites.common;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.network.NetworkDirection;
import net.smileycorp.elites.common.affixes.Affix;
import net.smileycorp.elites.common.affixes.AffixHolder;
import net.smileycorp.elites.common.affixes.Affixes;
import net.smileycorp.elites.common.effects.ElitesEffects;
import net.smileycorp.elites.common.network.PacketHandler;
import net.smileycorp.elites.common.network.SyncAffixMessage;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@EventBusSubscriber(modid = Constants.MODID, bus = Bus.MOD)
public class ElitesEventHandler {
	
	@SubscribeEvent
	public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
		Entity entity = event.getObject();
		if (entity instanceof LivingEntity &!(entity instanceof Player))
			event.addCapability(Constants.loc("Affix"), new AffixHolder.Provider());
	}
	
	@SubscribeEvent
	public void startTracking(PlayerEvent.StartTracking event) {
		if (!(event.getTarget() instanceof LivingEntity)) return;
		LivingEntity entity = (LivingEntity) event.getTarget();
		Optional<Affix> affix = Affix.getAffix(entity);
		if (!affix.isPresent()) return;
		PacketHandler.sendTo(new SyncAffixMessage(affix.get(), entity), ((ServerPlayer)event.getEntity()).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
	}
	
	@SubscribeEvent
	public void spawn(MobSpawnEvent.FinalizeSpawn event) {
		if (!(event.getEntity() instanceof Enemy)) return;
		//if (event.getEntity().getRandom().nextInt(10) > 0) return;
		LazyOptional<AffixHolder> optional = event.getEntity().getCapability(AffixHolder.CAPABILITY);
		if (!optional.isPresent()) return;
		AffixHolder cap = optional.orElseGet(null);
		if (cap.hasAffix()) return;
		Optional<Affix> affix = Affixes.getRandomAffix(event.getEntity());
		if (!affix.isPresent()) return;
		cap.setAffix(affix.get(), event.getEntity());
	}
	
	@SubscribeEvent
	public void convert(LivingConversionEvent.Post event) {
		Optional<Affix> affix = Affix.getAffix(event.getEntity());
		if (!affix.isPresent()) return;
		LazyOptional<AffixHolder> optional = event.getOutcome().getCapability(AffixHolder.CAPABILITY);
		if (!optional.isPresent()) return;
		optional.orElse(null).setAffix(affix.get(), event.getOutcome());
	}
	
	@SubscribeEvent
	public void tick(LivingEvent.LivingTickEvent event) {
		LivingEntity entity = event.getEntity();
		Optional<Affix> optional = Affix.getAffix(entity);
		if (optional.isPresent()) optional.get().tick(entity);
	}
	
	@SubscribeEvent
	public void attackEntity(LivingHurtEvent event) {
		LivingEntity entity = event.getEntity();
		if (entity.hasEffect(ElitesEffects.CRIPPLE.get())) event.setAmount(event.getAmount() * 1.25f);
		Optional<Affix> optional = Affix.getAffix(entity);
		if (optional.isPresent()) event.setAmount(optional.get().hurt(entity, event.getSource(), event.getAmount()));
		DamageSource source = event.getSource();
		if (!(source.getEntity() instanceof LivingEntity)) return;
		LivingEntity attacker = (LivingEntity) source.getEntity();
		Optional<Affix> optional1 = Affix.getAffix(attacker);
		if (optional1.isPresent()) optional1.get().dealDamage(attacker, entity, event.getAmount());
	}
	
	@SubscribeEvent
	public void die(LivingDeathEvent event) {
		LivingEntity entity = event.getEntity();
		Optional<Affix> optional = Affix.getAffix(entity);
		if (optional.isPresent()) optional.get().die(entity);
	}

	@SubscribeEvent
	public void effectExpired(MobEffectEvent.Expired event) {
		@Nullable MobEffectInstance effect = event.getEffectInstance();
		if (effect.getEffect() == ElitesEffects.COLLAPSE.get()) {
			LivingEntity entity = event.getEntity();
			entity.hurt(ElitesDamageSources.collapse(entity), effect.getAmplifier());
		}
	}

	@SubscribeEvent
	public void heal(LivingHealEvent event) {
		LivingEntity entity = event.getEntity();
		if (entity.hasEffect(ElitesEffects.HEALING_DISABLED.get())) event.setCanceled(true);
	}

}
