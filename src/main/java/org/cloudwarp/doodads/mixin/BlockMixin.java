package org.cloudwarp.doodads.mixin;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.cloudwarp.doodads.utils.DoodadsItemTypes.SLIMEY_SHOES;

@Mixin(Block.class)
public class BlockMixin {
	@Inject(method = "onEntityLand", at = @At("HEAD"), cancellable = true)
	public void onEntityLand (BlockView world, Entity entity, CallbackInfo ci) {
		if (entity instanceof LivingEntity livingEntity) {
			TrinketComponent trinketComponent = TrinketsApi.getTrinketComponent(livingEntity).get();
			if (trinketComponent.isEquipped(SLIMEY_SHOES.item())) {
				bounce(entity);
				ci.cancel();
			}
		}
	}

	@Unique
	private void bounce (Entity entity) {
		if (entity.bypassesLandingEffects()) {
			entity.setVelocity(entity.getVelocity().multiply(1.0, 0.0, 1.0));
		} else {
			Vec3d vec3d = entity.getVelocity();
			if (vec3d.y < 0.0f) {
				entity.setVelocity(vec3d.x, - vec3d.y * 0.9D, vec3d.z);
			}
		}
	}
}
