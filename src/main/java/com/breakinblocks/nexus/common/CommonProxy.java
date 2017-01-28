package com.breakinblocks.nexus.common;

import com.breakinblocks.nexus.common.registry.ModBlocks;
import com.breakinblocks.nexus.common.registry.ModItems;
import com.breakinblocks.nexus.common.world.NexusWorldGenerator;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	
	public static NexusWorldGenerator worldGenerator = new NexusWorldGenerator();

	public void preInit(FMLPreInitializationEvent event) {
		ModBlocks.init();
		ModItems.init();
	}

	public void init(FMLInitializationEvent event) {
		GameRegistry.registerWorldGenerator(worldGenerator.setupAttributes(), 10);
		System.out.println("Generator Called");
	}

	public void postInit(FMLPostInitializationEvent event) {

	}

	public void tryHandleItemModel(Item item, String name) {
	}

	public void tryHandleBlockModel(Block block, String name) {
	}

}
	