package org.cloudwarp.doodads.trinket;

import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.cloudwarp.doodads.registry.DTagKeys;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static org.cloudwarp.doodads.utils.DoodadsItemTypes.BEAVER_TEETH;
import static org.cloudwarp.doodads.utils.DoodadsItemTypes.SOGGY_GLOVE;

public class SoggyGlove extends TrinketItem {
	public SoggyGlove (Settings settings) {
		super(settings);
	}
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		if(Screen.hasShiftDown()){
			tooltip.add(Text.translatable("item.doodads."+ SOGGY_GLOVE.name +".tooltip.shift"));
		}else{
			tooltip.add(Text.translatable("item.doodads.generic_tooltip"));
		}
	}
}
