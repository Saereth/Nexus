package com.breakinblocks.nexus.common.world;

import java.util.Random;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.IWorldGenerator;

import com.breakinblocks.nexus.common.registry.ModBlocks;
import com.breakinblocks.nexus.common.util.ManaConcentrationHelper;

public class NexusWorldGenerator implements IWorldGenerator {
	public static final int VERSION = 0;

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {

		long worldSeed = world.getSeed();
		Random rand = new Random(worldSeed);
		ManaConcentrationHelper MC = new ManaConcentrationHelper();
		long x = rand.nextLong() >> 2 + 1L;
		long z = rand.nextLong() >> 2 + 1L;
		long chunkSeed = (x * chunkX + z * chunkZ) ^ worldSeed;
		BlockPos finalPos;
		Block mana;

		for (int xx = 0; xx < 16; xx++) {
			for (int zz = 0; zz < 16; zz++) {
				BlockPos pos = new BlockPos((chunkX * 16) + xx, 1, (chunkZ * 16) + zz);

				if (!biomeIsValid(world, pos))
					continue;

				float distr = MC.getConcentration(chunkSeed, pos);
				int y = (int) (100 + distr * 100);
				if (y >= 185) {
					finalPos = findTopBlock(world, pos);
					if (validPoolLocation(world, finalPos)) {
						System.out.println("Found location checking for top y block");
						if (finalPos.getY() == -1)
							continue;
						System.out.println("Found top Y block, checking for each color");
						// White = 1, Black = 2, Red = 3, Blue = 4, Green = 5, 0 = colorless

						if (biomeIsValidforColor(world, finalPos, 0)) { // Black
							mana = ModBlocks.FLUIDMANACOLOURLESS.getBlock();
							makePool(world, finalPos, mana, 8);

						}
						if (biomeIsValidforColor(world, finalPos, 1)) { // White
							mana = ModBlocks.FLUIDMANAWHITE.getBlock();
							makePool(world, finalPos, mana, 0);
						}
						if (biomeIsValidforColor(world, finalPos, 2)) { // Black
							mana = ModBlocks.FLUIDMANABLACK.getBlock();
							makePool(world, finalPos, mana, 15);
						}
						if (biomeIsValidforColor(world, finalPos, 3)) { // Red
							mana = ModBlocks.FLUIDMANARED.getBlock();
							makePool(world, finalPos, mana, 14);
						}
						if (biomeIsValidforColor(world, finalPos, 4)) { // Blue
							mana = ModBlocks.FLUIDMANABLUE.getBlock();
							makePool(world, finalPos, mana, 9);
						}
						if (biomeIsValidforColor(world, finalPos, 5)) { // Green
							mana = ModBlocks.FLUIDMANAGREEN.getBlock();
							makePool(world, finalPos, mana, 5);
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	private void makePool(World world, BlockPos pos, Block mana, int stained) {
		System.out.println("Generating Pool at: " + pos.getX() + "," + pos.getY() + "," + pos.getZ());
		Block mCobble,glass;

		mCobble = Blocks.MOSSY_COBBLESTONE;
		glass = Blocks.STAINED_GLASS;
		
		world.setBlockState(pos, mana.getDefaultState());
		world.setBlockState(pos.add(-1, 0, 0), mana.getDefaultState());
		world.setBlockState(pos.add(1, 0, 0), mana.getDefaultState());
		world.setBlockState(pos.add(0, 0, -1), mana.getDefaultState());
		world.setBlockState(pos.add(0, 1, 0), mana.getDefaultState());
		world.setBlockState(pos.add(0, -1, 0), mana.getDefaultState());
		world.setBlockState(pos.add(0, -2, 0), mana.getDefaultState());

		world.setBlockState(pos.add(1, 1, 0), glass.getStateFromMeta(stained));
		world.setBlockState(pos.add(0, 1, 1), glass.getStateFromMeta(stained));
		world.setBlockState(pos.add(-1, 1, 0), glass.getStateFromMeta(stained));
		world.setBlockState(pos.add(0, 1, -1), glass.getStateFromMeta(stained));

		world.setBlockState(pos.add(1, 1, 1), mCobble.getDefaultState());
		world.setBlockState(pos.add(-1, 1, -1), mCobble.getDefaultState());
		world.setBlockState(pos.add(1, 1, -1), mCobble.getDefaultState());
		world.setBlockState(pos.add(-1, 1, 1), mCobble.getDefaultState());

		world.setBlockToAir(pos.add(0, 1, 0).up());
		return;
	}

	private BlockPos findTopBlock(World world, BlockPos pos) {
		while (pos.getY() > 0 && world.isAirBlock(pos)) {
			pos = pos.down();
		}
		if (pos.getY() <= 0)
			return new BlockPos(0, -1, 0);

		while (pos.getY() < world.provider.getActualHeight() && !world.isAirBlock(pos.up())) {
			pos = pos.up();
		}
		if (pos.getY() > world.provider.getActualHeight())
			return new BlockPos(0, -1, 0);

		return pos.down();
	}

	public NexusWorldGenerator setupAttributes() {
		return this;
	}

	private boolean validPoolLocation(World world, BlockPos pos) {

		int radius = 2;
		IBlockState requiredBlock = world.getBiome(pos).topBlock;
		IBlockState alternateBlock = world.getBiome(pos).fillerBlock;
		IBlockState alternateBlock3 = Blocks.SAND.getDefaultState();
		
		if (requiredBlock == null || alternateBlock == null) {
			return false;
		}

		for (int i = -radius; i < radius; ++i) {
			for (int k = -radius; k < radius; ++k) {
				IBlockState blockBelow = world.getBlockState(pos.add(i, -1, k));
				IBlockState block = world.getBlockState(pos.add(i, 0, k));

				if (blockBelow == null || block == null) {
					return false;
				}
				if (block != requiredBlock && block != alternateBlock && block != alternateBlock3) {
					return false;
				}

				IBlockState blockAbove = world.getBlockState(pos.up());
				if (blockAbove == null) {
					continue;
				}
			}
		}

		return true;
	}

	private boolean biomeIsValid(World world, BlockPos pos) {
		Biome biome = world.getBiome(pos);
		Set<Type> types = BiomeDictionary.getTypes(biome);

		for (Type type : types) {
			if (type == Type.END || type == Type.MUSHROOM || type == Type.NETHER || type == Type.WASTELAND) {
				return false;
			}
		}

		return true;
	}

	private boolean biomeIsValidforColor(World world, BlockPos pos, int color) {

		Biome biome = world.getBiome(pos);
		Set<Type> types = BiomeDictionary.getTypes(biome);

		switch (color) {
		case 1:// White
			for (Type type : types) {
				if (type == Type.PLAINS || type == Type.HOT) {
					return true;
				}
			}
			break;
		case 2:// Black
			for (Type type : types) {
				if (type == Type.SWAMP || type == Type.DEAD || type == Type.HOT) {
					return true;
				}
			}
			break;
		case 3:// Red
			for (Type type : types) {
				if (type == Type.MOUNTAIN || type == Type.HILLS || type == Type.MESA) {
					return true;
				}
			}
			break;
		case 4:// Blue
			for (Type type : types) {
				if (type == Type.OCEAN || type == Type.BEACH || type == Type.RIVER) {
					return true;
				}
			}
			break;
		case 5:// Green
			for (Type type : types) {
				if (type == Type.FOREST || type == Type.JUNGLE) {
					return true;
				}
			}
			break;
		case 0:
		default:
			for (Type type : types) {
				if (type == Type.END) {
					return true;
				}
			}

			break;
		}

		return false;
	}

}
