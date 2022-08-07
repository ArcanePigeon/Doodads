package org.cloudwarp.doodads.entities;

import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.cloudwarp.doodads.registry.DDamageSource;
import org.cloudwarp.doodads.registry.DParticles;
import org.jetbrains.annotations.Nullable;

public abstract class SlingShotProjectileEntity extends ProjectileEntity {
	private static final TrackedData<Byte> PROJECTILE_FLAGS;
	private static final TrackedData<ItemStack> ITEM;
	private static final int CRITICAL_FLAG = 1;
	private double damage = 2.0;
	private int punch;
	private int life;
	private SoundEvent sound = this.getHitSound();

	public SlingShotProjectileEntity (EntityType<? extends SlingShotProjectileEntity> entityType, World world) {
		super(entityType, world);
	}

	public SlingShotProjectileEntity (EntityType<? extends SlingShotProjectileEntity> entityType, LivingEntity owner,World world) {
		this(entityType, owner.getX(), owner.getEyeY() - 0.10000000149011612, owner.getZ(), world);
		this.setOwner(owner);
	}

	public SlingShotProjectileEntity (EntityType<? extends SlingShotProjectileEntity> entityType, double x, double y, double z, World world) {
		this(entityType,world);
		this.setPosition(x, y, z);
	}

	public void setSound(SoundEvent sound) {
		this.sound = sound;
	}
	public boolean shouldRender(double distance) {
		double d = this.getBoundingBox().getAverageSideLength() * 10.0;
		if (Double.isNaN(d)) {
			d = 1.0;
		}

		d *= 64.0 * getRenderDistanceMultiplier();
		return distance < d * d;
	}
	@Override
	protected void initDataTracker () {
		this.dataTracker.startTracking(PROJECTILE_FLAGS, (byte)0);
		this.getDataTracker().startTracking(ITEM, ItemStack.EMPTY);
	}
	public void setVelocity(double x, double y, double z, float speed, float divergence) {
		super.setVelocity(x, y, z, speed, divergence);
		this.life = 0;
	}

