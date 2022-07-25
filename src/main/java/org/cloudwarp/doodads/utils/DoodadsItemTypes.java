package org.cloudwarp.doodads.utils;
import net.minecraft.util.Identifier;

import static org.cloudwarp.doodads.Doodads.*;

public enum DoodadsItemTypes {
	SLINGSHOT(1,"slingshot"),
	PEBBLE(64,"pebble");

	public final int maxCount;
	public final Identifier id;
	public final String name;

	DoodadsItemTypes (int maxCount, String name) {
		this.maxCount = maxCount;
		this.name = name;
		this.id = id(name);
	}
}
