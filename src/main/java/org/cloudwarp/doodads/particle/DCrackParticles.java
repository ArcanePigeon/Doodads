package org.cloudwarp.doodads.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.CrackParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ItemStackParticleEffect;
import org.cloudwarp.doodads.registry.DItems;

public class DCrackParticles extends CrackParticle {
	protected DCrackParticles (ClientWorld world, double x, double y, double z, ItemStack stack) {
		super(world, x, y, z, stack);
	}

	public DCrackParticles (ClientWorld world, double d, double e, double f, double g, double h, double i, ItemStack stack) {
		this(world, d, e, f, stack);
		this.velocityX *= (double)0.5f;
		this.velocityY *= (double)0.5f;
		this.velocityZ *= (double)0.5f;
		this.velocityX += velocityX;
		this.velocityY += velocityY;
		this.velocityZ += velocityZ;
	}

	@Environment(value=EnvType.CLIENT)
	public static class PebbleFactory
			implements ParticleFactory<DefaultParticleType> {
		@Override
		public Particle createParticle(DefaultParticleType itemStackParticleEffect, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			return new DCrackParticles(clientWorld, d, e, f, g, h, i, new ItemStack(DItems.get("pebble")));
		}
	}
	//@Environment(value= EnvType.CLIENT)
	//public static class PebbleFactory
	//		implements ParticleFactory<DefaultParticleType> {
	//	@Override
	//	public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
	//		return new DCrackParticles(clientWorld, d, e, f, new ItemStack(DItems.get("pebble")));
	//	}
	//}
}
