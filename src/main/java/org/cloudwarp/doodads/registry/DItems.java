package org.cloudwarp.doodads.registry;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.cloudwarp.doodads.Doodads;
import org.cloudwarp.doodads.item.*;
import org.cloudwarp.doodads.trinket.*;
import org.cloudwarp.doodads.utils.DoodadsItemTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class DItems {
	public static final ItemGroup DOODADS_GROUP = FabricItemGroupBuilder.create(
					new Identifier("doodads", "general"))
			.icon(() -> DoodadsItemTypes.SLINGSHOT.itemStack())
			.build();
	public static final HashMap<String, Item> ITEMS = new HashMap<>();
	public static final HashMap<Item, Identifier> BUNDLE_ITEMS = new HashMap<>();
	//private static final ArrayList<Item> COMMON_TRINKETS = new ArrayList<>();
	//private static final ArrayList<Item> UNCOMMON_TRINKETS = new ArrayList<>();
	//private static final ArrayList<Item> RARE_TRINKETS = new ArrayList<>();
	//private static final ArrayList<Item> ULTRA_RARE_TRINKETS = new ArrayList<>();
	static Random rand = new Random();


	private static Item registerItem (String id, Item item, boolean isBundleItem) {
		if(isBundleItem){
			addBundleItem(item,Doodads.id(id));
		}
		return ITEMS.put(id, Registry.register(Registry.ITEM, Doodads.id(id), item));
	}
	private static void addBundleItem (Item item, Identifier id) {
		BUNDLE_ITEMS.put(item,id);
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
	//public static final Item SLINGSHOT;
	//public static final Item SPORE_SWORD;
	//public static Item PEBBLE;
	//public static final Item PAINTBRUSH;
	//public static final Item BEAVER_TEETH;
	//public static final Item ENDER_GOGGLES;
	//public static final Item SPEED_BOOTS;
	//public static final Item BENDY_STRAW;
	//public static final Item SOGGY_GLOVE;
	//public static final Item LOGGERS_GLOVE;
	//public static final Item SUN_RING;
	//public static final Item MOON_RING;
	//public static final Item CELESTIAL_RING;
	//public static final Item CACTUS_RING;
	//public static final Item MIDNIGHTS_EYE;
	//public static final Item GLARE_PLUSHIE;
	//public static final Item SLIMEY_SHOES;
	//public static final Item SHULKER_AGLET;
	//public static final Item BLOSSOM_BELT;
	//public static final Item SCISSORS;
	//public static final Item DOODAD_BUNDLE;
	//public static final Item RUBBER_BAND;
	//public static final Item GLARE_STAFF;
	//public static final Item DUCT_TAPE;
	//public static final Item MAGIC_PLUM;

	public static void registerItems () {
		if (! ITEMS.isEmpty()) {
			return;}

		registerItem(DoodadsItemTypes.SLINGSHOT.name, new SlingShotItem(new Item.Settings().group(DOODADS_GROUP), DoodadsItemTypes.SLINGSHOT), false);
		registerItem(DoodadsItemTypes.SPORE_SWORD.name, new SporeSwordItem(new Item.Settings().group(DOODADS_GROUP), DoodadsItemTypes.SPORE_SWORD, DToolMaterials.SPORE,3,-2.4f), true);
		registerItem(DoodadsItemTypes.PEBBLE.name, new PebbleItem(new Item.Settings().group(DOODADS_GROUP).maxCount(DoodadsItemTypes.PEBBLE.maxCount), DoodadsItemTypes.PEBBLE), false);
		registerItem(DoodadsItemTypes.PAINTBRUSH.name, new PaintbrushItem(new Item.Settings().group(DOODADS_GROUP).maxDamage(640), DoodadsItemTypes.PAINTBRUSH), true);
		registerItem(DoodadsItemTypes.BEAVER_TEETH.name, new BeaverTeeth(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(DoodadsItemTypes.ENDER_GOGGLES.name, new EnderGoggles(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(DoodadsItemTypes.SPEED_BOOTS.name, new SpeedBoots(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(DoodadsItemTypes.BENDY_STRAW.name, new BendyStraw(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(DoodadsItemTypes.SOGGY_GLOVE.name, new SoggyGlove(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(DoodadsItemTypes.LOGGERS_GLOVE.name, new LoggersGlove(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(DoodadsItemTypes.SUN_RING.name, new SunRing(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(DoodadsItemTypes.MOON_RING.name, new MoonRing(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(DoodadsItemTypes.CELESTIAL_RING.name, new CelestialRing(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(DoodadsItemTypes.CACTUS_RING.name, new CactusRing(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(DoodadsItemTypes.MIDNIGHTS_EYE.name, new MidnightsEye(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(DoodadsItemTypes.GLARE_PLUSHIE.name, new GlarePlushie(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(DoodadsItemTypes.SLIMEY_SHOES.name, new SlimeyShoes(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(DoodadsItemTypes.SHULKER_AGLET.name, new ShulkerAglet(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(DoodadsItemTypes.BLOSSOM_BELT.name, new BlossomBelt(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(DoodadsItemTypes.SCISSORS.name, new DoodadsItem(new Item.Settings().group(DOODADS_GROUP).maxCount(DoodadsItemTypes.SCISSORS.maxCount), DoodadsItemTypes.SCISSORS), true);
		registerItem(DoodadsItemTypes.DOODAD_BUNDLE.name, new DoodadBundleItem(new Item.Settings().group(DOODADS_GROUP).maxCount(DoodadsItemTypes.DOODAD_BUNDLE.maxCount), DoodadsItemTypes.DOODAD_BUNDLE), false);
		registerItem(DoodadsItemTypes.RUBBER_BAND.name, new RubberBandItem(new Item.Settings().group(DOODADS_GROUP).maxCount(DoodadsItemTypes.RUBBER_BAND.maxCount), DoodadsItemTypes.RUBBER_BAND), false);
		registerItem(DoodadsItemTypes.GLARE_STAFF.name, new GlareStaffItem(new Item.Settings().group(DOODADS_GROUP).maxCount(DoodadsItemTypes.GLARE_STAFF.maxCount), DoodadsItemTypes.GLARE_STAFF), true);
		registerItem(DoodadsItemTypes.DUCT_TAPE.name, new DuctTapeItem(new Item.Settings().group(DOODADS_GROUP).maxCount(DoodadsItemTypes.DUCT_TAPE.maxCount), DoodadsItemTypes.DUCT_TAPE), true);
		registerItem(DoodadsItemTypes.MAGIC_PLUM.name, new MagicPlum(new Item.Settings().group(DOODADS_GROUP).maxCount(1).food(DFoodComponents.MAGIC_PLUM_FOOD), DoodadsItemTypes.MAGIC_PLUM), false);
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
		trinket = ((Item[])BUNDLE_ITEMS.keySet().toArray())[(int)(rand.nextFloat() * BUNDLE_ITEMS.size())];
		return trinket;
	}
}
