package org.cloudwarp.doodads.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static org.cloudwarp.doodads.Doodads.*;

import org.cloudwarp.doodads.Doodads;
import org.cloudwarp.doodads.entities.PebbleEntity;
import org.cloudwarp.doodads.entities.RubberBandEntity;

import java.util.LinkedHashMap;
import java.util.Map;


public class DEntities {
	private static final Map<EntityType<?>, Identifier> ENTITY_TYPES = new LinkedHashMap<>();

	public static final EntityType<PebbleEntity> PEBBLE_ENTITY = register("pebble",
			FabricEntityTypeBuilder.<PebbleEntity>create(SpawnGroup.MISC, PebbleEntity::new)
					.dimensions(EntityDimensions.fixed(0.25F, 0.25F))
					.trackRangeChunks(16)
					.build());
	public static final EntityType<RubberBandEntity> RUBBER_BAND_ENTITY = register("rubber_band",
			FabricEntityTypeBuilder.<RubberBandEntity>create(SpawnGroup.MISC, RubberBandEntity::new)
					.dimensions(EntityDimensions.fixed(0.25F, 0.25F))
					.trackRangeChunks(4).trackedUpdateRate(20)
					.build());

	public static void init () {
		ENTITY_TYPES.keySet().forEach(entityType -> Registry.register(Registry.ENTITY_TYPE, ENTITY_TYPES.get(entityType), entityType));
	}

	private static <T extends Entity> EntityType<T> register (String name, EntityType<T> type) {
		ENTITY_TYPES.put(type, Doodads.id(name));
		return type;
	}
}
