package org.cloudwarp.doodads.mixin;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.sound.GuardianAttackSoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static org.cloudwarp.doodads.utils.DoodadsItemTypes.MAGIC_PLUM;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	@Shadow public abstract boolean damage (DamageSource source, float amount);
	@Shadow public ItemStack getStackInHand(Hand hand){return null;}
	@Shadow public void setHealth(float health) {}
	@Shadow public boolean clearStatusEffects() {return false;}
	@Shadow  public final boolean addStatusEffect(StatusEffectInstance effect) {return false;}
	@Shadow protected SoundEvent getDeathSound() {return null;}
	@Shadow protected float getSoundVolume() {return 0f;}
	@Shadow public float getSoundPitch() {return 0f;}
	@Shadow public void onDeath(DamageSource damageSource) {}
	@Shadow  public boolean isDead() {return false;}

	private static final TrackedData<Boolean> HAS_MAGIC_PLUM = DataTracker.registerData(LivingEntityMixin.class, TrackedDataHandlerRegistry.BOOLEAN);

	public LivingEntityMixin (EntityType<?> type, World world) {
		super(type, world);
	}

	@Inject(method = "initDataTracker", at = @At(value = "TAIL", target = "Lnet/minecraft/entity/LivingEntity;initDataTracker()V"))
	protected void initDataTracker(CallbackInfo ci) {
		this.dataTracker.startTracking(HAS_MAGIC_PLUM, false);
	}

	public boolean hasPlum () {
		return this.dataTracker.get(HAS_MAGIC_PLUM);
	}

	public void setPlum(boolean hasPlum){
		this.dataTracker.set(HAS_MAGIC_PLUM, hasPlum);
	}

	private boolean tryUsePlum(DamageSource source) {
		if (source.isOutOfWorld()) {
			return false;
		}

		if (this.hasPlum()) {
			if (((LivingEntity)(Object)this) instanceof ServerPlayerEntity serverPlayerEntity) {
				serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(MAGIC_PLUM.item()));
				Criteria.USED_TOTEM.trigger(serverPlayerEntity, MAGIC_PLUM.itemStack());
			}
			this.setHealth(1.0f);
			this.clearStatusEffects();
			this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
			this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
			this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));
			this.world.sendEntityStatus(this, EntityStatuses.USE_TOTEM_OF_UNDYING);
			this.setPlum(false);
			return true;
		}
		return false;
	}

	@Inject(method = "damage",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/entity/LivingEntity;isDead()Z",
					ordinal = 1,
					shift = At.Shift.BEFORE))
	private void magicPlumDeath(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		if (this.isDead() && !this.tryUsePlum(source)) {
			boolean regenBoolean = true;
			if ((float)this.timeUntilRegen > 10.0f) {
				regenBoolean = false;
			}
			SoundEvent soundEvent = this.getDeathSound();
			if (regenBoolean && soundEvent != null) {
				this.playSound(soundEvent, this.getSoundVolume(), this.getSoundPitch());
			}
			this.onDeath(source);
		}
	}
	@Inject(method = "tryUseTotem", at = @At(value = "HEAD"), cancellable = true)
	private void tryUseTotem(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(false);
		cir.cancel();
	}
}
