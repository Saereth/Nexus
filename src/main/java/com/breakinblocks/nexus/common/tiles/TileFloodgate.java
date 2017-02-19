package com.breakinblocks.nexus.common.tiles;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;
import java.util.*;

public class TileFloodgate extends TileBase implements IFluidHandler, ITickable {
	private static final EnumFacing[] OFFSETS = new EnumFacing[5];
	private static final int[] REBUILD_DELAY = new int[8];
	@CapabilityInject(IFluidHandler.class)
	static Capability<IFluidHandler> FLUIDCAPABILITY = null;
	private static long lastcheck = 0;

	static {
		OFFSETS[0] = EnumFacing.DOWN;
		for (EnumFacing face : EnumFacing.HORIZONTALS)
			OFFSETS[face.getIndex() - 1] = face;
		for (int i = 0; i < 8; i++)
			REBUILD_DELAY[i] = (int) Math.pow(2, i) * 128;

	}

	private TreeSet<BlockPos> fillqueue = new TreeSet<>();
	private HashSet<BlockPos> visited = new HashSet<>();
	private LinkedList<BlockPos> fluidsqueue = new LinkedList<>();
	private FluidTank buffer = new FluidTank(Fluid.BUCKET_VOLUME * 2);
	private int tick= 0;
	private int cooldown  = 0;

	@Override
	public void update() {
		if (world.isRemote || buffer.getFluid() == null || buffer.drain(Fluid.BUCKET_VOLUME, false) == null || buffer.drain(Fluid.BUCKET_VOLUME, false).amount != Fluid.BUCKET_VOLUME)
			return;

		if (tick%20==0)
		try {
			System.out.printf("Waited %d %d %d %d\n", (System.currentTimeMillis() / 1000l - lastcheck), tick, cooldown,REBUILD_DELAY[cooldown%8]);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (tick++ % REBUILD_DELAY[cooldown % 8]==0) {
			cooldown++;
			rebuildQueue();
		}

		BlockPos newpos;
		if ((newpos = fillqueue.pollFirst()) != null) {
			IBlockState state = world.getBlockState(newpos);
			Block block = state.getBlock();
			if (block == Blocks.AIR || ((block instanceof IFluidBlock || block instanceof BlockStaticLiquid) && state.getValue(BlockStaticLiquid.LEVEL) != 0)) {
				world.setBlockState(newpos, buffer.getFluid().getFluid().getBlock().getDefaultState());
				//				drain(Fluid.BUCKET_VOLUME, true);
				cooldown=0;
			}
		}
		super.update();
	}

	private void rebuildQueue() {
		System.out.println("Waited " + (System.currentTimeMillis() / 1000l - lastcheck));
		lastcheck = System.currentTimeMillis() / 1000l;
		fillqueue.clear();
		fluidsqueue.clear();
		visited.clear();
		queueAdjacent(this.pos);
		queuePopulate();
	}

	private void queueAdjacent(BlockPos pos) {
		for (EnumFacing face : OFFSETS) {
			BlockPos newpos = pos.offset(face);
			if (visited.add(newpos) && newpos.getDistance(this.pos.getX(),this.pos.getY(),this.pos.getZ()) < 64*64) {
				Block block = world.getBlockState(newpos).getBlock();
				if (block instanceof IFluidBlock || block instanceof BlockStaticLiquid)
					fluidsqueue.add(newpos);
				if (block == Blocks.AIR || block instanceof IFluidBlock || block instanceof BlockStaticLiquid)
					fillqueue.add(newpos);
			}
		}
	}

	private void queuePopulate() {
		while (!fluidsqueue.isEmpty()) {
			LinkedList<BlockPos> fluidsqueuecopy = fluidsqueue;
			fluidsqueue = new LinkedList<>();
			fluidsqueuecopy.forEach(this::queueAdjacent);
		}
	}

	@Nullable
	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return buffer.drain(maxDrain, doDrain);
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[0];
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		return buffer.fill(resource, doFill);
	}

	@Nullable
	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		return buffer.drain(resource, doDrain);
	}

	public String serialize() {
		if (buffer.getFluid() == null)
			return "nothing";
		return buffer.getFluid().getFluid().getName() + " : " + buffer.getFluidAmount() + " / " + buffer.getCapacity();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == FLUIDCAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == FLUIDCAPABILITY) {
			return (T) buffer;
		}
		return super.getCapability(capability, facing);
	}
}
