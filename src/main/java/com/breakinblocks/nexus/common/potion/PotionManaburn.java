package com.breakinblocks.nexus.common.potion;

import com.breakinblocks.nexus.Nexus;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionManaburn extends Potion {
	public static final int HARMAFTER=3600;

	@Override
	public boolean hasStatusIcon() {
		return super.hasStatusIcon();
	}

	public static final DamageSource burn = new DamageSource(Nexus.MODID+".manaburn").setDamageAllowedInCreativeMode().setDamageBypassesArmor().setDamageIsAbsolute();

	public PotionManaburn() {
		super(true, 0xFFFFFF);
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return duration >= HARMAFTER;
	}

	@Override
	public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
		System.out.println("boi");
		entityLivingBaseIn.attackEntityFrom(DamageSource.outOfWorld,2);
	}


	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex() {
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(Nexus.MODID, "textures/misc/potions.png"));
		return 0;
	}

}
