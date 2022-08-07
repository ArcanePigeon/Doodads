package org.cloudwarp.doodads.trinket;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static org.cloudwarp.doodads.utils.DoodadsItemTypes.MIDNIGHTS_EYE;
import static org.cloudwarp.doodads.utils.DoodadsItemTypes.SHULKER_AGLET;

public class ShulkerAglet extends TrinketItem {
	public ShulkerAglet (Settings settings) {
		super(settings);
	}

	@Override
	public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
		if (entity instanceof PlayerEntity player && !entity.world.isClient && entity.age % 2 == 0) {
			boolean hasStatusEffect = entity.hasStatusEffect(StatusEffects.LEVITATION);
			if (hasStatusEffect) {
				entity.removeStatusEffect(StatusEffects.LEVITATION);
			}
		}
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(new TranslatableText("item.doodads."+ SHULKER_AGLET.name +".tooltip"));
	}
}
