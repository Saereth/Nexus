package com.teamdman.nexus;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Nexus.MODID, name = Nexus.NAME, version = Nexus.VERSION, dependencies = Nexus.DEPENDENCIES)
public class Nexus {
	public static final String MODID = "nexus";
	public static final String NAME = "Nexus";
	public static final String VERSION = "@VERSION";
	public static final String DEPENDENCIES = "";

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {}

	@Mod.EventHandler
	public void preinit (FMLPreInitializationEvent event) {}

	@Mod.EventHandler
	public void postinit (FMLPostInitializationEvent event) {}
}
