package org.cloudwarp.doodads.registry;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.cloudwarp.doodads.blockdetails.DoodadsItem;
import org.cloudwarp.doodads.blockdetails.PebbleItem;
import org.cloudwarp.doodads.blockdetails.SlingShotItem;
import org.cloudwarp.doodads.utils.DoodadsItemTypes;

import java.util.HashMap;

public class DItems {
	public static final ItemGroup DOODADS_GROUP = FabricItemGroupBuilder.create(
					new Identifier("doodads", "general"))
			.icon(() -> new ItemStack(get("slingshot")))
			.build();
	private static final HashMap<String, Item> ITEMS = new HashMap<>();

	private static void registerItem (String id, Item item) {
		ITEMS.put(id, Registry.register(Registry.ITEM, "doodads:" + id, item));
	}

	public static void registerItems () {
		if (! ITEMS.isEmpty()) {
			return;
		}
		registerItem("slingshot", new SlingShotItem(new Item.Settings().group(DOODADS_GROUP).maxDamage(640), DoodadsItemTypes.SLINGSHOT));
		registerItem("pebble", new PebbleItem(new Item.Settings().group(DOODADS_GROUP).maxCount(DoodadsItemTypes.PEBBLE.maxCount), DoodadsItemTypes.PEBBLE));
	}

	public static Item get (String id) {
		return ITEMS.getOrDefault(id, Items.AIR);
	}
}
