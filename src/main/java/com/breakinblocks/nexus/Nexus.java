package com.breakinblocks.nexus;

import com.breakinblocks.nexus.proxy.CommonProxy;
import com.breakinblocks.nexus.registry.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Nexus.MODID, name = Nexus.NAME, version = Nexus.VERSION, dependencies = Nexus.DEPENDENCIES)
public class Nexus {
	public static final String MODID = "nexus";
	public static final String NAME = "Nexus";
	public static final String VERSION = "@VERSION@";
	public static final String DEPENDENCIES = "";
	public static CreativeTabs tab = new CreativeTabs(MODID) {
		@Override
		public Item getTabIconItem() {
			return Items.COOKED_BEEF;
		}
	};

	@SidedProxy(clientSide = "com.breakinblocks.nexus.proxy.ClientProxy", serverSide = "com.breakinblocks.nexus.proxy.ServerProxy")
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		ModItems.init();
	}

	@Mod.EventHandler
	public void preinit (FMLPreInitializationEvent event) {

	}

	@Mod.EventHandler
	public void postinit (FMLPostInitializationEvent event) {}
}
