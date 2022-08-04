package org.cloudwarp.doodads.registry;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;
import org.cloudwarp.doodads.Doodads;
import org.cloudwarp.doodads.utils.DoodadsItemTypes;

import java.util.HashSet;
import java.util.Random;

import static org.cloudwarp.doodads.utils.DoodadsItemTypes.*;

public class DLootTableModifiers {

	private static Identifier mcId (String path) {
		return new Identifier("minecraft", path);
	}
	private static Identifier pcId (String path) {
		return new Identifier("probablychests", path);
	}

	private static final Random random = new Random();
	private static final HashSet<Identifier> CHESTS = new HashSet<>();
	private static final HashSet<Identifier> ENTITIES = new HashSet<>();

	public static void init () {
		ENTITIES.add(mcId("entities/zombie"));
		ENTITIES.add(mcId("entities/drowned"));
		ENTITIES.add(mcId("entities/skeleton"));
		ENTITIES.add(mcId("entities/spider"));
		ENTITIES.add(mcId("entities/creeper"));
		ENTITIES.add(mcId("entities/enderman"));

		if (Doodads.loadedConfig.doodadWorldGen.enableDoodadSpawnsInVillages) {
			CHESTS.add(mcId("chests/village/villager_armorer"));
			CHESTS.add(mcId("chests/village/villager_butcher"));
			CHESTS.add(mcId("chests/village/villager_cartographer"));
			CHESTS.add(mcId("chests/village/villager_desert_house"));
			CHESTS.add(mcId("chests/village/villager_fisher"));
			CHESTS.add(mcId("chests/village/villager_fletcher"));
			CHESTS.add(mcId("chests/village/villager_mason"));
			CHESTS.add(mcId("chests/village/villager_plains_house"));
			CHESTS.add(mcId("chests/village/villager_savanna_house"));
			CHESTS.add(mcId("chests/village/villager_shepherd"));
			CHESTS.add(mcId("chests/village/villager_snowy_house"));
			CHESTS.add(mcId("chests/village/villager_taiga_house"));
			CHESTS.add(mcId("chests/village/villager_tannery"));
			CHESTS.add(mcId("chests/village/villager_temple"));
			CHESTS.add(mcId("chests/village/villager_toolsmith"));
			CHESTS.add(mcId("chests/village/villager_weaponsmith"));
		}
		if(FabricLoader.getInstance().isModLoaded("probablychests")){
			CHESTS.add(pcId("chests/normal_items"));
		}

		CHESTS.add(mcId("chests/abandoned_mineshaft"));
		CHESTS.add(mcId("chests/ancient_city"));
		CHESTS.add(mcId("chests/ancient_city_ice_box"));
		CHESTS.add(mcId("chests/bastion_bridge"));
		CHESTS.add(mcId("chests/bastion_hoglin_stable"));
		CHESTS.add(mcId("chests/bastion_other"));
		CHESTS.add(mcId("chests/bastion_treasure"));
		CHESTS.add(mcId("chests/buried_treasure"));
		CHESTS.add(mcId("chests/desert_pyramid"));
		CHESTS.add(mcId("chests/end_city_treasure"));
		CHESTS.add(mcId("chests/igloo_chest"));
		CHESTS.add(mcId("chests/jungle_temple"));
		CHESTS.add(mcId("chests/nether_bridge"));
		CHESTS.add(mcId("chests/pillager_outpost"));
		CHESTS.add(mcId("chests/ruined_portal"));
		CHESTS.add(mcId("chests/shipwreck_map"));
		CHESTS.add(mcId("chests/shipwreck_supply"));
		CHESTS.add(mcId("chests/shipwreck_treasure"));
		CHESTS.add(mcId("chests/simple_dungeon"));
		CHESTS.add(mcId("chests/stronghold_corridor"));
		CHESTS.add(mcId("chests/stronghold_crossing"));
		CHESTS.add(mcId("chests/stronghold_library"));
		CHESTS.add(mcId("chests/underwater_ruin_big"));
		CHESTS.add(mcId("chests/underwater_ruin_small"));
		CHESTS.add(mcId("chests/woodland_mansion"));
		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (CHESTS.contains(id)) {
				LootPool.Builder poolBuilder = LootPool.builder();
				DItems.BUNDLE_ITEMS.forEach((item, identifier) -> {
					poolBuilder.with(ItemEntry.builder(item)).conditionally(RandomChanceLootCondition.builder(0.05f * Doodads.loadedConfig.doodadWorldGen.chestDoodadSpawnRate));
				});
				tableBuilder.pool(poolBuilder);
			}
			if (ENTITIES.contains(id)) {
				LootPool.Builder poolBuilder = LootPool.builder();
				DItems.BUNDLE_ITEMS.forEach((item, identifier) -> {
					poolBuilder.with(ItemEntry.builder(item)).conditionally(RandomChanceLootCondition.builder(0.05f * Doodads.loadedConfig.doodadWorldGen.entityDoodadDropRate));
				});
				tableBuilder.pool(poolBuilder);

			}
		});
	}
}
