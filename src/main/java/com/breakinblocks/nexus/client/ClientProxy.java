package com.breakinblocks.nexus.client;

import com.breakinblocks.nexus.common.CommonProxy;
import com.breakinblocks.nexus.common.registry.ModBlocks;
import com.breakinblocks.nexus.common.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		TextureHandler.registerFluidRenderers();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		TextureHandler.itemBuffer.forEach(e -> TextureHandler.handle(e, e.getRegistryName().getResourcePath()));
		TextureHandler.blockBuffer.forEach(e -> TextureHandler.handle(e, e.getRegistryName().getResourcePath()));
	}
}
