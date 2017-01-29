package com.breakinblocks.nexus.common.blocks;

import com.breakinblocks.nexus.common.potion.PotionManaburn;
import com.breakinblocks.nexus.common.registry.ModPotions;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FluidBlockMana extends BlockFluidClassic {
	public FluidBlockMana(Fluid fluid, Material material) {
		super(fluid, material);
		setDefaultState(this.blockState.getBaseState().withProperty(LEVEL, 0));
		setUnlocalizedName(fluid.getName());
		setRegistryName(fluid.getName());
		this.slipperiness = 1.2F;
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
		System.out.println("Check collision properties of: " + this.getFluid().getUnlocalizedName());
		PotionEffect burn = ((EntityLivingBase) entityIn).getActivePotionEffect(ModPotions.MANABURN);
		((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(ModPotions.MANABURN, burn == null ? 200 : burn.getDuration() + 2, 0, true, false));
		switch (this.getFluid().getUnlocalizedName()) {
			case "fluid.manawhite":
				((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 200, 1, true, true));
				break;
			case "fluid.manablack":
				((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.WITHER, 200, 1, true, true));
				break;
			case "fluid.manared":
				((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 200, 1, true, true));
				break;
			case "fluid.manablue":
				((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 200, 1, true, true));
				break;
			case "fluid.managreen":
				((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 1, true, true));
				break;
			case "fluid.manacolourless":
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


}


