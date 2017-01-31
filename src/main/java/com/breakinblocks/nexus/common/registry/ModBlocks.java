package com.breakinblocks.nexus.common.registry;

import com.breakinblocks.nexus.Nexus;
import com.breakinblocks.nexus.common.blocks.BlockEnderReservoir;
import com.breakinblocks.nexus.common.blocks.BlockFloodgate;
import com.breakinblocks.nexus.common.blocks.FluidBlockMana;
import com.breakinblocks.nexus.common.blocks.FluidMana;
import com.breakinblocks.nexus.common.items.ItemBlockEnderReservoir;
import com.breakinblocks.nexus.client.TextureHandler;
import com.breakinblocks.nexus.common.tiles.TileEnderReservoir;
import com.breakinblocks.nexus.common.tiles.TileFloodgate;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.InvocationTargetException;


public class ModBlocks {
	public static Fluid FLUIDMANACOLOURLESS;
	public static FluidBlockMana BLOCKMANACOLOURLESS;
	public static Fluid FLUIDMANAWHITE;
	public static FluidBlockMana BLOCKMANAWHITE;
	public static Fluid FLUIDMANABLUE;
	public static FluidBlockMana BLOCKMANABLUE;
	public static Fluid FLUIDMANABLACK;
	public static FluidBlockMana BLOCKMANABLACK;
	public static Fluid FLUIDMANARED;
	public static FluidBlockMana BLOCKMANARED;
	public static Fluid FLUIDMANAGREEN;
	public static FluidBlockMana BLOCKMANAGREEN;

	public static BlockFloodgate FLOODGATE;

		
	public static BlockEnderReservoir ENDERRESERVOIR;
	
	public static void init() {
		registerBlocks();
		registerFluids();
		registerTiles();
	}

	
	
	
	public static void registerBlocks() {
		
		FLOODGATE = register(new BlockFloodgate(),"floodgate");
		ENDERRESERVOIR = register(new BlockEnderReservoir(), ItemBlockEnderReservoir.class, "enderreservoir");
	}

    @SideOnly(Side.CLIENT)
    public static void initModels() {
    }

    
	public static void registerFluids() {
		FluidMana fluidColourless = new FluidMana("manacolourless", TextureHandler.fluidManaColourlessStill, TextureHandler.fluidManaColourlessFlow);
		FluidRegistry.registerFluid(fluidColourless);
		FLUIDMANACOLOURLESS = FluidRegistry.getFluid(fluidColourless.getName());
		BLOCKMANACOLOURLESS = new FluidBlockMana(fluidColourless,new MaterialLiquid(MapColor.GRAY));
		GameRegistry.register(BLOCKMANACOLOURLESS);
		FLUIDMANACOLOURLESS.setBlock(BLOCKMANACOLOURLESS);
		FluidRegistry.addBucketForFluid(fluidColourless);
		ModItems.BUCKETMANACOLOURLESS = UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, FLUIDMANACOLOURLESS);

