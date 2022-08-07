package org.cloudwarp.doodads.item;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.cloudwarp.doodads.interfaces.LivingEntityInterface;
import org.cloudwarp.doodads.interfaces.PlayerEntityInterface;
import org.cloudwarp.doodads.utils.DoodadsItemTypes;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MagicPlum extends Item {
	public DoodadsItemTypes doodadsItemType;

	public MagicPlum (Settings settings, DoodadsItemTypes doodadsItemType) {
		super(settings);
		this.doodadsItemType = doodadsItemType;
	}

	@Override
	public ItemStack finishUsing (ItemStack stack, World world, LivingEntity user) {
		PlayerEntity playerEntity;
		PlayerEntity playerEntity2 = playerEntity = user instanceof PlayerEntity ? (PlayerEntity) user : null;
		if (playerEntity instanceof ServerPlayerEntity) {
			Criteria.CONSUME_ITEM.trigger((ServerPlayerEntity) playerEntity, stack);
		}
		((LivingEntityInterface) user).setPlum(true);
		if (this.isFood()) {
			return user.eatFood(world, stack);
		}
		return stack;
	}

	@Override
	public TypedActionResult<ItemStack> use (World world, PlayerEntity user, Hand hand) {
		if (this.isFood()) {
			ItemStack itemStack = user.getStackInHand(hand);
			if (user.canConsume(this.getFoodComponent().isAlwaysEdible()) && ! ((PlayerEntityInterface) user).hasPlum()) {
				user.setCurrentHand(hand);
				return TypedActionResult.consume(itemStack);
			}
			return TypedActionResult.fail(itemStack);
		}
		return TypedActionResult.pass(user.getStackInHand(hand));
	}

	@Override
	public void appendTooltip (ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		if (Screen.hasShiftDown()) {
			tooltip.add(new TranslatableText("item.doodads." + doodadsItemType.name + ".tooltip.shift"));
		} else {
			tooltip.add(new TranslatableText("item.doodads.generic_tooltip"));
		}
	}

	@Override
	public boolean hasGlint (ItemStack stack) {
		return true;
	}

}

