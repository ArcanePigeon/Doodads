package org.cloudwarp.doodads.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static org.cloudwarp.doodads.utils.DoodadsItemTypes.DUCT_TAPE;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {
	protected MobEntityMixin (EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "interactWithItem", at = @At("HEAD"), cancellable = true)
	protected void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		if(player.getStackInHand(hand).isOf(DUCT_TAPE.item()) && !this.isSilent()){
			this.setSilent(true);
			this.world.playSound(player,this.getBlockPos(), SoundEvents.BLOCK_CAKE_ADD_CANDLE, SoundCategory.BLOCKS,1.0f,0.8f);
			cir.setReturnValue(ActionResult.SUCCESS);
			cir.cancel();
		}
		if(player.getStackInHand(hand).isOf(Items.SHEARS) && this.isSilent()){
			this.setSilent(false);
			this.world.playSound(player,this.getBlockPos(), SoundEvents.ENTITY_SNOW_GOLEM_SHEAR, SoundCategory.BLOCKS,1.0f,1.5f);
			cir.setReturnValue(ActionResult.SUCCESS);
			cir.cancel();
		}
	}
}