	public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps, boolean interpolate) {
		this.setPosition(x, y, z);
		this.setRotation(yaw, pitch);
	}
	public void setVelocityClient(double x, double y, double z) {
		super.setVelocityClient(x, y, z);
		this.life = 0;
	}
	public void tick() {
		super.tick();
		HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
		boolean bl = false;
		if (hitResult.getType() == HitResult.Type.BLOCK) {
			BlockPos blockPos = ((BlockHitResult)hitResult).getBlockPos();
			BlockState blockState = this.world.getBlockState(blockPos);
			if (blockState.isOf(Blocks.NETHER_PORTAL)) {
				this.setInNetherPortal(blockPos);
				bl = true;
			} else if (blockState.isOf(Blocks.END_GATEWAY)) {
				BlockEntity blockEntity = this.world.getBlockEntity(blockPos);
				if (blockEntity instanceof EndGatewayBlockEntity && EndGatewayBlockEntity.canTeleport(this)) {
					EndGatewayBlockEntity.tryTeleportingEntity(this.world, blockPos, blockState, this, (EndGatewayBlockEntity)blockEntity);
				}

				bl = true;
			} else if (blockState.isIn(ConventionalBlockTags.GLASS_BLOCKS) || blockState.isIn(ConventionalBlockTags.GLASS_PANES)) {
				this.world.breakBlock(blockPos,false,this);
				bl = false;
			}else if (blockState.isOf(Blocks.POWDER_SNOW)) {
				this.extinguish();
				bl = true;
			}
		}
		if (this.isTouchingWaterOrRain()) {
			this.extinguish();
		}
		if (hitResult.getType() != HitResult.Type.MISS && !bl) {
			this.onCollision(hitResult);
		}



		this.checkBlockCollision();
		Vec3d vec3d = this.getVelocity();
		double d = this.getX() + vec3d.x;
		double e = this.getY() + vec3d.y;
		double f = this.getZ() + vec3d.z;
		this.updateRotation();
		float h;
		if (this.isCritical()) {
			for(int i = 0; i < 4; ++i) {
				this.world.addParticle(ParticleTypes.CRIT, this.getX() + d * (double)i / 4.0, this.getY() + e * (double)i / 4.0, this.getZ() + f * (double)i / 4.0, -d, -e + 0.2, -f);
			}
		}
		if (this.isTouchingWater()) {
			for(int i = 0; i < 4; ++i) {
				float g = 0.25F;
				this.world.addParticle(ParticleTypes.BUBBLE, d - vec3d.x * 0.25, e - vec3d.y * 0.25, f - vec3d.z * 0.25, vec3d.x, vec3d.y, vec3d.z);
			}

			h = 0.8F;
		} else {
			h = 0.99F;
		}




		this.setVelocity(vec3d.multiply((double)h));
		if (!this.hasNoGravity()) {
			Vec3d vec3d2 = this.getVelocity();
			this.setVelocity(vec3d2.x, vec3d2.y - (double)this.getGravity(), vec3d2.z);
		}

		this.setPosition(d, e, f);
	}
	@Override
	protected void onEntityHit (EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		Entity entity = entityHitResult.getEntity();
		float f = (float)this.getVelocity().length();
		int i = MathHelper.ceil(MathHelper.clamp((double)f * this.damage, 0.0, 2.147483647E9));
		if (this.isCritical()) {
			long l = (long)this.random.nextInt(i / 2 + 2);
			i = (int)Math.min(l + (long)i, 2147483647L);
		}

		Entity entity2 = this.getOwner();
		DamageSource damageSource;
		if (entity2 == null) {
			damageSource = new DDamageSource.DProjectileDamageSource( "pebble", this,this);
		} else {
			damageSource = new DDamageSource.DProjectileDamageSource( "pebble", this, entity2);
			if (entity2 instanceof LivingEntity) {
				((LivingEntity)entity2).onAttacking(entity);
			}
		}

		boolean bl = entity.getType() == EntityType.ENDERMAN;
		int j = entity.getFireTicks();
		if (this.isOnFire() && !bl) {
			entity.setOnFireFor(5);
		}

		if (entity.damage(damageSource, (float)i)) {
			if (bl) {
				return;
			}
			if (entity instanceof LivingEntity livingEntity) {
				if (this.punch > 0) {
					double d = Math.max(0.0, 1.0 - livingEntity.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE));
					Vec3d vec3d = this.getVelocity().multiply(1.0, 0.0, 1.0).normalize().multiply((double)this.punch * 0.6 * d);
					if (vec3d.lengthSquared() > 0.0) {
						livingEntity.addVelocity(vec3d.x, 0.1, vec3d.z);
					}
				}

				if (!this.world.isClient && entity2 instanceof LivingEntity) {
					EnchantmentHelper.onUserDamaged(livingEntity, entity2);
					EnchantmentHelper.onTargetDamaged((LivingEntity)entity2, livingEntity);
				}

				this.onHit(livingEntity);
				if (entity2 != null && livingEntity != entity2 && livingEntity instanceof PlayerEntity && entity2 instanceof ServerPlayerEntity && !this.isSilent()) {
					((ServerPlayerEntity)entity2).networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER, 0.0F));
				}

				this.playSound(this.sound, 0.7F, 1.4F / (this.random.nextFloat() * 0.2F + 0.9F));
			}
			this.discard();
		} else {
			entity.setFireTicks(j);
			this.setVelocity(this.getVelocity().multiply(-0.1));
			this.setYaw(this.getYaw() + 180.0F);
			this.prevYaw += 180.0F;
			if (!this.world.isClient && this.getVelocity().lengthSquared() < 1.0E-7) {
				this.discard();
			}
		}
	}
	protected float getGravity() {
		return 0.04F;
	}
	protected void onHit(LivingEntity target) {
	}
	protected SoundEvent getHitSound() {
		return SoundEvents.BLOCK_STONE_BREAK;
	}

	protected final SoundEvent getSound() {
		return this.sound;
	}
	@Nullable
	protected EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition) {
		return ProjectileUtil.getEntityCollision(this.world, this, currentPosition, nextPosition, this.getBoundingBox().stretch(this.getVelocity()).expand(1.0), this::canHit);
	}
	public void setItem(ItemStack item) {
		if (!item.isOf(this.getDefaultItem()) || item.hasNbt()) {
			this.getDataTracker().set(ITEM, (ItemStack) Util.make(item.copy(), (stack) -> {
				stack.setCount(1);
			}));
		}

	}
	protected abstract Item getDefaultItem();

	protected ItemStack getItem() {
		return (ItemStack)this.getDataTracker().get(ITEM);
	}

	public ItemStack getStack() {
		ItemStack itemStack = this.getItem();
		return itemStack.isEmpty() ? new ItemStack(this.getDefaultItem()) : itemStack;
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putDouble("damage", this.damage);
		nbt.putBoolean("crit", this.isCritical());
		nbt.putString("SoundEvent", Registry.SOUND_EVENT.getId(this.sound).toString());
		ItemStack itemStack = this.getItem();
		if (!itemStack.isEmpty()) {
			nbt.put("Item", itemStack.writeNbt(new NbtCompound()));
		}
	}
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		if (nbt.contains("damage", 99)) {
			this.damage = nbt.getDouble("damage");
		}
		this.setCritical(nbt.getBoolean("crit"));
		if (nbt.contains("SoundEvent", 8)) {
			this.sound = (SoundEvent)Registry.SOUND_EVENT.getOrEmpty(new Identifier(nbt.getString("SoundEvent"))).orElse(this.getHitSound());
		}
		ItemStack itemStack = ItemStack.fromNbt(nbt.getCompound("Item"));
		this.setItem(itemStack);
	}
	public void setDamage(double damage) {
		this.damage = damage;
	}

	public double getDamage() {
		return this.damage;
	}
	public void setPunch(int punch) {
		this.punch = punch;
	}

	public int getPunch() {
		return this.punch;
	}
	public boolean isAttackable() {
		return false;
	}

	protected float getEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.13F;
	}

	public void setCritical(boolean critical) {
		this.setProjectileFlag(CRITICAL_FLAG, critical);
	}

	private void setProjectileFlag(int index, boolean flag) {
		byte b = (Byte)this.dataTracker.get(PROJECTILE_FLAGS);
		if (flag) {
			this.dataTracker.set(PROJECTILE_FLAGS, (byte)(b | index));
		} else {
			this.dataTracker.set(PROJECTILE_FLAGS, (byte)(b & ~index));
		}

	}

	public boolean isCritical() {
		byte b = (Byte)this.dataTracker.get(PROJECTILE_FLAGS);
		return (b & 1) != 0;
	}
	public void applyEnchantmentEffects(LivingEntity entity, float damageModifier) {
		int i = EnchantmentHelper.getEquipmentLevel(Enchantments.POWER, entity);
		int j = EnchantmentHelper.getEquipmentLevel(Enchantments.PUNCH, entity);
		this.setDamage((double)(damageModifier * 2.0F) + this.random.nextGaussian() * 0.25 + (double)((float)this.world.getDifficulty().getId() * 0.11F));
		if (i > 0) {
			this.setDamage(this.getDamage() + (double)i * 0.5 + 0.5);
		}

		if (j > 0) {
			this.setPunch(j);
		}

		if (EnchantmentHelper.getEquipmentLevel(Enchantments.FLAME, entity) > 0) {
			this.setOnFireFor(100);
		}

	}
	protected float getDragInWater() {
		return 0.6F;
	}

	public void setNoClip(boolean noClip) {
		this.noClip = noClip;
		this.setProjectileFlag(2, noClip);
	}

	public boolean isNoClip() {
		if (!this.world.isClient) {
			return this.noClip;
		} else {
			return ((Byte)this.dataTracker.get(PROJECTILE_FLAGS) & 2) != 0;
		}
	}
	public Packet<?> createSpawnPacket() {
		Entity entity = this.getOwner();
		/*PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());
		packet.writeDouble(getX());
		packet.writeDouble(getY());
		packet.writeDouble(getZ());
		packet.writeVarInt(getId());
		packet.writeUuid(getUuid());
		return ServerSidePacketRegistry.IN*/
		return new EntitySpawnS2CPacket(this, entity == null ? 0 : entity.getId());
	}

	public void onSpawnPacket(EntitySpawnS2CPacket packet) {
		super.onSpawnPacket(packet);
		Entity entity = this.world.getEntityById(packet.getEntityData());
		if (entity != null) {
			this.setOwner(entity);
		}

	}

	private ParticleEffect getParticleParameters() {
		ItemStack itemStack = this.getItem();
		return itemStack.isEmpty() ? DParticles.PEBBLE_PARTICLE : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack);
	}

	@Override
	public void handleStatus(byte status) {
		if (status == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {
			ParticleEffect particleEffect = this.getParticleParameters();
			for (int i = 0; i < 8; ++i) {
				this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
			}
		}
	}

	@Override
	protected void onCollision (HitResult hitResult) {
		super.onCollision(hitResult);
		if(!this.world.isClient){
			this.world.sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
			this.discard();
		}
	}
	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		super.onBlockHit(blockHitResult);
		this.playSound(this.getSound(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
	}

	static {
		PROJECTILE_FLAGS = DataTracker.registerData(SlingShotProjectileEntity.class, TrackedDataHandlerRegistry.BYTE);
		ITEM = DataTracker.registerData(SlingShotProjectileEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
	}
}
