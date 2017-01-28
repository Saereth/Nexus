package com.breakinblocks.nexus.common.items;

import com.breakinblocks.nexus.common.registry.ModBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemTablet extends Item {
	public ItemTablet() {
		setMaxStackSize(1);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		worldIn.setBlockState(pos.offset(facing), ModBlocks.BLOCKMANAGREEN.getDefaultState());
		return EnumActionResult.SUCCESS;
	}
}
