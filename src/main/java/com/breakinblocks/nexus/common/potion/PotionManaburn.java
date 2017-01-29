package com.breakinblocks.nexus.common.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import org.lwjgl.util.*;
import org.lwjgl.util.Color;

import java.awt.*;

public class PotionManaburn extends Potion {
	public static final int HARMAFTER=3600;

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
}
