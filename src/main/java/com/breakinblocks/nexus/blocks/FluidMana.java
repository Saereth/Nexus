package com.breakinblocks.nexus.blocks;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidMana extends Fluid {
	public FluidMana(String fluidName, ResourceLocation still, ResourceLocation flowing) {
		super(fluidName, still, flowing);
		setUnlocalizedName(fluidName);
		setRarity(EnumRarity.RARE);
		setLuminosity(15);
		setDensity(1000);
		setViscosity(300);
		setTemperature(100);
		setFillSound(SoundEvents.ITEM_BUCKET_FILL);
	}
}
