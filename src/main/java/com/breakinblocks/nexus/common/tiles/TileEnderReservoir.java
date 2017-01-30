package com.breakinblocks.nexus.common.tiles;

import com.breakinblocks.nexus.common.network.CustomInfoPacket;
import com.breakinblocks.nexus.common.network.NetworkController;
import com.breakinblocks.nexus.common.registry.ModBlocks;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class TileEnderReservoir extends TileBase implements ITickable 
{
	public int capacity = Integer.MAX_VALUE;
	public FluidTank tank = new FluidTank(capacity);
	

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) 
	{
		readFromNBTWithoutCoords(tagCompound);
        super.readFromNBT(tagCompound);
    }

	public void readFromNBTWithoutCoords(NBTTagCompound tagCompound) 
	{
		tank.readFromNBT(tagCompound);
		
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		writeToNBTWithoutCoords(compound);
		return super.writeToNBT(compound);
	}

	public void writeToNBTWithoutCoords(NBTTagCompound tagCompound) 
	{
		tank.writeToNBT(tagCompound);
		
	}

	@SuppressWarnings("rawtypes")
	public Packet getDescriptionPacket() 
	{
		NBTTagCompound nbtTag = new NBTTagCompound();
		writeToNBT(nbtTag);
		return new SPacketUpdateTileEntity(this.pos, 0, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) 
	{
		readFromNBT(packet.getNbtCompound());
	}

	@Override
	public void update() 
	{
		super.update();
	}


	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
		{
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
		{
			return (T) tank;
		}

		return super.getCapability(capability, facing);
	}



	public ItemStack getDropWithNBT() 
	{
		NBTTagCompound tileEntity = new NBTTagCompound();
		ItemStack dropStack = new ItemStack(ModBlocks.ENDERRESERVOIR, 1);
		writeToNBTWithoutCoords(tileEntity);
		dropStack.setTagCompound(new NBTTagCompound());
		dropStack.getTagCompound().setTag("tileEntity", tileEntity);
		return dropStack;
	}


	public void syncWithAll()
	{
		if (!world.isRemote)
		{
			NetworkController.sendToAllAround(new CustomInfoPacket(this), new NetworkRegistry.TargetPoint(world.provider.getDimension(), getPos().getX(), getPos().getY(), getPos().getZ(), 20));;
		}
	}




}
