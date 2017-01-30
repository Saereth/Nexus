package com.breakinblocks.nexus.common.tiles;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.breakinblocks.nexus.common.network.CustomInfoPacket;
import com.breakinblocks.nexus.common.network.NetworkController;
import com.breakinblocks.nexus.common.registry.ModBlocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.items.ItemHandlerHelper;

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

	public static FluidStack getFluidFromItemStack(ItemStack stack) {
        if(stack == null) {
            return null;
        } else {
            NBTTagCompound tagCompound = stack.getTagCompound();
            if(tagCompound != null && tagCompound.hasKey("Fluid")) {
                NBTTagCompound fluidNBT = tagCompound.getCompoundTag("Fluid");
                return FluidStack.loadFluidStackFromNBT(fluidNBT);
            } else {
                return null;
            }
            
            
            
        }
        
	}
	
	
	
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
    	System.out.println("Block activated!");
    	if(worldIn.isRemote) {
            return true;
        } else if(this.handleFluids(worldIn, playerIn, hand, heldItem, side)) {
            return true;
        } else {
            FluidStack fluidStack = this.tank.getFluid();
            if(fluidStack != null) {
            	playerIn.sendMessage(new TextComponentTranslation("Fluid: [" + this.tank.getFluid().getUnlocalizedName() + "] Current Value: " + this.tank.getFluidAmount()));
            }
            else {
            	playerIn.sendMessage(new TextComponentTranslation("Fluid: [None]"));
            }
        }    
        
        return false;
    }
    
    
    @Nullable
    public IFluidHandler getFluidHandler(EnumFacing facing) {
        return this.tank;
    }
    
    public boolean handleFluids(World worldIn, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side) {
        IFluidHandler handler = this.getFluidHandler(side);
        if(handler == null) {
            return false;
        } else {
            IFluidHandler container = FluidUtil.getFluidHandler(heldItem);
            if(container != null) {
                if(worldIn.isRemote) {
                    return true;
                }

                if(this.attemptDrain(playerIn, hand, heldItem, handler) || this.attemptFill(playerIn, hand, heldItem, handler)) {
                    return true;
                }
            }

            return false;
        }
    }
    
    public boolean isEmpty(@Nonnull ItemStack stack) {
        return stack.stackSize == 0;
    }
    
    public boolean attemptFill(EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, IFluidHandler handler) {
        ItemStack copy = heldItem.copy();
        copy.stackSize = 1;
        IFluidHandler container = FluidUtil.getFluidHandler(copy);
        if(container == null) {
            return false;
        } else {
            FluidStack drain = container.drain(1000, false);
            if(drain == null) {
                return false;
            } else {
                int fill = handler.fill(drain, false);
                if(fill == 0) {
                    return false;
                } else {
                    copy = heldItem.copy();
                    container = FluidUtil.getFluidHandler(copy);
                    if(container != null) {
                        handler.fill(container.drain(fill, true), true);
                    }

                    if(copy.stackSize != 0) {
                        if(this.isEmpty(heldItem)) {
                        	playerIn.setHeldItem(hand, copy);
                        } else if(ItemHandlerHelper.canItemStacksStack(copy, heldItem)) {
                        	++heldItem.stackSize;
                        	playerIn.setHeldItem(hand, heldItem);
                        } else {
                        	playerIn.inventory.addItemStackToInventory(copy);
                        }
                    }

                    return true;
                }
            }
        }
    }
    
    
    public boolean attemptDrain(EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, IFluidHandler handler) {
        ItemStack copy = heldItem.copy();
        copy.stackSize = 1;
        IFluidHandler container = FluidUtil.getFluidHandler(copy);
        if(container == null) {
            return false;
        } else {
            FluidStack toDrain = handler.drain(1000, false);
            if(toDrain == null) {
                return false;
            } else {
                int fill = container.fill(toDrain, false);
                if(fill == 0) {
                    return false;
                } else {
                    copy = heldItem.copy();
                    container = FluidUtil.getFluidHandler(copy);
                    if(container != null) {
                        container.fill(handler.drain(fill, true), true);
                    }

                    if(copy.stackSize != 0) {
                        if(this.isEmpty(heldItem)) {
                            playerIn.setHeldItem(hand, copy);
                        } else if(ItemHandlerHelper.canItemStacksStack(copy, heldItem)) {
                            ++heldItem.stackSize;
                            playerIn.setHeldItem(hand, heldItem);
                        } else {
                            playerIn.inventory.addItemStackToInventory(copy);
                        }
                    }

                    return true;
                }
            }
        }
    }




}
