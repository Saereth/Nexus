package com.breakinblocks.nexus.common.network;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CustomInfoPacket implements ISimplePacket<CustomInfoPacket> {

	private BlockPos blockPos;
	private NBTTagCompound nbt;

	public CustomInfoPacket(BlockPos blockPos, NBTTagCompound nbt) {
		this.blockPos = blockPos;
		this.nbt = nbt;
	}

	public CustomInfoPacket() {
	}

	public CustomInfoPacket(TileEntity tileEntity) {
		this.blockPos = tileEntity.getPos();
		this.nbt = tileEntity.writeToNBT(new NBTTagCompound());
	}

	@Override
	public void writeData(PacketBuffer buffer) throws IOException {
		buffer.writeBlockPos(blockPos);
		buffer.writeCompoundTag(nbt);
	}

	@Override
	public void readData(PacketBuffer buffer) throws IOException {
		blockPos = buffer.readBlockPos();
		nbt = buffer.readCompoundTag();
	}

	@Override
	public void processData(CustomInfoPacket message, MessageContext context) {
		if (message.blockPos == null || message.nbt == null) {
			return;
		}
		World world = Minecraft.getMinecraft().world;
				
		if (world.isBlockLoaded(message.blockPos)) {
			TileEntity tileentity = world.getTileEntity(message.blockPos);
			if (tileentity != null && message.nbt != null) {
				tileentity.readFromNBT(message.nbt);
			}
		}
	}
	
}
