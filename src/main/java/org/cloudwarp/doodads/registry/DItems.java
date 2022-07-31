package org.cloudwarp.doodads.registry;

import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.cloudwarp.doodads.Doodads;
import org.cloudwarp.doodads.item.*;
import org.cloudwarp.doodads.trinket.*;

import static org.cloudwarp.doodads.utils.DoodadsItemTypes.*;

import java.util.HashMap;

public class DItems {
	public static final ItemGroup DOODADS_GROUP = FabricItemGroupBuilder.create(
					new Identifier("doodads", "general"))
			.icon(() -> SLINGSHOT.itemStack())
			.build();
	private static final HashMap<String, Item> ITEMS = new HashMap<>();

	private static void registerItem (String id, Item item) {
		ITEMS.put(id, Registry.register(Registry.ITEM, Doodads.id(id), item));
	}

	public static void registerItems () {
		if (! ITEMS.isEmpty()) {
			return;
		}
		registerItem(SLINGSHOT.name, new SlingShotItem(new Item.Settings().group(DOODADS_GROUP).maxDamage(640), SLINGSHOT));
		registerItem(PEBBLE.name, new PebbleItem(new Item.Settings().group(DOODADS_GROUP).maxCount(PEBBLE.maxCount), PEBBLE));
		registerItem(PAINTBRUSH.name, new PaintbrushItem(new Item.Settings().group(DOODADS_GROUP).maxDamage(640), PAINTBRUSH));
		registerItem(BEAVER_TEETH.name, new BeaverTeeth(new Item.Settings().group(DOODADS_GROUP).maxCount(1)));
		registerItem(ENDER_GOGGLES.name, new EnderGoggles(new Item.Settings().group(DOODADS_GROUP).maxCount(1)));
		registerItem(SPEED_BOOTS.name, new SpeedBoots(new Item.Settings().group(DOODADS_GROUP).maxCount(1)));
		registerItem(BENDY_STRAW.name, new BendyStraw(new Item.Settings().group(DOODADS_GROUP).maxCount(1)));
		registerItem(SOGGY_GLOVE.name, new SoggyGlove(new Item.Settings().group(DOODADS_GROUP).maxCount(1)));
		registerItem(LOGGERS_GLOVE.name, new LoggersGlove(new Item.Settings().group(DOODADS_GROUP).maxCount(1)));
		registerItem(SUN_RING.name, new SunRing(new Item.Settings().group(DOODADS_GROUP).maxCount(1)));
		registerItem(MOON_RING.name, new MoonRing(new Item.Settings().group(DOODADS_GROUP).maxCount(1)));
		registerItem(CELESTIAL_RING.name, new CelestialRing(new Item.Settings().group(DOODADS_GROUP).maxCount(1)));
		registerItem(CACTUS_RING.name, new CactusRing(new Item.Settings().group(DOODADS_GROUP).maxCount(1)));
		registerItem(MIDNIGHTS_EYE.name, new MidnightsEye(new Item.Settings().group(DOODADS_GROUP).maxCount(1)));
		registerItem(GLARE_PLUSHIE.name, new GlarePlushie(new Item.Settings().group(DOODADS_GROUP).maxCount(1)));
		registerItem(SCISSORS.name, new DoodadsItem(new Item.Settings().group(DOODADS_GROUP).maxCount(SCISSORS.maxCount), SCISSORS));
		registerItem(GLARE_STAFF.name, new GlareStaffItem(new Item.Settings().group(DOODADS_GROUP).maxCount(GLARE_STAFF.maxCount), GLARE_STAFF));
		registerItem(DUCT_TAPE.name, new DuctTapeItem(new Item.Settings().group(DOODADS_GROUP).maxCount(DUCT_TAPE.maxCount), DUCT_TAPE));
		registerItem(MAGIC_PLUM.name, new MagicPlum(new Item.Settings().group(DOODADS_GROUP).maxCount(1).food(DFoodComponents.MAGIC_PLUM_FOOD), MAGIC_PLUM));
	}

	public static Item get (String id) {
		return ITEMS.getOrDefault(id, Items.AIR);
	}
}
