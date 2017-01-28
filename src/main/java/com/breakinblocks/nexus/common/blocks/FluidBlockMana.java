package com.breakinblocks.nexus.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class FluidBlockMana extends BlockFluidClassic {
	public FluidBlockMana(Fluid fluid, Material material) {
		super(fluid, material);
		setDefaultState(this.blockState.getBaseState().withProperty(LEVEL,0));
		setUnlocalizedName(fluid.getName());
		setRegistryName(fluid.getName());
	}

}
