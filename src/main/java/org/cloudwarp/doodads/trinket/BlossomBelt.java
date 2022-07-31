package org.cloudwarp.doodads.trinket;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static org.cloudwarp.doodads.utils.DoodadsItemTypes.BLOSSOM_BELT;
import static org.cloudwarp.doodads.utils.DoodadsItemTypes.SHULKER_AGLET;

public class BlossomBelt extends TrinketItem {
	public BlossomBelt (Settings settings) {
		super(settings);
	}

	@Override
	public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
		if (entity instanceof PlayerEntity player && !entity.world.isClient && entity.age % 2 == 0) {
			boolean hasStatusEffect = entity.hasStatusEffect(StatusEffects.POISON);
			if (hasStatusEffect) {
				entity.removeStatusEffect(StatusEffects.POISON);
			}
		}
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.translatable("item.doodads."+ BLOSSOM_BELT.name +".tooltip"));
	}
}
