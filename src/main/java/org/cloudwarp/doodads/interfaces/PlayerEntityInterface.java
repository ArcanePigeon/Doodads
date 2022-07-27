package org.cloudwarp.doodads.interfaces;

import net.minecraft.item.ItemStack;

public interface PlayerEntityInterface extends LivingEntityInterface{
	ItemStack getPebbleType(ItemStack stack);
}
