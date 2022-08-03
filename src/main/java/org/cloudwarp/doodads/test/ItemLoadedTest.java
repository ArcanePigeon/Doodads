package org.cloudwarp.doodads.test;

import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;
import org.cloudwarp.doodads.Doodads;

public class ItemLoadedTest {
	public static void init(){
		ResourceConditions.register(Doodads.id("all_items_present"), json -> {
			var values = JsonHelper.getArray(json, "values");

			for (var value : values) {
				if (! Registry.ITEM.containsId(new Identifier(value.getAsString()))) {
					return false;
				}
			}
			return true;
		});
		ResourceConditions.register(Doodads.id("all_blocks_present"), json -> {
			var values = JsonHelper.getArray(json, "values");

			for (var value : values) {
				if (!Registry.BLOCK.containsId(new Identifier(value.getAsString()))) {
					return false;
				}
			}
			return true;
		});
	}
}
