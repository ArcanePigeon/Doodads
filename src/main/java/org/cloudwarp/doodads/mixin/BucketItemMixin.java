package org.cloudwarp.doodads.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static org.cloudwarp.doodads.utils.DoodadsItemTypes.PAINTBRUSH;

@Mixin(BucketItem.class)
public abstract class BucketItemMixin extends Item {
	@Shadow
	@Final
	private Fluid fluid;
	public BucketItemMixin (Settings settings) {
		super(settings);
	}
	@Inject(method = "use", at = @At(value = "HEAD"), cancellable = true)
	public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
		if(fluid == Fluids.WATER  && user.getOffHandStack().isOf(PAINTBRUSH.item())){
			cir.setReturnValue(TypedActionResult.pass(user.getStackInHand(hand)));
			cir.cancel();
		}
	}
}
