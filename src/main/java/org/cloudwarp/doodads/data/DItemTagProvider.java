package org.cloudwarp.doodads.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import org.cloudwarp.doodads.registry.DItems;
import org.cloudwarp.doodads.registry.DTagKeys;
import org.cloudwarp.doodads.utils.DoodadsItemTypes;
import org.jetbrains.annotations.Nullable;

public class DItemTagProvider extends FabricTagProvider.ItemTagProvider {

	public DItemTagProvider (FabricDataGenerator dataGenerator, @Nullable BlockTagProvider blockTagProvider) {
		super(dataGenerator, blockTagProvider);
	}

	@Override
	public void generateTags () {
		DItems.BUNDLE_ITEMS.forEach((item, identifier) -> getOrCreateTagBuilder(DTagKeys.DOODAD_BUNDLE_CRAFTABLE).addOptional(identifier));
	}
}
