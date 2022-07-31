package org.cloudwarp.doodads.registry;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.cloudwarp.doodads.Doodads;
import org.cloudwarp.doodads.block.GlareLight;
import org.cloudwarp.doodads.item.*;
import org.cloudwarp.doodads.trinket.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.cloudwarp.doodads.registry.DItems.DOODADS_GROUP;
import static org.cloudwarp.doodads.utils.DoodadsItemTypes.*;

public class DBlocks {
	private static final Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
	private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

	public static final Block GLARE_LIGHT      = create("glare_light", new GlareLight(FabricBlockSettings.of(Material.LEAVES)
			.hardness(0f)
			.resistance(0f)
			.sounds(BlockSoundGroup.CAVE_VINES).breakInstantly().noCollision().nonOpaque().luminance(15)),false);

	private static <T extends Block> T create (String name, T block, boolean createItem) {
		BLOCKS.put(block, Doodads.id(name));
		if (createItem) {
			ITEMS.put(new BlockItem(block, new Item.Settings().group(DOODADS_GROUP)), BLOCKS.get(block));
		}
		return block;
	}

	public static void init () {
		BLOCKS.keySet().forEach(block -> Registry.register(Registry.BLOCK, BLOCKS.get(block), block));
		ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));
	}
}
