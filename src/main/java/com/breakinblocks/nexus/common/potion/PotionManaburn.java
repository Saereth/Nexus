package com.breakinblocks.nexus.common.potion;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.breakinblocks.nexus.Nexus;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionManaburn extends Potion {
	public static final int HARMAFTER=1200;
	@SideOnly(Side.CLIENT)
	static final ResourceLocation rl = new ResourceLocation(Nexus.MODID,"textures/misc/potions.png");
	DamageSource burnDamage = new DamageSource("nexus.manaburn").setDamageIsAbsolute().setDamageBypassesArmor();
	
	@Override
	public boolean hasStatusIcon() {
		return false;
	}

	public PotionManaburn() {
		super(true, 0xFFFFFF);
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
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
        Tessellator tes = Tessellator.getInstance();
        double wh = 18;
        double offsetX = 6;
        double offsetY = 7;
        Color c = new Color(getLiquidColor());
        float r =   ((float) c.getRed())   / 255F;
        float g = ((float) c.getGreen()) / 255F;
        float b =  ((float) c.getBlue())  / 255F;

        mc.renderEngine.bindTexture(rl);
        VertexBuffer vb = tes.getBuffer();
        vb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);

        vb.pos(x + offsetX,      y + offsetY,      0).tex(0, 0).color(r, g, b, 1F).endVertex();
        vb.pos(x + offsetX,      y + offsetY + wh, 0).tex(0, 1).color(r, g, b, 1F).endVertex();
        vb.pos(x + offsetX + wh, y + offsetY + wh, 0).tex(1, 1).color(r, g, b, 1F).endVertex();
        vb.pos(x + offsetX + wh, y + offsetY,      0).tex(1, 0).color(r, g, b, 1F).endVertex();

        tes.draw();
        
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
        Tessellator tes = Tessellator.getInstance();
        double wh = 18;
        double offsetX = 3;
        double offsetY = 3;
        Color c = new Color(getLiquidColor());
        float r =   ((float) c.getRed())   / 255F;
        float g = ((float) c.getGreen()) / 255F;
        float b =  ((float) c.getBlue())  / 255F;

        mc.renderEngine.bindTexture(rl);
        VertexBuffer vb = tes.getBuffer();
        vb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);

        vb.pos(x + offsetX,      y + offsetY,      0).tex(0, 0).color(r, g, b, alpha).endVertex();
        vb.pos(x + offsetX,      y + offsetY + wh, 0).tex(0, 1).color(r, g, b, alpha).endVertex();
        vb.pos(x + offsetX + wh, y + offsetY + wh, 0).tex(1, 1).color(r, g, b, alpha).endVertex();
        vb.pos(x + offsetX + wh, y + offsetY,      0).tex(1, 0).color(r, g, b, alpha).endVertex();

        tes.draw();
        
    }
    
}
