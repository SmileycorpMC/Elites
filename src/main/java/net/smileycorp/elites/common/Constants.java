package net.smileycorp.elites.common;

import net.minecraft.resources.ResourceLocation;

public class Constants {

	public static final String MODID = "elites";
	public static final String NAME = "Elites";

	public static String name(String name) {
		return name(MODID, name);
	}

	public static String name(String modid, String name) {
		return modid + "." + name.replace("_", "");
	}

	public static ResourceLocation loc(String name) {
		return new ResourceLocation(MODID, name.toLowerCase());
	}

	public static String locStr(String string) {
		return loc(string).toString();
	}

}
