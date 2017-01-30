package com.breakinblocks.nexus.common.network;

import java.util.HashMap;

import com.breakinblocks.nexus.common.registry.PacketEvent;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;


@SuppressWarnings("rawtypes")
public class NetworkController {

	public static final SimpleNetworkWrapper NETWORK_WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel("nexus");

	public static HashMap<Integer, Class<? extends ISimplePacket>> packetHashMap = new HashMap<>();
	
	public static HashMap<Class<? extends ISimplePacket>, Integer> packetHashMapReverse = new HashMap<>();

	public static void load() {
		MinecraftForge.EVENT_BUS.post(new PacketEvent(NETWORK_WRAPPER));
	}

	public static void sendToServer(ISimplePacket packet) {
		if (!packetHashMap.containsValue(packet.getClass())) {
			throw new RuntimeException("Packet " + packet.getClass().getName() + " has not been registered");
		}
		NETWORK_WRAPPER.sendToServer(new PacketWrapper(packet));
	}

	public static void sendToAllAround(ISimplePacket packet, NetworkRegistry.TargetPoint point) {
		if (!packetHashMap.containsValue(packet.getClass())) {
			throw new RuntimeException("Packet " + packet.getClass().getName() + " has not been registered");
		}
		NETWORK_WRAPPER.sendToAllAround(new PacketWrapper(packet), point);
	}
}
