package com.breakinblocks.nexus.common.handlers;

import com.breakinblocks.nexus.Nexus;
import com.breakinblocks.nexus.common.Config;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandler {
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(Nexus.MODID))
			Config.syncConfig();
	}
}
