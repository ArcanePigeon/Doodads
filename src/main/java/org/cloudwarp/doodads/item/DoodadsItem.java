package org.cloudwarp.doodads.item;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.cloudwarp.doodads.utils.DoodadsItemTypes;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DoodadsItem extends Item {
	public DoodadsItemTypes doodadsItemType;

	public DoodadsItem (Item.Settings settings, DoodadsItemTypes doodadsItemType) {
		super(settings);
		this.doodadsItemType = doodadsItemType;
	}


	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		if(Screen.hasShiftDown()){
			tooltip.add(Text.translatable("item.doodads."+ doodadsItemType.name +".tooltip.shift"));
		}else{
			tooltip.add(Text.translatable("item.doodads.generic_tooltip"));
		}
	}

}

