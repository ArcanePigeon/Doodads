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
import java.util.HashSet;
import java.util.Random;

public class DItems {
	public static final ItemGroup DOODADS_GROUP = FabricItemGroupBuilder.create(
					new Identifier("doodads", "general"))
			.icon(() -> DoodadsItemTypes.SLINGSHOT.itemStack())
			.build();
	public static final HashMap<String, Item> ITEMS = new HashMap<>();
	public static final HashSet<Identifier> ENABLED_ITEMS = new HashSet<>();
	public static final HashMap<Item, Identifier> BUNDLE_ITEMS = new HashMap<>();
	static Random rand = new Random();


	private static Item registerItem (boolean configEntry, String id, Item item, boolean isBundleItem) {
		if(configEntry){
			ENABLED_ITEMS.add(Doodads.id(id));
			if(isBundleItem){
				addBundleItem(item,Doodads.id(id));
			}
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

	public static void registerItems () {
		if (! ITEMS.isEmpty()) {
			return;}

		registerItem(Doodads.loadedConfig.doodadItems.enableSlingshot,DoodadsItemTypes.SLINGSHOT.name, new SlingShotItem(new Item.Settings().group(DOODADS_GROUP), DoodadsItemTypes.SLINGSHOT), false);
		registerItem(Doodads.loadedConfig.doodadItems.enableSporeSword,DoodadsItemTypes.SPORE_SWORD.name, new SporeSwordItem(new Item.Settings().group(DOODADS_GROUP), DoodadsItemTypes.SPORE_SWORD, DToolMaterials.SPORE,3,-2.4f), true);
		registerItem(Doodads.loadedConfig.doodadItems.enablePebble,DoodadsItemTypes.PEBBLE.name, new PebbleItem(new Item.Settings().group(DOODADS_GROUP).maxCount(DoodadsItemTypes.PEBBLE.maxCount), DoodadsItemTypes.PEBBLE), false);
		registerItem(Doodads.loadedConfig.doodadItems.enablePaintbrush,DoodadsItemTypes.PAINTBRUSH.name, new PaintbrushItem(new Item.Settings().group(DOODADS_GROUP).maxCount(1), DoodadsItemTypes.PAINTBRUSH), true);
		registerItem(Doodads.loadedConfig.doodadTrinkets.enableBeaverTeeth,DoodadsItemTypes.BEAVER_TEETH.name, new BeaverTeeth(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(Doodads.loadedConfig.doodadTrinkets.enableEnderGoggles,DoodadsItemTypes.ENDER_GOGGLES.name, new EnderGoggles(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(Doodads.loadedConfig.doodadTrinkets.enableSpeedySneakers,DoodadsItemTypes.SPEED_BOOTS.name, new SpeedBoots(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(Doodads.loadedConfig.doodadTrinkets.enableBendyStraw,DoodadsItemTypes.BENDY_STRAW.name, new BendyStraw(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(Doodads.loadedConfig.doodadTrinkets.enableSoggyGlove,DoodadsItemTypes.SOGGY_GLOVE.name, new SoggyGlove(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(Doodads.loadedConfig.doodadTrinkets.enableLoggersGlove,DoodadsItemTypes.LOGGERS_GLOVE.name, new LoggersGlove(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(Doodads.loadedConfig.doodadTrinkets.enableSunStoneRing,DoodadsItemTypes.SUN_RING.name, new SunRing(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(Doodads.loadedConfig.doodadTrinkets.enableMoonStoneRing,DoodadsItemTypes.MOON_RING.name, new MoonRing(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(Doodads.loadedConfig.doodadTrinkets.enableCelestialStoneRing,DoodadsItemTypes.CELESTIAL_RING.name, new CelestialRing(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(Doodads.loadedConfig.doodadTrinkets.enableCactusRing,DoodadsItemTypes.CACTUS_RING.name, new CactusRing(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(Doodads.loadedConfig.doodadTrinkets.enableMidnightsEye,DoodadsItemTypes.MIDNIGHTS_EYE.name, new MidnightsEye(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(Doodads.loadedConfig.doodadTrinkets.enableGlarePlushie,DoodadsItemTypes.GLARE_PLUSHIE.name, new GlarePlushie(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(Doodads.loadedConfig.doodadTrinkets.enableSlimeyShoes,DoodadsItemTypes.SLIMEY_SHOES.name, new SlimeyShoes(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(Doodads.loadedConfig.doodadTrinkets.enableShulkerAglet,DoodadsItemTypes.SHULKER_AGLET.name, new ShulkerAglet(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(Doodads.loadedConfig.doodadTrinkets.enableBlossomBelt,DoodadsItemTypes.BLOSSOM_BELT.name, new BlossomBelt(new Item.Settings().group(DOODADS_GROUP).maxCount(1)), true);
		registerItem(Doodads.loadedConfig.doodadItems.enableScissors,DoodadsItemTypes.SCISSORS.name, new DoodadsItem(new Item.Settings().group(DOODADS_GROUP).maxCount(DoodadsItemTypes.SCISSORS.maxCount), DoodadsItemTypes.SCISSORS), true);
		registerItem(Doodads.loadedConfig.doodadItems.enableDoodadBundle,DoodadsItemTypes.DOODAD_BUNDLE.name, new DoodadBundleItem(new Item.Settings().group(DOODADS_GROUP).maxCount(DoodadsItemTypes.DOODAD_BUNDLE.maxCount), DoodadsItemTypes.DOODAD_BUNDLE), false);
		registerItem(Doodads.loadedConfig.doodadItems.enableRubberBand,DoodadsItemTypes.RUBBER_BAND.name, new RubberBandItem(new Item.Settings().group(DOODADS_GROUP).maxCount(DoodadsItemTypes.RUBBER_BAND.maxCount), DoodadsItemTypes.RUBBER_BAND), false);
		registerItem(Doodads.loadedConfig.doodadItems.enableGlareStaff,DoodadsItemTypes.GLARE_STAFF.name, new GlareStaffItem(new Item.Settings().group(DOODADS_GROUP).maxCount(DoodadsItemTypes.GLARE_STAFF.maxCount), DoodadsItemTypes.GLARE_STAFF), true);
		registerItem(Doodads.loadedConfig.doodadItems.enableDuctTape,DoodadsItemTypes.DUCT_TAPE.name, new DuctTapeItem(new Item.Settings().group(DOODADS_GROUP).maxCount(DoodadsItemTypes.DUCT_TAPE.maxCount), DoodadsItemTypes.DUCT_TAPE), true);
		registerItem(Doodads.loadedConfig.doodadItems.enableMagicPlum,DoodadsItemTypes.MAGIC_PLUM.name, new MagicPlum(new Item.Settings().group(DOODADS_GROUP).maxCount(1).food(DFoodComponents.MAGIC_PLUM_FOOD), DoodadsItemTypes.MAGIC_PLUM), false);
	}

	public static Item get (String id) {
		return ITEMS.getOrDefault(id, Items.AIR);
	}

	public static Item getRandomBundleItem(){
		if(BUNDLE_ITEMS.isEmpty()){
			return Items.AIR;
		}
		Item trinket;
		trinket = ((Item[])BUNDLE_ITEMS.keySet().toArray())[(int)(rand.nextFloat() * BUNDLE_ITEMS.size())];
		return trinket;
	}
}
