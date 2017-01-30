package com.breakinblocks.nexus.common.registry;

import java.lang.reflect.Constructor;

import com.breakinblocks.nexus.common.network.ISimplePacket;
import com.breakinblocks.nexus.common.network.NetworkController;
import com.breakinblocks.nexus.common.network.PacketWrapper;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@SuppressWarnings("rawtypes")
public class PacketEvent extends Event {
	SimpleNetworkWrapper wrapper;

	public PacketEvent(SimpleNetworkWrapper wrapper) {
		this.wrapper = wrapper;
	}

	public void registerPacket(Class<? extends ISimplePacket> packet, Side processingSide) {
		int id = getNextID();
		if (packet.getName() == ISimplePacket.class.getName()) {
			throw new RuntimeException("Cannot register a INetworkPacket, please register a child of this");
		}
		boolean hasEmptyConstructor = false;
		for ( Constructor constructor : packet.getConstructors()) {
			if (constructor.getParameterCount() == 0) {
				hasEmptyConstructor = true;
			}
		}
		if (!hasEmptyConstructor) {
			throw new RuntimeException("The packet " + packet.getName() + " does not have an empty constructor");
		}
		NetworkController.packetHashMap.put(id, packet);
		NetworkController.packetHashMapReverse.put(packet, id);
		wrapper.registerMessage(PacketWrapper.PacketWrapperHandler.class, PacketWrapper.class, id, processingSide);
	}

	public static int getNextID() {
		return NetworkController.packetHashMap.size() + 1;
	}
}
