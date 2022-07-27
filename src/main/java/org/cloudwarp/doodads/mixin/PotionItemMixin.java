package org.cloudwarp.doodads.mixin;

import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.cloudwarp.doodads.utils.DoodadsItemTypes.BENDY_STRAW;
import static org.cloudwarp.doodads.utils.DoodadsItemTypes.ENDER_GOGGLES;

@Mixin(PotionItem.class)
public abstract class PotionItemMixin {

	//@ModifyVariable(method = "finishUsing",at = @At(value = "INVOKE",
	//		target = "Lnet/minecraft/entity/LivingEntity;addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;)Z"))
	//private List<StatusEffectInstance> BendyStrawDurationModification () {
	//
	//}
	@Inject(method = "finishUsing", at = @At(value = "INVOKE",
			shift = At.Shift.BEFORE,
			target = "Lnet/minecraft/entity/LivingEntity;addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;)Z"),
			locals = LocalCapture.CAPTURE_FAILSOFT
	)
	private void BendyStrawDurationModification (ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir, PlayerEntity playerEntity, List list, Iterator var6, StatusEffectInstance statusEffectInstance) {
		boolean isWearingStraw = TrinketsApi.getTrinketComponent((LivingEntity) ((Object) playerEntity)).get().isEquipped(BENDY_STRAW.item());
		if (isWearingStraw) {
			if (! statusEffectInstance.getEffectType().isInstant()) {
				statusEffectInstance = new StatusEffectInstance(
						statusEffectInstance.getEffectType(),
						statusEffectInstance.getDuration() * 2,
						statusEffectInstance.getAmplifier(),
						statusEffectInstance.isAmbient(),
						statusEffectInstance.shouldShowParticles(),
						statusEffectInstance.shouldShowIcon());
				user.addStatusEffect(statusEffectInstance);
			}
		}
		//if(!isWearingStraw);
	}
}
