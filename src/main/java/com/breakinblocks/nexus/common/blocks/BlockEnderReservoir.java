package com.breakinblocks.nexus.common.blocks;

import java.util.List;

import com.breakinblocks.nexus.common.tiles.TileEnderReservoir;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

@SuppressWarnings("deprecation")
public class BlockEnderReservoir extends Block implements ITileEntityProvider {

	public BlockEnderReservoir() {
		super(Material.IRON);
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEnderReservoir();
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEnderReservoir();
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		boolean res;
		TileEntity te = worldIn.getTileEntity(pos);
		if (te == null) {
			return false;
		}
		if (!(te instanceof TileEnderReservoir)) {
			return false;
		}
		TileEnderReservoir Er = (TileEnderReservoir) te;
		if (Er != null) {
			res = Er.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
			if (res)
				return true;
		}
		return false;
	}

	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		FluidStack fluidStack = TileEnderReservoir.getFluidFromItemStack(stack);

		if (fluidStack != null) {
			Fluid fluid = fluidStack.getFluid();
			String unlocalizedName;
			if (fluid == FluidRegistry.WATER) {
				unlocalizedName = "tile.water.name";
			} else if (fluid == FluidRegistry.LAVA) {
				unlocalizedName = "tile.lava.name";
			} else {
				unlocalizedName = fluid.getUnlocalizedName(fluidStack);
			}

			int capacity = Integer.MAX_VALUE;
			tooltip.add("Tank: " + new Object[] {
					I18n.translateToLocal(unlocalizedName) + " [" + fluidStack.amount + " / " + capacity + "]" });
		}

	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		return false;

	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		return Lists.newArrayList();
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te != null && !worldIn.isRemote && !player.isCreative() && te instanceof TileEnderReservoir) {
			TileEnderReservoir Er = (TileEnderReservoir) te;
			if (Er.tank.getFluid() != null) {
				ItemStack itemNBT = Er.getDropWithNBT();
				EntityItem entityitem = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), itemNBT);
				worldIn.spawnEntity(entityitem);
			} else {
				Item itemDrop = Item.getItemFromBlock(this);
				ItemStack stackDrop = new ItemStack(itemDrop);
				EntityItem entityitem = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), stackDrop);
				worldIn.spawnEntity(entityitem);

			}
		}

		super.onBlockHarvested(worldIn, pos, state, player);
	}

}
