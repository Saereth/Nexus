package com.breakinblocks.nexus.common.world;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;
import com.breakinblocks.nexus.common.util.ManaConcentrationHelper;

public class NexusWorldGenerator implements IWorldGenerator {
	public static final int VERSION = 0;

	
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {

    	System.out.println("Calling Generate!");
    	
        long worldSeed = world.getSeed();
        Random rand = new Random(worldSeed);
        ManaConcentrationHelper MC = new ManaConcentrationHelper();
        long x = rand.nextLong() >> 2 + 1L;
        long z = rand.nextLong() >> 2 + 1L;
        long chunkSeed = (x * chunkX + z * chunkZ) ^ worldSeed;

        for (int xx = 0; xx < 16; xx++) {
            for (int zz = 0; zz < 16; zz++) {
                BlockPos pos = new BlockPos((chunkX * 16) + xx, 0, (chunkZ * 16) + zz);
                float distr = MC.getConcentration(chunkSeed, pos);
                int y = (int) (100 + distr * 40);
                world.setBlockState(new BlockPos(pos.getX(), y, pos.getZ()), Blocks.GLASS.getDefaultState(), 2);
            }
        }
    }
	
    public NexusWorldGenerator setupAttributes() {
    	System.out.println("Calling setup!");
    	return this;
    }
    
}
