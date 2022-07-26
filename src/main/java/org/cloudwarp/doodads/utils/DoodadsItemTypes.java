package org.cloudwarp.doodads.utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.cloudwarp.doodads.registry.DItems;

import static org.cloudwarp.doodads.Doodads.*;

public enum DoodadsItemTypes {
	SLINGSHOT(1,"slingshot"),
	PEBBLE(64,"pebble"),
	BEAVER_TEETH(1,"beaver_teeth"),
	ENDER_GOGGLES(1,"ender_goggles");

	public final int maxCount;
	public final Identifier id;
	public final String name;

	DoodadsItemTypes (int maxCount, String name) {
		this.maxCount = maxCount;
		this.name = name;
		this.id = id(name);
	}
	public ItemStack itemStack (){
		return new ItemStack(this.item());
	}
	public Item item (){
		return (DItems.get(this.name));
	}
}
