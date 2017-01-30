package com.breakinblocks.nexus.common.blocks;

import com.breakinblocks.nexus.common.tiles.TileEnderReservoir;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

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

}
