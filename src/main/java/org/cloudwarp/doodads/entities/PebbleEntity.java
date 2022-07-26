package org.cloudwarp.doodads.entities;


import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;

import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import org.cloudwarp.doodads.registry.DEntities;
import org.cloudwarp.doodads.registry.DItems;

import static org.cloudwarp.doodads.utils.DoodadsItemTypes.PEBBLE;


public class PebbleEntity extends SlingShotProjectileEntity {
	public PebbleEntity(EntityType<? extends PebbleEntity> entityType, World world) {
		super(entityType, world);
	}

	public PebbleEntity(World world, double x, double y, double z) {
		super(DEntities.PEBBLE_ENTITY, x, y, z, world);
	}

	public PebbleEntity(World world, LivingEntity owner) {
		super(DEntities.PEBBLE_ENTITY, owner, world);
	}

	protected void onHit(LivingEntity target) {
		super.onHit(target);
	}

	@Override
	protected Item getDefaultItem () {
		return PEBBLE.item();
	}

}
