package org.cloudwarp.doodads.entities;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.cloudwarp.doodads.registry.DDamageSource;
import org.cloudwarp.doodads.registry.DEntities;
import org.jetbrains.annotations.Nullable;

import static org.cloudwarp.doodads.utils.DoodadsItemTypes.RUBBER_BAND;

public class RubberBandEntity extends PersistentProjectileEntity {
	private static final TrackedData<Byte> LOYALTY = DataTracker.registerData(RubberBandEntity.class, TrackedDataHandlerRegistry.BYTE);
	private static final TrackedData<Boolean> ENCHANTED = DataTracker.registerData(RubberBandEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	private ItemStack rubberBandStack = RUBBER_BAND.itemStack();
	private boolean dealtDamage;
	public int returnTimer;

	public RubberBandEntity(EntityType<? extends RubberBandEntity> entityType, World world) {
		super((EntityType<? extends PersistentProjectileEntity>)entityType, world);
	}

	public RubberBandEntity(World world, LivingEntity owner, ItemStack stack) {
		super(DEntities.RUBBER_BAND_ENTITY, owner, world);
		this.rubberBandStack = stack.copy();
		this.dataTracker.set(LOYALTY, (byte) EnchantmentHelper.getLoyalty(stack));
		this.dataTracker.set(ENCHANTED, stack.hasGlint());
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(LOYALTY, (byte)0);
		this.dataTracker.startTracking(ENCHANTED, false);
	}

	@Override
	public void tick() {
		if (this.inGroundTime > 4) {
			this.dealtDamage = true;
		}
		Entity entity = this.getOwner();
		byte i = this.dataTracker.get(LOYALTY);
		if (i > 0 && (this.dealtDamage || this.isNoClip()) && entity != null) {
			if (!this.isOwnerAlive()) {
				if (!this.world.isClient && this.pickupType == PersistentProjectileEntity.PickupPermission.ALLOWED) {
					this.dropStack(this.asItemStack(), 0.1f);
				}
				this.discard();
			} else {
				this.setNoClip(true);
				Vec3d vec3d = entity.getEyePos().subtract(this.getPos());
				this.setPos(this.getX(), this.getY() + vec3d.y * 0.015 * (double)i, this.getZ());
				if (this.world.isClient) {
					this.lastRenderY = this.getY();
				}
				double d = 0.05 * (double)i;
				this.setVelocity(this.getVelocity().multiply(0.95).add(vec3d.normalize().multiply(d)));
				if (this.returnTimer == 0) {
					this.playSound(SoundEvents.ITEM_TRIDENT_RETURN, 10.0f, 1.0f);
				}
				++this.returnTimer;
			}
		}
		super.tick();
	}

	private boolean isOwnerAlive() {
		Entity entity = this.getOwner();
		if (entity == null || !entity.isAlive()) {
			return false;
		}
		return !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
	}

	@Override
	public ItemStack asItemStack() {
		return this.rubberBandStack.copy();
	}

	public boolean isEnchanted() {
		return this.dataTracker.get(ENCHANTED);
	}

	@Override
	@Nullable
	protected EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition) {
		if (this.dealtDamage) {
			return null;
		}
		return super.getEntityCollision(currentPosition, nextPosition);
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		BlockPos blockPos;
		Entity entity2;
		Entity entity = entityHitResult.getEntity();
		float f = 1.0f;
		if (entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity)entity;
			f += EnchantmentHelper.getAttackDamage(this.rubberBandStack, livingEntity.getGroup());
		}
		DamageSource damageSource = new DDamageSource.DProjectileDamageSource("rubber_band", this, (entity2 = this.getOwner()) == null ? this : entity2);
		this.dealtDamage = true;
		SoundEvent soundEvent = SoundEvents.ENTITY_ARROW_HIT_PLAYER;
		if (entity.damage(damageSource, f)) {
			if (entity.getType() == EntityType.ENDERMAN) {
				return;
			}
			if (entity instanceof LivingEntity) {
				LivingEntity livingEntity2 = (LivingEntity)entity;
				if (entity2 instanceof LivingEntity) {
					EnchantmentHelper.onUserDamaged(livingEntity2, entity2);
					EnchantmentHelper.onTargetDamaged((LivingEntity)entity2, livingEntity2);
				}
				this.onHit(livingEntity2);
			}
		}
		this.setVelocity(this.getVelocity().multiply(-0.01, -0.1, -0.01));
		float g = 1.0f;
		this.playSound(soundEvent, g, 1.0f);
	}

	@Override
	protected boolean tryPickup(PlayerEntity player) {
		return super.tryPickup(player) || this.isNoClip() && this.isOwner(player) && player.getInventory().insertStack(this.asItemStack());
	}

	@Override
	protected SoundEvent getHitSound() {
		return SoundEvents.BLOCK_MUD_HIT;
	}

	@Override
	public void onPlayerCollision(PlayerEntity player) {
		if (this.isOwner(player) || this.getOwner() == null) {
			super.onPlayerCollision(player);
		}
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		if (nbt.contains(RUBBER_BAND.name, NbtElement.COMPOUND_TYPE)) {
			this.rubberBandStack = ItemStack.fromNbt(nbt.getCompound(RUBBER_BAND.name));
		}
		this.dealtDamage = nbt.getBoolean("DealtDamage");
		this.dataTracker.set(LOYALTY, (byte)EnchantmentHelper.getLoyalty(this.rubberBandStack));
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.put(RUBBER_BAND.name, this.rubberBandStack.writeNbt(new NbtCompound()));
		nbt.putBoolean("DealtDamage", this.dealtDamage);
	}

	@Override
	public void age() {
		byte i = this.dataTracker.get(LOYALTY);
		if (this.pickupType != PersistentProjectileEntity.PickupPermission.ALLOWED || i <= 0) {
			super.age();
		}
	}

	@Override
	protected float getDragInWater() {
		return 0.99f;
	}

	@Override
	public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
		return true;
	}
}
