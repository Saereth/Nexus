package com.breakinblocks.nexus.common.blocks;

import com.breakinblocks.nexus.common.tiles.TileFloodgate;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

public class BlockFloodgate extends Block implements ITileEntityProvider {
	public BlockFloodgate() {
		super(Material.PISTON);
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}


	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		FluidStack in;
		TileEntity tile;
		if ((tile = worldIn.getTileEntity(pos)) != null && tile instanceof TileFloodgate)
		playerIn.sendMessage(new TextComponentString("Fluid: " + ((TileFloodgate) tile).serialize()));
		if (!worldIn.isRemote && (tile = worldIn.getTileEntity(pos)) != null && tile instanceof TileFloodgate && heldItem != null && (in = getFluidFromStack(heldItem)) != null) {
			((TileFloodgate) tile).fill(in, true);
			if (!playerIn.isCreative())
				heldItem.setItem(Items.BUCKET);
		}
		return true;
	}

	public FluidStack getFluidFromStack(ItemStack in) {
		if (in == null) {
			return null;
		} else {
			NBTTagCompound tagCompound = in.getTagCompound();
			if (tagCompound != null && tagCompound.hasKey("FluidName")) {
				return FluidStack.loadFluidStackFromNBT(tagCompound);
			} else {
				return null;
			}
		}
	}


	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileFloodgate();
	}
}
