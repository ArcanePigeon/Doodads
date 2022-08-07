package org.cloudwarp.doodads.trinket;

import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static org.cloudwarp.doodads.utils.DoodadsItemTypes.LOGGERS_GLOVE;
import static org.cloudwarp.doodads.utils.DoodadsItemTypes.SOGGY_GLOVE;

public class LoggersGlove extends TrinketItem {
	public LoggersGlove (Settings settings) {
		super(settings);
	}
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		if(Screen.hasShiftDown()){
			tooltip.add(new TranslatableText("item.doodads."+ LOGGERS_GLOVE.name +".tooltip.shift"));
		}else{
			tooltip.add(new TranslatableText("item.doodads.generic_tooltip"));
		}
	}
}
