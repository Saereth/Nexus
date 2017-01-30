package com.breakinblocks.nexus.common.util;

import com.breakinblocks.nexus.common.registry.ModBlocks;
import net.minecraftforge.fluids.Fluid;

public enum EnumMana {
	COLOURLESS(ModBlocks.FLUIDMANACOLOURLESS),
	WHITE(ModBlocks.FLUIDMANAWHITE),
	BLUE(ModBlocks.FLUIDMANABLUE),
	BLACK(ModBlocks.FLUIDMANABLACK),
	RED(ModBlocks.FLUIDMANARED),
	GREEN(ModBlocks.FLUIDMANAGREEN);

	private final Fluid fluid;

	EnumMana(Fluid fluid) {
		this.fluid = fluid;
	}

	public static EnumMana getFromFluid(Fluid in) {
		for (EnumMana mana : EnumMana.values())
			if (mana.fluid == in)
				return mana;
		return null;
	}
}
