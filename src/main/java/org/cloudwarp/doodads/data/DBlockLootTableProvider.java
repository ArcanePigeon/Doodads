package org.cloudwarp.doodads.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import org.cloudwarp.doodads.registry.DBlocks;

public class DBlockLootTableProvider extends FabricBlockLootTableProvider {
	public DBlockLootTableProvider (FabricDataGenerator dataGenerator) {
		super(dataGenerator);
	}

	@Override
	public void generateBlockLootTables () {
		addDrop(DBlocks.PORTALBE_NETHER);
		addDrop(DBlocks.ASPHALT);
		addDrop(DBlocks.BRICK_ROAD);
		addDrop(DBlocks.STONE_BRICK_ROAD);
		addDrop(DBlocks.YELLOW_BRICK_ROAD);
		addDrop(DBlocks.PLATFORM);
	}
}
