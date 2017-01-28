package com.breakinblocks.nexus.client;

import com.breakinblocks.nexus.Nexus;
import com.breakinblocks.nexus.common.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;

import java.util.stream.Stream;

public class TextureHandler {
	public static final ResourceLocation fluidManaColourlessStill = new ResourceLocation(Nexus.MODID + ":blocks/fluid/manacolourless_still");
	public static final ResourceLocation fluidManaColourlessFlow = new ResourceLocation(Nexus.MODID + ":blocks/fluid/manacolourless_flow");
	public static final ResourceLocation fluidManaWhiteStill = new ResourceLocation(Nexus.MODID + ":blocks/fluid/manawhite_still");
	public static final ResourceLocation fluidManaWhiteFlow = new ResourceLocation(Nexus.MODID + ":blocks/fluid/manawhite_flow");
	public static final ResourceLocation fluidManaBlueStill = new ResourceLocation(Nexus.MODID + ":blocks/fluid/manablue_still");
	public static final ResourceLocation fluidManaBlueFlow = new ResourceLocation(Nexus.MODID + ":blocks/fluid/manablue_flow");
	public static final ResourceLocation fluidManaBlackStill = new ResourceLocation(Nexus.MODID + ":blocks/fluid/manablack_still");
	public static final ResourceLocation fluidManaBlackFlow = new ResourceLocation(Nexus.MODID + ":blocks/fluid/manablack_flow");
	public static final ResourceLocation fluidManaRedStill = new ResourceLocation(Nexus.MODID + ":blocks/fluid/manared_still");
	public static final ResourceLocation fluidManaRedFlow = new ResourceLocation(Nexus.MODID + ":blocks/fluid/manared_flow");
	public static final ResourceLocation fluidManaGreenStill = new ResourceLocation(Nexus.MODID + ":blocks/fluid/managreen_still");
	public static final ResourceLocation fluidManaGreenFlow = new ResourceLocation(Nexus.MODID + ":blocks/fluid/managreen_flow");

	public static void registerFluidRenderers() {
		registerFluidRenderer(ModBlocks.FLUIDMANACOLOURLESS);
		registerFluidRenderer(ModBlocks.FLUIDMANAWHITE);
		registerFluidRenderer(ModBlocks.FLUIDMANABLUE);
		registerFluidRenderer(ModBlocks.FLUIDMANABLACK);
		registerFluidRenderer(ModBlocks.FLUIDMANARED);
		registerFluidRenderer(ModBlocks.FLUIDMANAGREEN);
	}

	public static void registerFluidRenderer(Fluid fluid) {
		FluidCustomModelMapper mapper = new FluidCustomModelMapper(fluid);
		Block block = fluid.getBlock();
		if (block != null) {
			Item item = Item.getItemFromBlock(block);
			if (item != null) {
				ModelLoader.registerItemVariants(item);
				ModelLoader.setCustomMeshDefinition(item, mapper);
			} else {
				ModelLoader.setCustomStateMapper(block, mapper);
			}
		}
	}

	public static void registerItem(Item item, int meta, String name) {
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		mesher.register(item, meta, new ModelResourceLocation(Nexus.MODID + ":" + name, "inventory"));
	}

	public static void handle(Item item, String name) {
		registerItem(item, 0, name);
		//TODO: Handle sub items n stuff
	}


	public static class FluidCustomModelMapper extends StateMapperBase implements ItemMeshDefinition {
		private final ModelResourceLocation resource;

		public FluidCustomModelMapper(Fluid fluid) {
			System.out.println(fluid);
			System.out.println(fluid.getName());
			for (int i=0;i<10;i++)
				System.out.println();
			this.resource = new ModelResourceLocation(Nexus.MODID + ":fluids", fluid.getName());
		}

		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack) {
			return resource;
		}

		@Override
		protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
			return resource;
		}
	}
}
