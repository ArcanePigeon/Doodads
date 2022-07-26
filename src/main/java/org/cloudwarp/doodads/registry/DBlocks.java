package org.cloudwarp.doodads.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.cloudwarp.doodads.Doodads;
import org.cloudwarp.doodads.block.PortableNetherBlock;
import org.cloudwarp.doodads.block.RoadBlock;
import org.cloudwarp.doodads.block.GlareLight;
import org.cloudwarp.doodads.block.PlatformBlock;
import org.cloudwarp.doodads.item.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.cloudwarp.doodads.registry.DItems.DOODADS_GROUP;

public class DBlocks {
	public static final Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
	public static final HashSet<Identifier> ENABLED_BLOCKS = new HashSet<>();
	public static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

	public static final Block GLARE_LIGHT      = create(true,"glare_light", new GlareLight(FabricBlockSettings.of(Material.LEAVES)
			.hardness(0f)
			.resistance(0f)
			.sounds(BlockSoundGroup.CAVE_VINES).breakInstantly().noCollision().nonOpaque().luminance(15)),false);
	public static final Block PLATFORM         = create(Doodads.loadedConfig.doodadBlocks.enablePlatform,"platform", new PlatformBlock(FabricBlockSettings.of(Material.DECORATION)
			.noCollision()
			.dynamicBounds()
			.sounds(BlockSoundGroup.WOOD)),false);
	public static final Block ASPHALT          = create(Doodads.loadedConfig.doodadBlocks.enableAsphalt,"asphalt", new RoadBlock(FabricBlockSettings.of(Material.STONE)
			.hardness(3f)
			.resistance(6f)
			.sounds(BlockSoundGroup.STONE).requiresTool()),true);
	public static final Block STONE_BRICK_ROAD          = create(Doodads.loadedConfig.doodadBlocks.enableStoneBrickRoad,"stone_brick_road", new RoadBlock(FabricBlockSettings.of(Material.STONE)
			.hardness(3f)
			.resistance(6f)
			.sounds(BlockSoundGroup.STONE).requiresTool()),true);
	public static final Block BRICK_ROAD          = create(Doodads.loadedConfig.doodadBlocks.enableBrickRoad,"brick_road", new RoadBlock(FabricBlockSettings.of(Material.STONE)
			.hardness(3f)
			.resistance(6f)
			.sounds(BlockSoundGroup.STONE).requiresTool()),true);
	public static final Block YELLOW_BRICK_ROAD          = create(Doodads.loadedConfig.doodadBlocks.enableYellowBrickRoad,"yellow_brick_road", new RoadBlock(FabricBlockSettings.of(Material.STONE)
			.hardness(3f)
			.resistance(6f)
			.sounds(BlockSoundGroup.STONE).requiresTool()),true);
	public static final Block PORTALBE_NETHER          = create(Doodads.loadedConfig.doodadBlocks.enablePortableNether,"portable_nether", new PortableNetherBlock(FabricBlockSettings.of(Material.STONE)
			.hardness(3f)
			.resistance(6f)
			.sounds(BlockSoundGroup.BASALT).requiresTool()),true);


	public static BlockItem PLATFORM_ITEM;

	private static <T extends Block> T create (boolean configEntry, String name, T block, boolean createItem) {
		if(configEntry){
			ENABLED_BLOCKS.add(Doodads.id(name));
		}
		BLOCKS.put(block, Doodads.id(name));
		if (createItem) {
			ITEMS.put(new BlockItem(block, new Item.Settings().group(DOODADS_GROUP)), BLOCKS.get(block));
		}
		return block;
	}

	public static void init () {
		BLOCKS.keySet().forEach(block -> Registry.register(Registry.BLOCK, BLOCKS.get(block), block));
		ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));
		PLATFORM_ITEM = new PlatformItem(PLATFORM, new Item.Settings().group(DOODADS_GROUP));
		Registry.register(Registry.ITEM, Doodads.id("platform"), PLATFORM_ITEM);
		ITEMS.put(PLATFORM_ITEM, BLOCKS.get(PLATFORM));
	}
}
