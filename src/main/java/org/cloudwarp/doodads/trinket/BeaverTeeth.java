package org.cloudwarp.doodads.trinket;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotAttributes;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.cloudwarp.doodads.utils.DoodadsItemTypes;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

import static org.cloudwarp.doodads.utils.DoodadsItemTypes.BEAVER_TEETH;

public class BeaverTeeth extends TrinketItem {
	public BeaverTeeth (Settings settings) {
		super(settings);
	}
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		if(Screen.hasShiftDown()){
			tooltip.add(Text.translatable("item.doodads."+ BEAVER_TEETH.name +".tooltip.shift"));
			tooltip.add(Text.translatable("item.doodads."+ BEAVER_TEETH.name +".tooltip2.shift"));
		}else{
			tooltip.add(Text.translatable("item.doodads.generic_tooltip"));
		}
	}
}
