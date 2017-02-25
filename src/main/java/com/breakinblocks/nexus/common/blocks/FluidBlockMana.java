package com.breakinblocks.nexus.common.blocks;

import com.breakinblocks.nexus.common.Config;
import com.breakinblocks.nexus.common.registry.ModBlocks;
import com.breakinblocks.nexus.common.registry.ModPotions;
import com.breakinblocks.nexus.common.util.EnumMana;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidBlock;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;
import java.util.function.Consumer;

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
		super.updateTick(worldIn, pos, state, rand);
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
				checkBlockInteraction(worldIn, pos, Blocks.MOSSY_COBBLESTONE, Blocks.WATER);
				checkSpecialCase(worldIn, pos, Blocks.WHEAT, face ->
						ItemDye.applyBonemeal(new ItemStack(Items.DYE, EnumDyeColor.WHITE.getDyeDamage()), worldIn, pos.offset(face))
				); // Needs tweaking, currently only supports wheat and only on a block update
				break;
			case WHITE:
				checkBlockInteraction(worldIn, pos, Blocks.GRAVEL, Blocks.FLOWING_LAVA);
				checkBlockInteraction(worldIn, pos, Blocks.GRAVEL, Blocks.LAVA);
				checkBlockInteraction(worldIn, pos, Blocks.SAND, Blocks.FLOWING_WATER);
				checkBlockInteraction(worldIn, pos, Blocks.SAND, Blocks.WATER);
				checkSpecialCase(worldIn, pos, ModBlocks.BLOCKMANABLACK, face ->
						worldIn.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 5F, true)
				);
				break;
			case BLACK:
				checkBlockInteraction(worldIn, pos, Blocks.SOUL_SAND, Blocks.FLOWING_LAVA);
				checkBlockInteraction(worldIn, pos, Blocks.SOUL_SAND, Blocks.LAVA);
				checkBlockInteraction(worldIn, pos, Blocks.BROWN_MUSHROOM_BLOCK, Blocks.FLOWING_WATER);
				checkBlockInteraction(worldIn, pos, Blocks.BROWN_MUSHROOM_BLOCK, Blocks.WATER);
				break;
			case BLUE:
				checkBlockInteraction(worldIn, pos, Blocks.HARDENED_CLAY, Blocks.FLOWING_LAVA);
				checkBlockInteraction(worldIn, pos, Blocks.HARDENED_CLAY, Blocks.LAVA);
				checkBlockInteraction(worldIn, pos, Blocks.LEAVES, Blocks.FLOWING_WATER);
				checkBlockInteraction(worldIn, pos, Blocks.LEAVES, Blocks.WATER);
				checkBlockInteractionReplace(worldIn, pos, Blocks.CLAY, Blocks.SAND);
				checkSpecialCase(worldIn, pos, Blocks.CAULDRON, face ->
						worldIn.setBlockState(pos.offset(face), worldIn.getBlockState(pos.offset(face)).withProperty(BlockCauldron.LEVEL, 3), 2)
				);
				break;
			case COLOURLESS:
				checkBlockInteraction(worldIn, pos, Blocks.END_STONE, Blocks.FLOWING_LAVA);
				checkBlockInteraction(worldIn, pos, Blocks.END_STONE, Blocks.LAVA);
				checkBlockInteraction(worldIn, pos, Blocks.STONE, Blocks.FLOWING_WATER);
				checkBlockInteraction(worldIn, pos, Blocks.STONE, Blocks.WATER);
				break;
			default:
				break;
		}


	}

	void checkBlockEvaporate(World world, BlockPos pos) {
		int next = world.rand.nextInt((int) (1 / Config.fluidmanaconsumechance));
		System.out.println(next);
		if (next == 0) {
			BlockPos newpos = getSourcePos(world, pos, null);
			if (newpos != null) {
				world.setBlockToAir(newpos);
//				world.setBlockState(newpos,Blocks.BEDROCK.getDefaultState());
				world.playSound(null, newpos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1f, 1f);
			}
		}
	}

	BlockPos getSourcePos(World world, BlockPos pos, HashSet<BlockPos> visited) {
		if (visited == null)
			visited = new HashSet<>();
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		if (block instanceof IFluidBlock || block instanceof BlockStaticLiquid) {
			int ourLevel = state.getValue(BlockStaticLiquid.LEVEL);
			if (ourLevel == 0) {
				return pos;
			}
			for (EnumFacing offset : EnumFacing.VALUES) {
				if (visited.add(pos.offset(offset))) {
					state = world.getBlockState(pos.offset(offset));
					block = state.getBlock();
					if (block == this && (block instanceof IFluidBlock || block instanceof BlockStaticLiquid)) {
						BlockPos rtn = getSourcePos(world, pos.offset(offset),visited);
						if (rtn != null)
							return rtn;
					}
				}
			}
		}
		return null;
	}

	void checkBlockInteraction(World world, BlockPos pos, Block target, Block fluid) {
		for (EnumFacing offset : EnumFacing.VALUES) {
			if (world.getBlockState(pos.offset(offset)).getBlock() == fluid) {
				checkBlockEvaporate(world, pos);
				if (target == Blocks.LEAVES) {
					world.setBlockState(pos, Blocks.LEAVES.getDefaultState().withProperty(BlockLeaves.DECAYABLE, false));
				} else {
					world.setBlockState(pos, target.getDefaultState());
				}
			}
		}
		return;
	}

	void checkBlockInteractionReplace(World world, BlockPos pos, Block target, Block fluid) {
		for (EnumFacing offset : EnumFacing.VALUES) {
			if (world.getBlockState(pos.offset(offset)).getBlock() == fluid) {
				checkBlockEvaporate(world, pos);
				if (target == Blocks.LEAVES) {
					world.setBlockState(pos.offset(offset), Blocks.LEAVES.getDefaultState().withProperty(BlockLeaves.DECAYABLE, false));
				} else {
					world.setBlockState(pos.offset(offset), target.getDefaultState());
				}
			}
		}
		return;
	}

	void checkSpecialCase(World world, BlockPos pos, Block fluid, Consumer<EnumFacing> exec) {
		for (EnumFacing offset : EnumFacing.VALUES) {
			if (world.getBlockState(pos.offset(offset)).getBlock() == fluid) {
				checkBlockEvaporate(world, pos);
				exec.accept(offset);
			}
		}
		return;
	}

}
