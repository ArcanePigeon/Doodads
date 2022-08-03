package org.cloudwarp.doodads.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.cloudwarp.doodads.Doodads;

public class DTagKeys {
	public static final TagKey<Block> CONCRETES = TagKey.of(Registry.BLOCK_KEY, Doodads.id("concretes"));
	public static final TagKey<Block> CONCRETE_POWDER = TagKey.of(Registry.BLOCK_KEY, Doodads.id("concrete_powders"));
	public static final TagKey<Block> GLAZED_TERRACOTTA = TagKey.of(Registry.BLOCK_KEY, Doodads.id("glazed_terracottas"));
	public static final TagKey<Item> DOODAD_BUNDLE_CRAFTABLE = TagKey.of(Registry.ITEM_KEY, Doodads.id("doodad_bundle_craftable"));

}
