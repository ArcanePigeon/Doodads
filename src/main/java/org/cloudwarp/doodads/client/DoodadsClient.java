package org.cloudwarp.doodads.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.util.Identifier;
import org.cloudwarp.doodads.Doodads;
import org.cloudwarp.doodads.client.renderer.PebbleEntityRenderer;
import org.cloudwarp.doodads.registry.DEntities;
import org.cloudwarp.doodads.registry.DModelPredicateProvider;

@Environment(EnvType.CLIENT)
public class DoodadsClient implements ClientModInitializer {
	public static final Identifier PEBBLE_PACKET_ID = Doodads.id("pebble_spawn_packet");
	@Override
	public void onInitializeClient () {
		EntityRendererRegistry.register(DEntities.PEBBLE_ENTITY, PebbleEntityRenderer::new);
		DModelPredicateProvider.init();
	}
}
