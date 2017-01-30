package com.breakinblocks.nexus.common.items;

import java.awt.List;

import com.breakinblocks.nexus.common.registry.ModBlocks;
import com.breakinblocks.nexus.common.tiles.TileEnderReservoir;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockEnderReservoir extends ItemBlock {

	public ItemBlockEnderReservoir(Block block) {
		super(block);
	}
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) 
	{
		if (!world.setBlockState(pos, ModBlocks.ENDERRESERVOIR.getDefaultState()))
		{
			return false;
		}
		if (stack != null && stack.hasTagCompound()) 
		{
			((TileEnderReservoir) world.getTileEntity(pos)).readFromNBTWithoutCoords(stack.getTagCompound().getCompoundTag("tileEntity"));
		}
		return true;	
	}


	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		if (stack != null && stack.hasTagCompound())
		{
			if(stack.getTagCompound().getCompoundTag("tileEntity").getCompoundTag("TileEnderReservoir") != null)
			{
				list.add(stack.getTagCompound().getCompoundTag("tileEntity").getCompoundTag("TileEnderReservoir").getInteger("Amount")+" MB of "
                        + stack.getTagCompound().getCompoundTag("tileEntity").getCompoundTag("TileEnderReservoir").getString("FluidName").toUpperCase());
			}
		}
	}

}
