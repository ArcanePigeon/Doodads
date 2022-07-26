package org.cloudwarp.doodads.blockdetails;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.cloudwarp.doodads.entities.PebbleEntity;
import org.cloudwarp.doodads.entities.SlingShotProjectileEntity;
import org.cloudwarp.doodads.utils.DoodadsItemTypes;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PebbleItem extends Item {
	public DoodadsItemTypes doodadsItemType;

	public PebbleItem (Settings settings, DoodadsItemTypes doodadsItemType) {
		super(settings);
		this.doodadsItemType = doodadsItemType;
	}
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand); // creates a new ItemStack instance of the user's itemStack in-hand
		world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 1F); // plays a globalSoundEvent
		if (!world.isClient) {
			PebbleEntity pebbleEntity = new PebbleEntity(world, user);
			pebbleEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 1.0f, 1.0f);
			world.spawnEntity(pebbleEntity);
		}

		user.incrementStat(Stats.USED.getOrCreateStat(this));
		if (! user.getAbilities().creativeMode) {
			itemStack.decrement(1);
		}
		return TypedActionResult.success(itemStack, world.isClient());
	}
	public SlingShotProjectileEntity createPebble(World world, ItemStack stack, LivingEntity shooter) {
		PebbleEntity pebbleEntity = new PebbleEntity(world, shooter);
		return pebbleEntity;

	}

}

