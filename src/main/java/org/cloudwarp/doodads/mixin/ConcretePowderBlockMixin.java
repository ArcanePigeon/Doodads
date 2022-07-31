package org.cloudwarp.doodads.mixin;

import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConcretePowderBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static org.cloudwarp.doodads.utils.DoodadsItemTypes.SOGGY_GLOVE;

@Mixin(ConcretePowderBlock.class)
public abstract class ConcretePowderBlockMixin {
	@Shadow
	@Final
	private BlockState hardenedState;

	@Inject(method = "getPlacementState", at = @At("HEAD"), cancellable = true)
	public void getPlacementState(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
		if (TrinketsApi.getTrinketComponent(ctx.getPlayer()).get().isEquipped(SOGGY_GLOVE.item())){
			ctx.getWorld().playSound(ctx.getPlayer(),ctx.getBlockPos(), SoundEvents.BLOCK_CANDLE_PLACE, SoundCategory.BLOCKS,0.8f,1.0f);
			cir.setReturnValue(this.hardenedState);
			cir.cancel();
		}
	}
}
