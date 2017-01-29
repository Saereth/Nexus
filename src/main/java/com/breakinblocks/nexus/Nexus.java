package com.breakinblocks.nexus;

import com.breakinblocks.nexus.common.CommonProxy;
import com.breakinblocks.nexus.common.registry.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Nexus.MODID, name = Nexus.NAME, version = Nexus.VERSION, dependencies = Nexus.DEPENDENCIES, guiFactory = "com.breakinblocks.nexus.client.gui.config.ConfigGuiFactory")
public class Nexus {
	public static final String MODID = "nexus";
	public static final String NAME = "Nexus";
	public static final String VERSION = "@VERSION@";
	public static final String DEPENDENCIES = "";
	public static CreativeTabs tab = new CreativeTabs(MODID) {
		@Override
		public Item getTabIconItem() {
			return ModItems.TABLET;
		}
	};

	@SidedProxy(clientSide = "com.breakinblocks.nexus.client.ClientProxy", serverSide = "com.breakinblocks.nexus.common.CommonProxy")
	public static CommonProxy proxy;

	static {
		FluidRegistry.enableUniversalBucket();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@Mod.EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@Mod.EventHandler
	public void postinit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
}
