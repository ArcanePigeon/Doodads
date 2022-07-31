package org.cloudwarp.doodads.trinket;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotAttributes;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

import static org.cloudwarp.doodads.utils.DoodadsItemTypes.CACTUS_RING;
import static org.cloudwarp.doodads.utils.DoodadsItemTypes.MIDNIGHTS_EYE;

public class MidnightsEye extends TrinketItem {
	public MidnightsEye (Settings settings) {
		super(settings);
	}

	@Override
	public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
		if (entity instanceof PlayerEntity player && !entity.world.isClient && entity.age % 10 == 0) {
			StatusEffectInstance nightVision = new StatusEffectInstance(StatusEffects.NIGHT_VISION, 300, 0, true, false);
			boolean canApply = entity.canHaveStatusEffect(nightVision);
			if (canApply) {
				entity.setStatusEffect(nightVision,null);
			}
		}
		//if (entity instanceof PlayerEntity player && !entity.world.isClient && entity.age % 10 == 0 &&
		//		entity.world.getBlockState(entity.getBlockPos().down()).hasSolidTopSurface(entity.world,entity.getBlockPos().down(),entity) &&
		//		entity.world.getLightLevel(LightType.BLOCK,entity.getBlockPos()) == 0) {
		//	entity.world.setBlockState(entity.getBlockPos(), Blocks.TORCH.getDefaultState());
		//}
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.translatable("item.doodads."+ MIDNIGHTS_EYE.name +".tooltip"));
	}
}
