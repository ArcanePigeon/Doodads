package org.cloudwarp.doodads.registry;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.registry.Registry;
import org.cloudwarp.doodads.Doodads;

public class DParticles {
	public static final DefaultParticleType PEBBLE_PARTICLE = FabricParticleTypes.simple();
	public static void init(){
		//Registry.register(Registry.PARTICLE_TYPE, Doodads.id("pebble_particle"), PEBBLE_PARTICLE);
	}
}
