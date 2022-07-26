package org.cloudwarp.doodads.mixin;

import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.BlockTags;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static org.cloudwarp.doodads.utils.DoodadsItemTypes.ENDER_GOGGLES;

@Mixin(EndermanEntity.class)
public abstract class EndermanEntityMixin extends HostileEntity {


	protected EndermanEntityMixin (EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}
	@Inject(method = "isPlayerStaring", at = @At(value = "RETURN", target = "Lnet/minecraft/entity/mob/EndermanEntity;isPlayerStaring(Lnet/minecraft/entity/player/PlayerEntity;)Z"), cancellable = true)
	public void isPlayerStaring(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
		if(TrinketsApi.getTrinketComponent((LivingEntity)((Object)player)).get().isEquipped(ENDER_GOGGLES.item())){
			cir.setReturnValue(false);
		}
	}
}
