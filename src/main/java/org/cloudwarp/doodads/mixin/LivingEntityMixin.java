package org.cloudwarp.doodads.mixin;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.ThornsEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static org.cloudwarp.doodads.utils.DoodadsItemTypes.*;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	@Shadow
	public abstract boolean damage (DamageSource source, float amount);

	@Shadow
	public ItemStack getStackInHand (Hand hand) {
		return null;
	}

	@Shadow
	public void setHealth (float health) {
	}

	@Shadow
	public boolean clearStatusEffects () {
		return false;
	}

	@Shadow
	public final boolean addStatusEffect (StatusEffectInstance effect) {
		return false;
	}

	@Shadow
	protected SoundEvent getDeathSound () {
		return null;
	}

	@Shadow
	protected float getSoundVolume () {
		return 0f;
	}

	@Shadow
	public float getSoundPitch () {
		return 0f;
	}

	@Shadow
	public void onDeath (DamageSource damageSource) {
	}

	@Shadow
	public boolean isDead () {
		return false;
	}

	@Shadow
	public abstract ItemStack getMainHandStack ();

	@Shadow
	public abstract ItemStack getOffHandStack ();

	@Unique
	private static final TrackedData<Boolean> HAS_MAGIC_PLUM = DataTracker.registerData(LivingEntityMixin.class, TrackedDataHandlerRegistry.BOOLEAN);

	public LivingEntityMixin (EntityType<?> type, World world) {
		super(type, world);
	}

	@Inject(method = "initDataTracker", at = @At(value = "TAIL", target = "Lnet/minecraft/entity/LivingEntity;initDataTracker()V"))
	protected void initDataTracker (CallbackInfo ci) {
		this.dataTracker.startTracking(HAS_MAGIC_PLUM, false);
	}

	@Unique
	public boolean hasPlum () {
		return this.dataTracker.get(HAS_MAGIC_PLUM);
	}

	@Unique
	public void setPlum (boolean hasPlum) {
		this.dataTracker.set(HAS_MAGIC_PLUM, hasPlum);
	}

	@Unique
	private boolean tryUsePlum (DamageSource source) {
		if (source.isOutOfWorld()) {
			return false;
		}

		if (this.hasPlum()) {
			if (((LivingEntity) (Object) this) instanceof ServerPlayerEntity serverPlayerEntity) {
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

	@Inject(method = "tryUseTotem", at = @At(value = "HEAD"), cancellable = true)
	private void tryUseTotem (DamageSource source, CallbackInfoReturnable<Boolean> cir) {
		if (tryUsePlum(source)) {
			cir.setReturnValue(true);
			cir.cancel();
		}
	}

	@Inject(method = "getAttributeValue", at = @At("RETURN"), cancellable = true)
	public void getAttributeValue (EntityAttribute attribute, CallbackInfoReturnable<Double> cir) {
		TrinketComponent trinketComponent = TrinketsApi.getTrinketComponent((LivingEntity) ((Object) this)).get();
		if (attribute.equals(EntityAttributes.GENERIC_ATTACK_DAMAGE)) {
			if ((trinketComponent.isEquipped(SUN_RING.item()) && this.world.isDay()) ||
					(trinketComponent.isEquipped(MOON_RING.item()) && this.world.isNight())) {
				cir.setReturnValue(cir.getReturnValueD() * 1.25D);
			} else if ((trinketComponent.isEquipped(CELESTIAL_RING.item()))) {
				cir.setReturnValue(cir.getReturnValueD() * 1.5D);
			}
		}
	}
	@Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/damage/DamageSource;getAttacker()Lnet/minecraft/entity/Entity;", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILSOFT)
	public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir, float f, boolean bl, float g, boolean bl2){
		TrinketComponent trinketComponent = TrinketsApi.getTrinketComponent((LivingEntity) ((Object) this)).get();
		Entity attacker = source.getAttacker();
		if(trinketComponent.isEquipped(CACTUS_RING.item()) && attacker != null){
			int damage = 1 + random.nextInt(4);
			attacker.damage(DamageSource.thorns((LivingEntity) ((Object) this)), damage);
		}
	}
	@Inject(method="getJumpVelocity", at = @At("RETURN"), cancellable = true)
	public void getJumpVelocity(CallbackInfoReturnable<Float> cir){
		TrinketComponent trinketComponent = TrinketsApi.getTrinketComponent((LivingEntity) ((Object) this)).get();
		if(trinketComponent.isEquipped(SLIMEY_SHOES.item())){
			cir.setReturnValue(cir.getReturnValueF() * 2f);
		}
	}

	@Inject(method ="computeFallDamage", at = @At("RETURN"), cancellable = true)
	public void computeFallDamage(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Integer> cir){
		TrinketComponent trinketComponent = TrinketsApi.getTrinketComponent((LivingEntity) ((Object) this)).get();
		if(trinketComponent.isEquipped(SLIMEY_SHOES.item())){
			cir.setReturnValue(0);
		}
	}

	@Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
	public void readDoodadNBT(NbtCompound nbt, CallbackInfo ci){
		this.dataTracker.set(HAS_MAGIC_PLUM, nbt.getBoolean("doodads_has_magic_plum"));
	}
	@Inject(method="writeCustomDataToNbt", at = @At("HEAD"))
	public void writeDoodadNBT(NbtCompound nbt, CallbackInfo ci){
		nbt.putBoolean("doodads_has_magic_plum",this.dataTracker.get(HAS_MAGIC_PLUM));
	}

}
