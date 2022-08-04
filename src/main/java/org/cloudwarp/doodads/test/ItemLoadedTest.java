package org.cloudwarp.doodads.test;

import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;
import org.cloudwarp.doodads.Doodads;
import org.cloudwarp.doodads.registry.DBlocks;
import org.cloudwarp.doodads.registry.DItems;

public class ItemLoadedTest {
	public static void init(){
		ResourceConditions.register(Doodads.id("all_items_present"), json -> {
			var values = JsonHelper.getArray(json, "values");

			for (var value : values) {
				if (! DItems.ENABLED_ITEMS.contains(new Identifier(value.getAsString()))) {
					return false;
				}
			}
			return true;
		});
		ResourceConditions.register(Doodads.id("all_blocks_present"), json -> {
			var values = JsonHelper.getArray(json, "values");

			for (var value : values) {
				if (! DBlocks.ENABLED_BLOCKS.contains(new Identifier(value.getAsString()))) {
					return false;
				}
			}
			return true;
		});
	}
}
