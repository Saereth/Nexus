package com.breakinblocks.nexus.handlers;

import com.breakinblocks.nexus.Nexus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class TextureHandler {
	public static void registerItem(Item item, int meta, String name) {
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		mesher.register(item, meta, new ModelResourceLocation(Nexus.MODID + ":" + name, "inventory"));
	}
	public static void handle(Item item,String name) {
		registerItem(item,0,name);
		//TODO: Handle sub items n stuff
	}
}
