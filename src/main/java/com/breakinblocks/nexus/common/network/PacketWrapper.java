package com.breakinblocks.nexus.common.network;

import java.io.IOException;

import org.apache.commons.lang3.Validate;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

@SuppressWarnings("rawtypes")
public class PacketWrapper implements IMessage {
	ISimplePacket packet;

	
	public PacketWrapper(ISimplePacket packet) {
		this.packet = packet;
	}

	public PacketWrapper() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			packet = NetworkController.packetHashMap.get(buf.readInt()).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		try {
			packet.readData(new PacketBuffer(buf));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		Validate.notNull(packet);
		buf.writeInt(NetworkController.packetHashMapReverse.get(packet.getClass()));
		try {
			packet.writeData(new PacketBuffer(buf));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static class PacketWrapperHandler implements IMessageHandler<PacketWrapper, IMessage> {

		@SuppressWarnings("unchecked")
		@Override
		public IMessage onMessage(PacketWrapper message, MessageContext ctx) {
			message.packet.processData(message.packet, ctx);
			return null;
		}


	}



}
