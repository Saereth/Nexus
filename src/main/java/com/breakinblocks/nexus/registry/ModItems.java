package com.breakinblocks.nexus.registry;

import com.breakinblocks.nexus.Nexus;
import com.breakinblocks.nexus.items.ItemTablet;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {
	public static Item tablet;
	public static void init() {
		tablet = setupItem(new ItemTablet(),"tablet");
	}
	public static Item setupItem(Item item, String name) {
		if (item.getRegistryName() == null)
			item.setRegistryName(name);
		item.setUnlocalizedName(name);
		item.setCreativeTab(Nexus.tab);
		GameRegistry.register(item);
		Nexus.proxy.tryHandleItemModel(item,name);
		return item;
	}
}
