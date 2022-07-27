package org.cloudwarp.doodads.mixin;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import org.cloudwarp.doodads.interfaces.PlayerEntityInterface;
import org.cloudwarp.doodads.item.MagicPlum;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static org.cloudwarp.doodads.utils.DoodadsItemTypes.MAGIC_PLUM;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {

	@Inject(method = "getActiveTotemOfUndying", at = @At(value = "RETURN"), cancellable = true)
	private static void getActiveTotemOfUndying(PlayerEntity player, CallbackInfoReturnable<ItemStack> cir) {
		if(((PlayerEntityInterface)player).hasPlum()){
			cir.setReturnValue(MAGIC_PLUM.itemStack());
		}
	}
}
