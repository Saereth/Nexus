package com.breakinblocks.nexus.common.registry;

import com.breakinblocks.nexus.Nexus;
import com.breakinblocks.nexus.client.TextureHandler;
import com.breakinblocks.nexus.common.items.ItemTablet;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class ModItems {
	public static Item TABLET;
	public static ItemStack BUCKETMANACOLOURLESS;
	public static ItemStack BUCKETMANAWHITE;
	public static ItemStack BUCKETMANABLUE;
	public static ItemStack BUCKETMANABLACK;
	public static ItemStack BUCKETMANARED;
	public static ItemStack BUCKETMANAGREEN;



	public static void init() {
		TABLET = register(new ItemTablet(),"tablet");
	}
	public static Item register(Item item, String name) {
		if (item.getRegistryName() == null)
			item.setRegistryName(name);
		item.setUnlocalizedName(name);
		item.setCreativeTab(Nexus.tab);
		GameRegistry.register(item);
		TextureHandler.itemBuffer.add(item);
		return item;
	}
}
