package org.cloudwarp.doodads.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.particle.FireworksSparkParticle;
import net.minecraft.screen.PlayerScreenHandler;
import org.cloudwarp.doodads.Doodads;
import org.cloudwarp.doodads.entities.renderers.*;
import org.cloudwarp.doodads.models.*;
import org.cloudwarp.doodads.registry.DEntities;
import org.cloudwarp.doodads.registry.MSParticles;

@Environment(EnvType.CLIENT)
public class DoodadsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient () {
		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
			registry.register(Doodads.id("particle/plushie_particle"));
		}));

		ParticleFactoryRegistry.getInstance().register(MSParticles.PLUSHIE_PARTICLE, FireworksSparkParticle.ExplosionFactory::new);

		EntityRendererRegistry.register(DEntities.CAT_PLUSHIE, ((context) -> new CatPlushieEntityRenderer(context)));
		EntityModelLayerRegistry.registerModelLayer(CatPlushieModel.LAYER_LOCATION, CatPlushieModel::getTexturedModelData);

		EntityRendererRegistry.register(DEntities.PILLAGER_PLUSHIE, ((context) -> new PillagerPlushieEntityRenderer(context)));
		EntityModelLayerRegistry.registerModelLayer(PillagerPlushieModel.LAYER_LOCATION, PillagerPlushieModel::getTexturedModelData);

		EntityRendererRegistry.register(DEntities.STEVE_PLUSHIE, ((context) -> new StevePlushieEntityRenderer(context)));
		EntityModelLayerRegistry.registerModelLayer(StevePlushieModel.LAYER_LOCATION, StevePlushieModel::getTexturedModelData);

		EntityRendererRegistry.register(DEntities.VILLAGER_PLUSHIE, ((context) -> new VillagerPlushieEntityRenderer(context)));
		EntityModelLayerRegistry.registerModelLayer(VillagerPlushieModel.LAYER_LOCATION, VillagerPlushieModel::getTexturedModelData);

		EntityRendererRegistry.register(DEntities.WOLF_PLUSHIE, ((context) -> new WolfPlushieEntityRenderer(context)));
		EntityModelLayerRegistry.registerModelLayer(WolfPlushieModel.LAYER_LOCATION, WolfPlushieModel::getTexturedModelData);

		EntityRendererRegistry.register(DEntities.ZOMBIE_PLUSHIE, ((context) -> new ZombiePlushieEntityRenderer(context)));
		EntityModelLayerRegistry.registerModelLayer(ZombiePlushieModel.LAYER_LOCATION, ZombiePlushieModel::getTexturedModelData);

		EntityRendererRegistry.register(DEntities.AXOLOTL_PLUSHIE, ((context) -> new AxolotlPlushieEntityRenderer(context)));
		EntityModelLayerRegistry.registerModelLayer(AxolotlPlushieModel.LAYER_LOCATION, AxolotlPlushieModel::getTexturedModelData);

		EntityRendererRegistry.register(DEntities.CHICKEN_PLUSHIE, ((context) -> new ChickenPlushieEntityRenderer(context)));
		EntityModelLayerRegistry.registerModelLayer(ChickenPlushieModel.LAYER_LOCATION, ChickenPlushieModel::getTexturedModelData);

		EntityRendererRegistry.register(DEntities.COPPER_GOLEM_PLUSHIE, ((context) -> new CopperGolemPlushieEntityRenderer(context)));
		EntityModelLayerRegistry.registerModelLayer(CopperGolemPlushieModel.LAYER_LOCATION, CopperGolemPlushieModel::getTexturedModelData);

		EntityRendererRegistry.register(DEntities.DEFAULT_SCARECROW, ((context) -> new DefaultScarecrowEntityRenderer(context)));
		EntityModelLayerRegistry.registerModelLayer(DefaultScarecrowModel.LAYER_LOCATION, DefaultScarecrowModel::getTexturedModelData);

		EntityRendererRegistry.register(DEntities.GOLD_PIG_PLUSHIE, ((context) -> new GoldPigPlushieEntityRenderer(context)));
		EntityModelLayerRegistry.registerModelLayer(GoldPigPlushieModel.LAYER_LOCATION, GoldPigPlushieModel::getTexturedModelData);

		EntityRendererRegistry.register(DEntities.PIGEON_PLUSHIE, ((context) -> new PigeonPlushieEntityRenderer(context)));
		EntityModelLayerRegistry.registerModelLayer(PigeonPlushieModel.LAYER_LOCATION, PigeonPlushieModel::getTexturedModelData);

		EntityRendererRegistry.register(DEntities.PIGLIN_PLUSHIE, ((context) -> new PiglinPlushieEntityRenderer(context)));
		EntityModelLayerRegistry.registerModelLayer(PiglinPlushieModel.LAYER_LOCATION, PiglinPlushieModel::getTexturedModelData);

		EntityRendererRegistry.register(DEntities.RABBIT_PLUSHIE, ((context) -> new RabbitPlushieEntityRenderer(context)));
		EntityModelLayerRegistry.registerModelLayer(RabbitPlushieModel.LAYER_LOCATION, RabbitPlushieModel::getTexturedModelData);

		EntityRendererRegistry.register(DEntities.SNOW_GOLEM_PLUSHIE, ((context) -> new SnowGolemPlushieEntityRenderer(context)));
		EntityModelLayerRegistry.registerModelLayer(SnowGolemPlushieModel.LAYER_LOCATION, SnowGolemPlushieModel::getTexturedModelData);

		EntityRendererRegistry.register(DEntities.ZOMBIFIED_PIGLIN_PLUSHIE, ((context) -> new ZombifiedPiglinPlushieEntityRenderer(context)));
		EntityModelLayerRegistry.registerModelLayer(ZombifiedPiglinPlushieModel.LAYER_LOCATION, ZombifiedPiglinPlushieModel::getTexturedModelData);

		EntityRendererRegistry.register(DEntities.IRON_GOLEM_PLUSHIE, ((context) -> new IronGolemPlushieEntityRenderer(context)));
		EntityModelLayerRegistry.registerModelLayer(IronGolemPlushieModel.LAYER_LOCATION, IronGolemPlushieModel::getTexturedModelData);
	}
}
