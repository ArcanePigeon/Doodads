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
	SPEED_BOOTS(1,"speed_boots"),
	BENDY_STRAW(1,"bendy_straw"),
	MAGIC_PLUM(1,"magic_plum"),
	PAINTBRUSH(1,"paintbrush"),
	SCISSORS(1,"scissors"),
	DUCT_TAPE(1,"duct_tape"),
	SOGGY_GLOVE(1,"soggy_glove"),
	LOGGERS_GLOVE(1,"loggers_glove"),
	SUN_RING(1,"sun_ring"),
	MOON_RING(1,"moon_ring"),
	CELESTIAL_RING(1,"celestial_ring"),
	CACTUS_RING(1,"cactus_ring"),
	MIDNIGHTS_EYE(1,"midnights_eye"),
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
