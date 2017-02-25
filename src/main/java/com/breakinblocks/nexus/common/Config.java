package com.breakinblocks.nexus.common;

import com.breakinblocks.nexus.Nexus;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class Config {
	private static Configuration config;

	public static Configuration getConfig() {
		return config;
	}

	public static int manapooldensity;
	public static double fluidmanaconsumechance;

	public static void init(File file) {
		config = new Configuration(file);
		syncConfig();
	}

	public static void syncConfig() {
		String category;

		category = "Mana Values";
		config.addCustomCategoryComment(category,"Various mana values");
		config.setCategoryRequiresMcRestart(category,false);
		manapooldensity=config.get(category,"manapooldensity",100,"Value as percentage").getInt();
		fluidmanaconsumechance=config.get(category,"fluidmanaconsumechance",0.005,"Percent chance of fluid being consumed on interaction (0-1)").getDouble();
		config.save();
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent event) {
		if (event.getModID().equals(Nexus.MODID))
			syncConfig();
	}
}
