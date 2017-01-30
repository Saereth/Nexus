package com.breakinblocks.nexus.common.blocks;

import com.breakinblocks.nexus.common.registry.ModBlocks;
import com.breakinblocks.nexus.common.registry.ModPotions;
import com.breakinblocks.nexus.common.util.EnumMana;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class FluidBlockMana extends BlockFluidClassic {
	public FluidBlockMana(Fluid fluid, Material material) {
		super(fluid, material);
		setDefaultState(this.blockState.getBaseState().withProperty(LEVEL, 0));
		setUnlocalizedName(fluid.getName());
		setRegistryName(fluid.getName());

	}

	@Override
	protected boolean canFlowInto(IBlockAccess world, BlockPos pos) {
		return super.canFlowInto(world, pos);
	}

	@Override
	public Boolean isEntityInsideMaterial(IBlockAccess world, BlockPos blockpos, IBlockState iblockstate, Entity entity, double yToTest, Material materialIn, boolean testingHead) {
		AxisAlignedBB box = iblockstate.getBoundingBox(world, blockpos).offset(blockpos);
		AxisAlignedBB entityBox = entity.getEntityBoundingBox();
		return box.intersectsWith(entityBox) && materialIn.isLiquid();
	}

	@Override
	public boolean canDisplace(IBlockAccess world, BlockPos pos) {
		IBlockState bs = world.getBlockState(pos);
		if (bs.getMaterial().isLiquid()) {
			return false;
		}
		return super.canDisplace(world, pos);
	}

	@Override
	public boolean displaceIfPossible(World world, BlockPos pos) {
		IBlockState bs = world.getBlockState(pos);
		if (bs.getMaterial().isLiquid()) {
			return false;
		}
		return super.displaceIfPossible(world, pos);
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);

		if (!(entityIn instanceof EntityLivingBase))
			return;
		PotionEffect burn = ((EntityLivingBase) entityIn).getActivePotionEffect(ModPotions.MANABURN);
		((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(ModPotions.MANABURN, burn == null ? 200 : burn.getDuration() + 2, 0, true, false));

		switch (EnumMana.getFromFluid(this.getFluid())) {
			case WHITE:
				((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 200, 1, true, true));
				break;
			case BLACK:
				((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.WITHER, 200, 1, true, true));
				break;
			case RED:
				((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 200, 1, true, true));
				break;
			case BLUE:
				((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 200, 1, true, true));
				break;
			case GREEN:
				((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 1, true, true));
				break;
			case COLOURLESS:
			default:
				List<Potion> effectsToRemove = new ArrayList<>();
				Iterator<PotionEffect> iter = ((EntityLivingBase) entityIn).getActivePotionEffects().iterator();
				while (iter.hasNext()) {
					Potion potion = ((PotionEffect) iter.next()).getPotion();
					effectsToRemove.add(potion);
				}
				if (!worldIn.isRemote)
					for (Potion i : effectsToRemove)
						((EntityLivingBase) entityIn).removePotionEffect(i);

				break;

		}

	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		switch (EnumMana.getFromFluid(this.getFluid())) {
			case RED:
				checkBlockInteraction(worldIn, pos, Blocks.OBSIDIAN, Blocks.FLOWING_LAVA);
				checkBlockInteraction(worldIn, pos, Blocks.OBSIDIAN, Blocks.LAVA);
				checkBlockInteraction(worldIn, pos, Blocks.NETHERRACK, Blocks.FLOWING_WATER);
				checkBlockInteraction(worldIn, pos, Blocks.NETHERRACK, Blocks.WATER);
				break;
			case GREEN:
				checkBlockInteraction(worldIn, pos, Blocks.GRASS, Blocks.FLOWING_LAVA);
				checkBlockInteraction(worldIn, pos, Blocks.GRASS, Blocks.LAVA);
				checkBlockInteraction(worldIn, pos, Blocks.MOSSY_COBBLESTONE, Blocks.FLOWING_WATER);
				checkBlockInteraction(worldIn, pos, Blocks.MOSSY_COBBLESTONE, Blocks.FLOWING_WATER);
				break;
			case WHITE:
				checkBlockInteraction(worldIn, pos, Blocks.GRAVEL, Blocks.FLOWING_LAVA);
				checkBlockInteraction(worldIn, pos, Blocks.GRAVEL, Blocks.LAVA);
				checkBlockInteraction(worldIn, pos, Blocks.SAND, Blocks.FLOWING_WATER);
				checkBlockInteraction(worldIn, pos, Blocks.SAND, Blocks.FLOWING_WATER);
				checkFluidExplode(worldIn, pos, ModBlocks.BLOCKMANABLACK);
				break;
			case BLACK:
				checkBlockInteraction(worldIn, pos, Blocks.SOUL_SAND, Blocks.FLOWING_LAVA);
				checkBlockInteraction(worldIn, pos, Blocks.SOUL_SAND, Blocks.LAVA);
				checkBlockInteraction(worldIn, pos, Blocks.BROWN_MUSHROOM_BLOCK, Blocks.FLOWING_WATER);
				checkBlockInteraction(worldIn, pos, Blocks.BROWN_MUSHROOM_BLOCK, Blocks.FLOWING_WATER);
				break;
			case BLUE:
				checkBlockInteraction(worldIn, pos, Blocks.HARDENED_CLAY, Blocks.FLOWING_LAVA);
				checkBlockInteraction(worldIn, pos, Blocks.HARDENED_CLAY, Blocks.LAVA);
				checkBlockInteraction(worldIn, pos, Blocks.LEAVES, Blocks.FLOWING_WATER);
				checkBlockInteraction(worldIn, pos, Blocks.LEAVES, Blocks.FLOWING_WATER);
				break;
			case COLOURLESS:
				checkBlockInteraction(worldIn, pos, Blocks.END_STONE, Blocks.FLOWING_LAVA);
				checkBlockInteraction(worldIn, pos, Blocks.END_STONE, Blocks.LAVA);
				checkBlockInteraction(worldIn, pos, Blocks.STONE, Blocks.FLOWING_WATER);
				checkBlockInteraction(worldIn, pos, Blocks.STONE, Blocks.FLOWING_WATER);
				break;
			default:
				break;
		}

		super.updateTick(worldIn, pos, state, rand);
	}

	void checkBlockInteraction(World world, BlockPos pos, Block target, Block fluid) {
		for (EnumFacing offset : EnumFacing.VALUES) {
			if (world.getBlockState(pos.offset(offset)).getBlock() == fluid) {
				if (target == Blocks.LEAVES)
					world.setBlockState(pos, Blocks.LEAVES.getDefaultState().withProperty(BlockLeaves.DECAYABLE, false));
				else
					world.setBlockState(pos, target.getDefaultState());
				return;
			}
		}
		return;
	}

	void checkFluidExplode(World world, BlockPos pos, Block fluid) {
		for (EnumFacing offset : EnumFacing.VALUES) {
			if (world.getBlockState(pos.offset(offset)).getBlock() == fluid) {
				world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 5F, true);
				return;
			}
		}
		return;
	}

}
