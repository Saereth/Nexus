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
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class TileFloodgate extends TileBase implements IFluidHandler, ITickable {
	private static final EnumFacing[] offsets = new EnumFacing[5];
	private static final int DEPTH_MAX = 8;
	private static final int DEPTH_BASE = 3;
	private static final double DEPTH_WAIT = 128.0;
	private static final int DEPTH_COOLDOWN = (int) (DEPTH_WAIT * Math.pow(2, DEPTH_MAX))-1;
	private static final double LOGTWO = Math.log10(2);
	private static long lastcheck = 0;
	@CapabilityInject(IFluidHandler.class)
	static Capability<IFluidHandler> FLUIDCAPABILITY = null;

	static {
		offsets[0] = EnumFacing.DOWN;
		for (EnumFacing face : EnumFacing.HORIZONTALS)
			offsets[face.getIndex() - 1] = face;

	}

	private TreeSet<BlockPos> queue = new TreeSet<>();
	private Set<BlockPos> visited = new HashSet<>();
	private FluidTank buffer = new FluidTank(Fluid.BUCKET_VOLUME * 2);
	private int cooldown = 0;

	@Override
	public void update() {
		if (world.isRemote || buffer.getFluid() == null || buffer.drain(Fluid.BUCKET_VOLUME, false) == null || buffer.drain(Fluid.BUCKET_VOLUME, false).amount != Fluid.BUCKET_VOLUME)
			return;

		rebuildQueue(Math.log10(((cooldown++ % DEPTH_COOLDOWN) + 1)/DEPTH_WAIT)  / LOGTWO);

		BlockPos newpos;
		if ((newpos = queue.pollFirst()) != null) {
			IBlockState state = world.getBlockState(newpos);
			Block block = state.getBlock();
			if (block == Blocks.AIR || ((block instanceof IFluidBlock || block instanceof BlockStaticLiquid) && state.getValue(BlockStaticLiquid.LEVEL) != 0)) {
				world.setBlockState(newpos, buffer.getFluid().getFluid().getBlock().getDefaultState());
				//				drain(Fluid.BUCKET_VOLUME, true);
			}
		}
		super.update();
	}

	private void rebuildQueue(double depth) {
		if (depth % 1 != 0 || depth < 0)
			return;
		System.out.println("Waited " + (System.currentTimeMillis()/1000l-lastcheck));
		lastcheck = System.currentTimeMillis()/1000l;
		queue.clear();
		visited.clear();
		queueAdjacent(this.pos, (int) Math.pow(DEPTH_BASE,depth));
	}

	private void queueAdjacent(BlockPos pos, int depth) {
		if (depth <= 0)
			return;

		visited.add(pos);
		for (EnumFacing face : offsets) {
			BlockPos newpos = pos.offset(face);
			if (!visited.contains(newpos)) {
				Block block = world.getBlockState(newpos).getBlock();
				if (block == Blocks.AIR || block instanceof IFluidBlock || block instanceof BlockStaticLiquid) {
					if (block == Blocks.AIR || (block instanceof IFluidBlock || block instanceof BlockStaticLiquid) && world.getBlockState(newpos).getValue(BlockStaticLiquid.LEVEL) != 0)
						queue.add(newpos);
					queueAdjacent(newpos, depth - 1);
				}
			}
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
