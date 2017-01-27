package com.breakinblocks.nexus.proxy;

import com.breakinblocks.nexus.handlers.TextureHandler;
import net.minecraft.item.Item;

public class ClientProxy extends CommonProxy {
	@Override
	public void tryHandleItemModel(Item item, String name) {
		TextureHandler.handle(item,name);
	}
}
