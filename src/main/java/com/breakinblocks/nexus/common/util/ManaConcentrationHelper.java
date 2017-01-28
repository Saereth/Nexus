package com.breakinblocks.nexus.common.util;

import java.util.Random;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import java.util.Date;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class ManaConcentrationHelper {
	private static final Random rand = new Random();
	
    public float getConcentration(long seed, BlockPos pos) {
        BlockPos base = new BlockPos(
                        (int) Math.floor((float) pos.getX() / 100) * 100,
                        0,
                        (int) Math.floor((float) pos.getZ() / 100) * 100);
        float bottomL = getNoiseDistribution(seed,
        		base,
        		base.add(100, 0, 0),
        		base.add(0, 0, 100),
        		base.add(100, 0, 100),
                pos);
        rand.setSeed(seed);
        long nextLayerSeed = rand.nextLong();
        float topL = getNoiseDistribution(nextLayerSeed,
        		base,
        		base.add(100, 0, 0),
        		base.add(0, 0, 100),
        		base.add(100, 0, 100),
                pos);
        return bottomL * topL;
    }
    
    
    
    private float getNoiseDistribution(long seed, BlockPos lXlZ, BlockPos hXlZ, BlockPos lXhZ, BlockPos hXhZ, BlockPos exact) {
        float nll = getNoise(seed, lXlZ.getX(), lXlZ.getZ());
        float nhl = getNoise(seed, hXlZ.getX(), hXlZ.getZ());
        float nlh = getNoise(seed, lXhZ.getX(), lXhZ.getZ());
        float nhh = getNoise(seed, hXhZ.getX(), hXhZ.getZ());

        float xPart = Math.abs(((float) (exact.getX() - lXlZ.getX()) ) / 100);
        float zPart = Math.abs(((float) (exact.getZ() - lXlZ.getZ()) ) / 100);

        return linearInterpolate(linearInterpolate(nll, nhl, xPart), linearInterpolate(nlh, nhh, xPart), zPart);
    }
    
    
    private float getNoise(long seed, int posX, int posZ) {
    	int hash = hashCode();
    	System.out.println("Hashcode: " + hash);
    	rand.setSeed(hash);
        rand.nextLong();
        System.out.println("Rand.nextfloat:" + rand.nextFloat());
        return rand.nextFloat();
    }
    
    public int hashCode() {
    	long time = new Date().getTime();
    	String str = "Nexus";
        return (new HashCodeBuilder()
                .append(time+((time & 63l) << 57)).append(str).toHashCode());
    }


    private static float linearInterpolate(float l, float h, float partial) {
        float t2 = (1F - MathHelper.cos((float) (partial * Math.PI))) / 2F;
        return(l * (1F - t2) + h * t2);
    }
    
}
