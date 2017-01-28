package com.breakinblocks.nexus.common.blocks;


import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidMana extends Fluid {
	public FluidMana(String fluidName, ResourceLocation still, ResourceLocation flowing) {
		super(fluidName, still, flowing);
		setUnlocalizedName(fluidName);
		setRarity(EnumRarity.RARE);
		setLuminosity(6);
		setDensity(650);
		setViscosity(450);
		setTemperature(200);
		
		setFillSound(SoundEvents.ITEM_BUCKET_FILL);
	}
	
	
	
}
