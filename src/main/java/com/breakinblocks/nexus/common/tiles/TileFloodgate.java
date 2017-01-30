package com.breakinblocks.nexus.common.tiles;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;

public class TileFloodgate extends TileBase implements IFluidHandler, ITickable {
	@CapabilityInject(IFluidHandler.class)
	static Capability<IFluidHandler> FLUIDCAPABILITY = null;

	FluidTank buffer = new FluidTank(10000);

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

	@Override
	public void update() {
		FluidStack drained;
		if ((drained=drain(1000,false)) != null && drained.amount==1000 && world.isAirBlock(pos.down())) {
			world.setBlockState(pos.down(),buffer.getFluid().getFluid().getBlock().getDefaultState());
			drain(1000,true);
		}
		super.update();
	}

	@Nullable
	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		return buffer.drain(resource, doDrain);
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
