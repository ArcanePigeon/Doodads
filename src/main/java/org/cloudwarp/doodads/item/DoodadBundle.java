package org.cloudwarp.doodads.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BundleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.cloudwarp.doodads.registry.DItems;
import org.cloudwarp.doodads.utils.DoodadsItemTypes;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DoodadBundle extends Item {
	public DoodadsItemTypes doodadsItemType;

	public DoodadBundle (Settings settings, DoodadsItemTypes doodadsItemType) {
		super(settings);
		this.doodadsItemType = doodadsItemType;
	}


	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.translatable("item.doodads."+ doodadsItemType.name +".tooltip"));
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		if (DoodadBundle.dropAllBundledItems(itemStack, user)) {
			this.playDropContentsSound(user);
			user.incrementStat(Stats.USED.getOrCreateStat(this));
			return TypedActionResult.success(itemStack, world.isClient());
		}
		return TypedActionResult.fail(itemStack);
	}

	private static boolean dropAllBundledItems(ItemStack stack, PlayerEntity player) {
		if (player instanceof ServerPlayerEntity) {
			player.dropItem(new ItemStack(DItems.getRandomBundleItem()), true);
		}
		stack.decrement(1);
		return true;
	}

	private void playDropContentsSound(Entity entity) {
		entity.playSound(SoundEvents.ITEM_BUNDLE_DROP_CONTENTS, 0.8f, 0.8f + entity.getWorld().getRandom().nextFloat() * 0.4f);
	}

}