		FluidMana fluidWhite = new FluidMana("manawhite", TextureHandler.fluidManaWhiteStill, TextureHandler.fluidManaWhiteFlow);
		FluidRegistry.registerFluid(fluidWhite);
		FLUIDMANAWHITE = FluidRegistry.getFluid(fluidWhite.getName());
		BLOCKMANAWHITE = new FluidBlockMana(fluidWhite,new MaterialLiquid(MapColor.SNOW));
		GameRegistry.register(BLOCKMANAWHITE);
		FLUIDMANAWHITE.setBlock(BLOCKMANAWHITE);
		FluidRegistry.addBucketForFluid(fluidWhite);
		ModItems.BUCKETMANAWHITE = UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, FLUIDMANAWHITE);

		FluidMana fluidBlue = new FluidMana("manablue", TextureHandler.fluidManaBlueStill, TextureHandler.fluidManaBlueFlow);
		FluidRegistry.registerFluid(fluidBlue);
		FLUIDMANABLUE = FluidRegistry.getFluid(fluidBlue.getName());
		BLOCKMANABLUE = new FluidBlockMana(fluidBlue,new MaterialLiquid(MapColor.WATER));
		GameRegistry.register(BLOCKMANABLUE);
		FLUIDMANABLUE.setBlock(BLOCKMANABLUE);
		FluidRegistry.addBucketForFluid(fluidBlue);
		ModItems.BUCKETMANABLUE = UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, FLUIDMANABLUE);

		FluidMana fluidBlack = new FluidMana("manablack", TextureHandler.fluidManaBlackStill, TextureHandler.fluidManaBlackFlow);
		FluidRegistry.registerFluid(fluidBlack);
		FLUIDMANABLACK = FluidRegistry.getFluid(fluidBlack.getName());
		BLOCKMANABLACK = new FluidBlockMana(fluidBlack,new MaterialLiquid(MapColor.OBSIDIAN));
		GameRegistry.register(BLOCKMANABLACK);
		FLUIDMANABLACK.setBlock(BLOCKMANABLACK);
		FluidRegistry.addBucketForFluid(fluidBlack);
		ModItems.BUCKETMANABLACK = UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, FLUIDMANABLACK);

		FluidMana fluidRed = new FluidMana("manared", TextureHandler.fluidManaRedStill, TextureHandler.fluidManaRedFlow);
		FluidRegistry.registerFluid(fluidRed);
		FLUIDMANARED = FluidRegistry.getFluid(fluidRed.getName());
		BLOCKMANARED = new FluidBlockMana(fluidRed,new MaterialLiquid(MapColor.NETHERRACK));
		GameRegistry.register(BLOCKMANARED);
		FLUIDMANARED.setBlock(BLOCKMANARED);
		FluidRegistry.addBucketForFluid(fluidRed);
		ModItems.BUCKETMANARED = UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, FLUIDMANARED);

		FluidMana fluidGreen = new FluidMana("managreen", TextureHandler.fluidManaGreenStill, TextureHandler.fluidManaGreenFlow);
		FluidRegistry.registerFluid(fluidGreen);
		FLUIDMANAGREEN = FluidRegistry.getFluid(fluidGreen.getName());
		BLOCKMANAGREEN = new FluidBlockMana(fluidGreen,new MaterialLiquid(MapColor.GRASS));
		GameRegistry.register(BLOCKMANAGREEN);
		FLUIDMANAGREEN.setBlock(BLOCKMANAGREEN);
		FluidRegistry.addBucketForFluid(fluidGreen);
		ModItems.BUCKETMANAGREEN = UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, FLUIDMANAGREEN);

		

	}

	public static void registerTiles() {
		GameRegistry.registerTileEntity(TileFloodgate.class,"floodgate");
		GameRegistry.registerTileEntity(TileEnderReservoir.class, "enderreservoir");
	}

	public static <T extends Block>T register(T block, String name) {
		if (block.getRegistryName() == null)
			block.setRegistryName(name);
		ItemBlock item = new ItemBlock(block) {
			@Override
			public int getMetadata(int damage) {
				return damage;
			}
		};
		item.setRegistryName(name);
		block.setUnlocalizedName(name);
		block.setCreativeTab(Nexus.tab);
		GameRegistry.register(block);
		GameRegistry.register(item);
		TextureHandler.blockBuffer.add(block);
		return block;
	}
	
	public static <T extends Block>T register(T block, Class<? extends ItemBlock> itemclass, String name) {
		if (block.getRegistryName() == null)
			block.setRegistryName(name);

		try
		{
			ItemBlock itemBlock = itemclass.getConstructor(Block.class).newInstance(block);
			itemBlock.setRegistryName(name);
			GameRegistry.register(itemBlock);
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e)
		{
			e.printStackTrace();
		}
		
		block.setUnlocalizedName(name);
		block.setCreativeTab(Nexus.tab);
		GameRegistry.register(block);
		TextureHandler.blockBuffer.add(block);
		
		return block;
	}
	
	public static void registerBlock(Block block, Class<? extends ItemBlock> itemclass, String name)
	{
	}
	
	
}
