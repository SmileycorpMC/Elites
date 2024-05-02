package net.smileycorp.elites.common;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.smileycorp.elites.client.ClientHandler;
import net.smileycorp.elites.common.effects.ElitesEffects;
import net.smileycorp.elites.common.network.PacketHandler;

@Mod(value = Constants.MODID)
@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Elites {

	public Elites() {
		ElitesLogger.clearLog();
		PacketHandler.initPackets();
	}

	@SubscribeEvent
	public static void constructMod(FMLConstructModEvent event) {
		MinecraftForge.EVENT_BUS.register(new ElitesEventHandler());
		ElitesEffects.EFFECTS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

	@SubscribeEvent
	public static void commonSetup(FMLCommonSetupEvent event) {
	
	}

	@SubscribeEvent
	public static void loadClient(FMLClientSetupEvent event) {
		new ClientHandler().init();
	}

}
