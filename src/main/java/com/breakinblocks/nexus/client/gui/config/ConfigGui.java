package com.breakinblocks.nexus.client.gui.config;


import com.breakinblocks.nexus.Nexus;
import com.breakinblocks.nexus.common.Config;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;

public class ConfigGui extends GuiConfig
{

	public ConfigGui(GuiScreen parentScreen)
	{
		super(parentScreen, getConfigElements(parentScreen), Nexus.MODID, false, false, Nexus.MODID);
	}

	private static List<IConfigElement> getConfigElements(GuiScreen parent)
	{
		List<IConfigElement> list = new ArrayList<IConfigElement>();

		list.add(new ConfigElement(Config.getConfig().getCategory("EnumMana Values".toLowerCase())));

		return list;
	}
}