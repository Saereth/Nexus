package com.breakinblocks.nexus.proxy;

import com.breakinblocks.nexus.handlers.TextureHandler;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);

		TextureHandler.registerFluidRenderers();
	}

	@Override
	public void tryHandleItemModel(Item item, String name) {
		TextureHandler.handle(item,name);
	}
}
