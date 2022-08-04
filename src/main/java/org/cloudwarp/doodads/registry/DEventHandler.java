package org.cloudwarp.doodads.registry;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import org.cloudwarp.doodads.Doodads;

public class DEventHandler {

	public static void registerEvents(){
		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			PacketByteBuf data = PacketByteBufs.create();
			data.writeNbt(Doodads.configToNBT());
			ServerPlayNetworking.send(handler.player, Doodads.id("doodads_config_packet"), data);
		});
	}
}
