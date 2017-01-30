package com.breakinblocks.nexus.common.util;

import com.breakinblocks.nexus.common.network.CustomInfoPacket;
import com.breakinblocks.nexus.common.network.NetworkController;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class TankBase extends FluidTank {

	private final String name;

	Fluid lastFluid;

	public TankBase(String name, int capacity, TileEntity tile) {
		super(capacity);
		this.name = name;
		this.tile = tile;
	}

	public boolean isEmpty() {
		return getFluid() == null || getFluid().amount <= 0;
	}

	public boolean isFull() {
		return getFluid() != null && getFluid().amount >= getCapacity();
	}

	public Fluid getFluidType() {
		return getFluid() != null ? getFluid().getFluid() : null;
	}

	@Override
	public final NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		NBTTagCompound tankData = new NBTTagCompound();
		super.writeToNBT(tankData);
		nbt.setTag(name, tankData);
		return nbt;
	}

	public void setFluidAmount(int amount) {
		if (fluid != null) {
			fluid.amount = amount;
		}
	}

	@Override
	public final FluidTank readFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey(name)) {
			setFluid(null);

			NBTTagCompound tankData = nbt.getCompoundTag(name);
			super.readFromNBT(tankData);
		}
		return this;
	}

	// TODO optimise
	public void compareAndUpdate() {
		if (tile == null || tile.getWorld().isRemote) {
			return;
		}
		if (lastFluid == null || (lastFluid != null && (this.getFluid() == null) || this.getFluid().getFluid() == null)
				|| (lastFluid != this.getFluid().getFluid())) {
			if (this.getFluid() == null) {
				lastFluid = null;
			} else {
				lastFluid = this.getFluid().getFluid();
			}
			NetworkController.sendToAllAround(new CustomInfoPacket(tile),
					new NetworkRegistry.TargetPoint(tile.getWorld().provider.getDimension(), tile.getPos().getX(),
							tile.getPos().getY(), tile.getPos().getZ(), 20));
		}
	}

}
