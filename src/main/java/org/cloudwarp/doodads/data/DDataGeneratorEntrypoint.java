package org.cloudwarp.doodads.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

public class DDataGeneratorEntrypoint implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator (FabricDataGenerator fabricDataGenerator) {
		fabricDataGenerator.addProvider(DBlockLootTableProvider::new);
		fabricDataGenerator.addProvider(new DItemTagProvider(fabricDataGenerator,null));
	}
}
