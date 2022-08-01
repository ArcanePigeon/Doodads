package org.cloudwarp.doodads.registry;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.cloudwarp.doodads.Doodads;
import org.cloudwarp.doodads.item.*;
import org.cloudwarp.doodads.trinket.*;

import static org.cloudwarp.doodads.utils.DoodadsItemTypes.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class DItems {
	public static final ItemGroup DOODADS_GROUP = FabricItemGroupBuilder.create(
					new Identifier("doodads", "general"))
			.icon(() -> SLINGSHOT.itemStack())
			.build();
	private static final HashMap<String, Item> ITEMS = new HashMap<>();
	private static final ArrayList<Item> BUNDLE_ITEMS = new ArrayList<>();
	//private static final ArrayList<Item> COMMON_TRINKETS = new ArrayList<>();
	//private static final ArrayList<Item> UNCOMMON_TRINKETS = new ArrayList<>();
	//private static final ArrayList<Item> RARE_TRINKETS = new ArrayList<>();
	//private static final ArrayList<Item> ULTRA_RARE_TRINKETS = new ArrayList<>();
	static Random rand = new Random();


	private static void registerItem (String id, Item item, boolean isBundleItem) {
		ITEMS.put(id, Registry.register(Registry.ITEM, Doodads.id(id), item));
		if(isBundleItem){
			addBundleItem(item);
		}
	}
	private static void addBundleItem (Item item) {
		BUNDLE_ITEMS.add(item);
	}
	/*private static void addCommonTrinket (Item item) {
		COMMON_TRINKETS.add(item);
	}
	private static void addUncommonTrinket (Item item) {
		UNCOMMON_TRINKETS.add(item);
	}
	private static void addRareTrinket (Item item) {
		RARE_TRINKETS.add(item);
	}
	private static void addUltraRareTrinket (Item item) {
		ULTRA_RARE_TRINKETS.add(item);
	}*/

	public static void registerItems () {
		if (! ITEMS.isEmpty()) {
			return;}
		registerItem(SLINGSHOT.name, new SlingShotItem(new Item.Settings().group(DOODADS_GROUP).maxDamage(640), SLINGSHOT), false);
		registerItem(SPORE_SWORD.name, new SporeSwordItem(new Item.Settings().group(DOODADS_GROUP), SPORE_SWORD, DToolMaterials.SPORE,3,-2.4f), true);
		registerItem(PEBBLE.name, new PebbleItem(new Item.Settings().group(DOODADS_GROUP).maxCount(PEBBLE.maxCount), PEBBLE), false);
		registerItem(PAINTBRUSH.name, new PaintbrushItem(new Item.Settings().group(DOODADS_GROUP).maxDamage(640), PAINTBRUSH), true);
		registerItem(BEAVER_TEETH.name, new BeaverTeeth(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(ENDER_GOGGLES.name, new EnderGoggles(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(SPEED_BOOTS.name, new SpeedBoots(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(BENDY_STRAW.name, new BendyStraw(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(SOGGY_GLOVE.name, new SoggyGlove(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(LOGGERS_GLOVE.name, new LoggersGlove(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(SUN_RING.name, new SunRing(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(MOON_RING.name, new MoonRing(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(CELESTIAL_RING.name, new CelestialRing(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(CACTUS_RING.name, new CactusRing(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(MIDNIGHTS_EYE.name, new MidnightsEye(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(GLARE_PLUSHIE.name, new GlarePlushie(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(SLIMEY_SHOES.name, new SlimeyShoes(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(SHULKER_AGLET.name, new ShulkerAglet(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(BLOSSOM_BELT.name, new BlossomBelt(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(SCISSORS.name, new DoodadsItem(new Item.Settings().group(DOODADS_GROUP).maxCount(SCISSORS.maxCount), SCISSORS), true);
		registerItem(DOODAD_BUNDLE.name, new DoodadBundleItem(new Item.Settings().group(DOODADS_GROUP).maxCount(DOODAD_BUNDLE.maxCount), DOODAD_BUNDLE), false);
		registerItem(RUBBER_BAND.name, new RubberBandItem(new Item.Settings().group(DOODADS_GROUP).maxCount(RUBBER_BAND.maxCount), RUBBER_BAND), false);
		registerItem(GLARE_STAFF.name, new GlareStaffItem(new Item.Settings().group(DOODADS_GROUP).maxCount(GLARE_STAFF.maxCount), GLARE_STAFF), true);
		registerItem(DUCT_TAPE.name, new DuctTapeItem(new Item.Settings().group(DOODADS_GROUP).maxCount(DUCT_TAPE.maxCount), DUCT_TAPE), true);
		registerItem(MAGIC_PLUM.name, new MagicPlum(new Item.Settings().group(DOODADS_GROUP).maxCount(1).food(DFoodComponents.MAGIC_PLUM_FOOD), MAGIC_PLUM), false);
	}

	public static Item get (String id) {
		return ITEMS.getOrDefault(id, Items.AIR);
	}

	public static Item getRandomBundleItem(){
		//float chance = rand.nextFloat() + 0.1f * luck;
		Item trinket;
		//if(chance <= 0.5f && !COMMON_TRINKETS.isEmpty()){
		//	trinket = COMMON_TRINKETS.get((int)(rand.nextFloat() * COMMON_TRINKETS.size()));
		//}else if(chance <= 0.80f && !UNCOMMON_TRINKETS.isEmpty()){
		//	trinket = UNCOMMON_TRINKETS.get((int)(rand.nextFloat() * UNCOMMON_TRINKETS.size()));
		//}else if(chance <= 0.90 && !RARE_TRINKETS.isEmpty()){
		//	trinket = RARE_TRINKETS.get((int)(rand.nextFloat() * RARE_TRINKETS.size()));
		//}else if( chance > 0.90f && !ULTRA_RARE_TRINKETS.isEmpty()){
		//	trinket = ULTRA_RARE_TRINKETS.get((int)(rand.nextFloat() * ULTRA_RARE_TRINKETS.size()));
		//}
		trinket = BUNDLE_ITEMS.get((int)(rand.nextFloat() * BUNDLE_ITEMS.size()));
		return trinket;
	}
}
