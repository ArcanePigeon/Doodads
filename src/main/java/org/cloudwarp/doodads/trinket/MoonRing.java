package org.cloudwarp.doodads.trinket;

import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static org.cloudwarp.doodads.utils.DoodadsItemTypes.MOON_RING;
import static org.cloudwarp.doodads.utils.DoodadsItemTypes.SUN_RING;

public class MoonRing extends TrinketItem {
	public MoonRing (Settings settings) {
		super(settings);
	}
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		if(Screen.hasShiftDown()){
			tooltip.add(Text.translatable("item.doodads."+ MOON_RING.name +".tooltip.shift"));
		}else{
			tooltip.add(Text.translatable("item.doodads.generic_tooltip"));
		}
	}
}
