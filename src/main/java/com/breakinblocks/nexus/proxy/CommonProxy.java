package com.breakinblocks.nexus.proxy;

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

	}

	public void init(FMLInitializationEvent event) {
		GameRegistry.registerWorldGenerator(worldGenerator.setupAttributes(), 30);
		System.out.println("Generator Called");
	}

	public void postInit(FMLPostInitializationEvent event) {

	}

	public void tryHandleItemModel(Item item, String name) {
	}

	public void tryHandleBlockModel(Block block, String name) {
	}

}
	