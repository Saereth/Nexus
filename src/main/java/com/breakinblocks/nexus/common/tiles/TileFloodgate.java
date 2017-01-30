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
	private static final EnumFacing[] offsets = new EnumFacing[5];
	private static final int MAX_DEPTH = 32;
	@CapabilityInject(IFluidHandler.class)
	static Capability<IFluidHandler> FLUIDCAPABILITY = null;

	static {
		offsets[0] = EnumFacing.DOWN;
		for (EnumFacing face : EnumFacing.HORIZONTALS)
			offsets[face.getIndex() - 1] = face;

	}

	private TreeMap<Integer, Deque<BlockPos>> queue = new TreeMap<>();
	private Set<BlockPos> visited = new HashSet<BlockPos>();
	private FluidTank buffer = new FluidTank(Fluid.BUCKET_VOLUME * 10);
	private int cooldown = 0;

	@Override
	public void update() {
		if (world.isRemote)
			return;
		FluidStack drained;
		if ((drained = drain(Fluid.BUCKET_VOLUME, false)) == null || drained.amount < Fluid.BUCKET_VOLUME)
			return;

		BlockPos newpos;
		if ((newpos = getNextSpot()) != null ) {
			IBlockState state = world.getBlockState(newpos);
			if (state != null && state.getBlock() != null) {
				Block block = state.getBlock();
				if (block == Blocks.AIR || ((block instanceof IFluidBlock || block instanceof BlockStaticLiquid) && state.getValue(BlockStaticLiquid.LEVEL) != 0)) {
					world.setBlockState(newpos, buffer.getFluid().getFluid().getBlock().getDefaultState());
					drain(Fluid.BUCKET_VOLUME, true);

				}
			}
		} else {
			rebuildQueue();
		}
		super.update();
	}

	private BlockPos getNextSpot() {
		if (queue.isEmpty() || queue.firstEntry().getValue() == null)
			return null;
		return queue.firstEntry().getValue().pollFirst();
	}

	private void rebuildQueue() {
		queue.clear();
		visited.clear();
		queueAdjacent(this.pos,0);
	}

	private Deque<BlockPos> getLayer(int layer) {
		Deque<BlockPos> get = queue.get(layer);
		if (get == null) {
			get = new LinkedList<>();
			queue.put(layer, get);
		}
		return get;
	}

	private void queueAdjacent(BlockPos pos, int depth) {
		if (depth > MAX_DEPTH)
			return;
		for (EnumFacing face : offsets) {
			BlockPos newpos = pos.offset(face);
			if (!visited.contains(newpos)) {
				visited.add(newpos);
				Block block = world.getBlockState(newpos).getBlock();
				if (block == Blocks.AIR || block instanceof IFluidBlock || block instanceof BlockStaticLiquid) {
					getLayer(newpos.getY()).add(newpos);
					queueAdjacent(newpos, depth+1);
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
