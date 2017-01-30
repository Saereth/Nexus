package com.breakinblocks.nexus.common.network;

import java.io.IOException;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public interface ISimplePacket<T> {
	void writeData(PacketBuffer buffer) throws IOException;
	void readData(PacketBuffer buffer) throws IOException;
	void processData(T message, MessageContext context);
}
