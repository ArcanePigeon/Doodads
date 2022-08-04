package org.cloudwarp.doodads.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.CrackParticle;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.cloudwarp.doodads.Doodads;
import org.cloudwarp.doodads.client.renderer.PebbleEntityRenderer;
import org.cloudwarp.doodads.client.renderer.RubberBandEntityRenderer;
import org.cloudwarp.doodads.particle.DCrackParticles;
import org.cloudwarp.doodads.registry.DEntities;
import org.cloudwarp.doodads.registry.DModelPredicateProvider;
import org.cloudwarp.doodads.registry.DParticles;

@Environment(EnvType.CLIENT)
public class DoodadsClient implements ClientModInitializer {
	public static final Identifier PEBBLE_PACKET_ID = Doodads.id("pebble_spawn_packet");

	@Override
	public void onInitializeClient () {
		ClientPlayNetworking.registerGlobalReceiver(Doodads.id("doodads_config_packet"), (client, networkHandler, data, sender) -> {
			NbtCompound tag = data.readNbt();
			client.execute(() -> Doodads.loadedConfig = Doodads.nbtToConfig(tag));
		});
		EntityRendererRegistry.register(DEntities.PEBBLE_ENTITY, PebbleEntityRenderer::new);
		EntityRendererRegistry.register(DEntities.RUBBER_BAND_ENTITY, RubberBandEntityRenderer::new);
		ParticleFactoryRegistry.getInstance().register(DParticles.PEBBLE_PARTICLE, new DCrackParticles.PebbleFactory());
		DModelPredicateProvider.init();
	}
}
