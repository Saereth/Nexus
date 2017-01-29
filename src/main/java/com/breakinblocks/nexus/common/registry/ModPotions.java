package com.breakinblocks.nexus.common.registry;

import com.breakinblocks.nexus.common.potion.PotionManaburn;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModPotions {
	public static Potion MANABURN;
	public static void init() {
		MANABURN = register(new PotionManaburn(), "manaburn");
	}

	public static Potion register(Potion potion, String name) {
		potion.setRegistryName(name);
		potion.setPotionName(net.minecraft.client.resources.I18n.format("potion."+name+".name"));
		GameRegistry.register(potion);

		return potion;
	}


}
