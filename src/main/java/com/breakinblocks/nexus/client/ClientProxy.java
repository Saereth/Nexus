package com.breakinblocks.nexus.client;

import com.breakinblocks.nexus.Nexus;
import com.breakinblocks.nexus.common.CommonProxy;
import com.breakinblocks.nexus.common.registry.ModBlocks;

import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		OBJLoader.INSTANCE.addDomain(Nexus.MODID);
		TextureHandler.registerFluidRenderers();
        ModBlocks.initModels();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		TextureHandler.itemBuffer.forEach(e -> TextureHandler.handle(e, e.getRegistryName().getResourcePath()));
		TextureHandler.blockBuffer.forEach(e -> TextureHandler.handle(e, e.getRegistryName().getResourcePath()));
	}
}
