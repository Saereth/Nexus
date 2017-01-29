package com.breakinblocks.nexus.common.potion;

import com.breakinblocks.nexus.Nexus;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionManaburn extends Potion {
	public static final int HARMAFTER=1200;
	static final ResourceLocation rl = new ResourceLocation(Nexus.MODID,"textures/misc/potions.png");
	DamageSource burnDamage = new DamageSource("%1$s was immoliated in a blaze of mana.").setDamageIsAbsolute().setDamageBypassesArmor();
	
	@Override
	public boolean hasStatusIcon() {
		return true;
	}

	public PotionManaburn() {
		super(true, 0xFFFFFF);
		this.setIconIndex(0, 0);
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return duration >= HARMAFTER;
	}
	

	@Override
	public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
		entityLivingBaseIn.attackEntityFrom(burnDamage,2);
	}


	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex() {
		Minecraft.getMinecraft().renderEngine.bindTexture(rl);
		return super.getStatusIconIndex();
	}

}
