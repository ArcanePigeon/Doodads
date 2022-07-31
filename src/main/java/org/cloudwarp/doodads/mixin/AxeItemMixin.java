package org.cloudwarp.doodads.mixin;

import com.google.common.collect.ImmutableMap;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

import static org.cloudwarp.doodads.utils.DoodadsItemTypes.LOGGERS_GLOVE;

@Mixin(AxeItem.class)
public class AxeItemMixin {
	@Shadow
	private Optional<BlockState> getStrippedState(BlockState state) {return Optional.empty();}

	@Inject(method = "useOnBlock", at = @At(value = "HEAD"), cancellable = true)
	public void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
		World dWorld = context.getWorld();
		BlockPos dPos = context.getBlockPos();
		BlockState dState = dWorld.getBlockState(dPos);
		Optional<BlockState> isStripped = this.getStrippedState(dState);
		if(isStripped.isPresent() && TrinketsApi.getTrinketComponent(context.getPlayer()).get().isEquipped(LOGGERS_GLOVE.item())){
			cir.setReturnValue(ActionResult.PASS);
			cir.cancel();
		}
	}
}
