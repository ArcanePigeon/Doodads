package org.cloudwarp.doodads.registry;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;
import org.cloudwarp.doodads.Doodads;

import static org.cloudwarp.doodads.registry.DTagKeys.*;

public class DDataGeneratorEntrypoint implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator (FabricDataGenerator fabricDataGenerator) {
		fabricDataGenerator.addProvider(DBlockTagProvider::new);
	}
	private static class DBlockTagProvider extends FabricTagProvider.BlockTagProvider {

		public DBlockTagProvider (FabricDataGenerator dataGenerator) {
			super(dataGenerator);
		}
		@Override
		protected void generateTags () {
			//this.getOrCreateTagBuilder(CONCRETES).add((Block[]) new Block[]{Blocks.WHITE_CONCRETE, Blocks.ORANGE_CONCRETE, Blocks.MAGENTA_CONCRETE, Blocks.LIGHT_BLUE_CONCRETE, Blocks.YELLOW_CONCRETE, Blocks.LIME_CONCRETE, Blocks.PINK_CONCRETE, Blocks.GRAY_CONCRETE, Blocks.LIGHT_GRAY_CONCRETE, Blocks.CYAN_CONCRETE, Blocks.PURPLE_CONCRETE, Blocks.BLUE_CONCRETE, Blocks.BROWN_CONCRETE, Blocks.GREEN_CONCRETE, Blocks.RED_CONCRETE, Blocks.BLACK_CONCRETE});
		}
	}
}
