package org.cloudwarp.doodads.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Vanishable;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.cloudwarp.doodads.entities.RubberBandEntity;
import org.cloudwarp.doodads.utils.DoodadsItemTypes;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RubberBandItem extends Item implements Vanishable {
	public DoodadsItemTypes doodadsItemType;
	private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

	public RubberBandItem (Settings settings, DoodadsItemTypes doodadsItemType) {
		super(settings);
		this.doodadsItemType = doodadsItemType;
		ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", 1.0, EntityAttributeModifier.Operation.ADDITION));
		builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", (double) - 2.9f, EntityAttributeModifier.Operation.ADDITION));
		this.attributeModifiers = builder.build();
	}

	@Override
	public UseAction getUseAction (ItemStack stack) {
		return UseAction.BOW;
	}

	@Override
	public int getMaxUseTime (ItemStack stack) {
		return 72000;
	}

	@Override
	public void onStoppedUsing (ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		if (! (user instanceof PlayerEntity)) {
			return;
		}
		PlayerEntity playerEntity = (PlayerEntity) user;
		int i = this.getMaxUseTime(stack) - remainingUseTicks;
		if (i < 10) {
			return;
		}
		if (! world.isClient) {
			if (! playerEntity.getAbilities().creativeMode) {
				stack = stack.split(1);
			}

			RubberBandEntity rubberBandEntity = new RubberBandEntity(world, (LivingEntity) playerEntity,
					stack);
			rubberBandEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0f, 2.5f, 1.0f);
			if (playerEntity.getAbilities().creativeMode) {
				rubberBandEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
			}
			world.spawnEntity(rubberBandEntity);
			world.playSoundFromEntity(null, rubberBandEntity, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0f, 1.0f);


		}
		playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
	}

	@Override
	public TypedActionResult<ItemStack> use (World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		user.setCurrentHand(hand);
		return TypedActionResult.consume(itemStack);
	}

	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers (EquipmentSlot slot) {
		if (slot == EquipmentSlot.MAINHAND) {
			return this.attributeModifiers;
		}
		return super.getAttributeModifiers(slot);
	}

	@Override
	public int getEnchantability () {
		return 1;
	}
}

