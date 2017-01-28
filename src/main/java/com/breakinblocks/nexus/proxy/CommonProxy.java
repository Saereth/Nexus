package com.breakinblocks.nexus.proxy;

import com.breakinblocks.nexus.registry.ModBlocks;
import com.breakinblocks.nexus.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		ModBlocks.init();
		ModItems.init();
	}

	public void init(FMLInitializationEvent event) {

	}

	public void postInit(FMLPostInitializationEvent event) {

	}

	public void tryHandleItemModel(Item item, String name) {
	}

	public void tryHandleBlockModel(Block block, String name) {
	}

}
