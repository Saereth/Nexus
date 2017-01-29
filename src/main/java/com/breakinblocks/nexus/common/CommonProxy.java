package com.breakinblocks.nexus.common;

import java.io.File;

import com.breakinblocks.nexus.Nexus;
import com.breakinblocks.nexus.common.handlers.EventHandler;
import com.breakinblocks.nexus.common.registry.ModBlocks;
import com.breakinblocks.nexus.common.registry.ModItems;
import com.breakinblocks.nexus.common.registry.ModPotions;
import com.breakinblocks.nexus.common.world.NexusWorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	
	public static NexusWorldGenerator worldGenerator = new NexusWorldGenerator();

	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new EventHandler());
		Config.init(new File(event.getModConfigurationDirectory(), Nexus.MODID + ".cfg"));
		ModBlocks.init();
		ModItems.init();
		ModPotions.init();
	}

	public void init(FMLInitializationEvent event) {
		GameRegistry.registerWorldGenerator(worldGenerator.setupAttributes(), 10);
	}

	public void postInit(FMLPostInitializationEvent event) {

	}
}
	