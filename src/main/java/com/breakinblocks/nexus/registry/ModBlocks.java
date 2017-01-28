package com.breakinblocks.nexus.registry;

import com.breakinblocks.nexus.Nexus;
import com.breakinblocks.nexus.blocks.FluidBlockMana;
import com.breakinblocks.nexus.blocks.FluidMana;
import com.breakinblocks.nexus.handlers.TextureHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {
	public static Fluid FLUIDMANAGREEN;
	public static FluidBlockMana BLOCKMANAGREEN;

	public static void init() {
		registerBlocks();
		registerFluids();
		registerTiles();
	}

	public static void registerBlocks() {

	}

	public static void registerFluids() {
		FluidMana fluid = new FluidMana("managreen", TextureHandler.fluidManaGreenStill, TextureHandler.fluidManaGreenFlow);
		FluidRegistry.registerFluid(fluid);
		FLUIDMANAGREEN = FluidRegistry.getFluid(fluid.getName());
		BLOCKMANAGREEN = new FluidBlockMana(fluid,new MaterialLiquid(MapColor.GRASS));
		GameRegistry.register(BLOCKMANAGREEN);
		FLUIDMANAGREEN.setBlock(BLOCKMANAGREEN);
		FluidRegistry.addBucketForFluid(fluid);
		ModItems.BUCKETMANAGREEN = UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, FLUIDMANAGREEN);
	}

	public static void registerTiles() {

	}

	public static <T extends Block>T register(T block, String name) {
		if (block.getRegistryName() == null)
			block.setRegistryName(name);
		block.setUnlocalizedName(name);
		block.setCreativeTab(Nexus.tab);
		GameRegistry.register(block);
		Nexus.proxy.tryHandleBlockModel(block,name);
		return block;
	}
}
