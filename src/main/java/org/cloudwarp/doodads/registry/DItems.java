package org.cloudwarp.doodads.registry;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.cloudwarp.doodads.blockdetails.DoodadsItem;
import org.cloudwarp.doodads.utils.DoodadsItemTypes;

import java.util.HashMap;

public class DItems {
	public static final ItemGroup DOODADS_GROUP = FabricItemGroupBuilder.create(
					new Identifier("doodads", "general"))
			.icon(() -> new ItemStack(get("sling_shot")))
			.build();
	private static final HashMap<String, Item> ITEMS = new HashMap<>();

	private static void registerItem (String id, Item item) {
		ITEMS.put(id, Registry.register(Registry.ITEM, "doodads:" + id, item));
	}

	public static void registerItems () {
		if (! ITEMS.isEmpty()) {
			return;
		}
		registerItem("sling_shot", new DoodadsItem(new Item.Settings().maxCount(1).group(DOODADS_GROUP), DoodadsItemTypes.SLING_SHOT));

	}

	public static Item get (String id) {
		return ITEMS.getOrDefault(id, Items.AIR);
	}
}
