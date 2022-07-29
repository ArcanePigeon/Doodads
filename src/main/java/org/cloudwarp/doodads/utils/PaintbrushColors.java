package org.cloudwarp.doodads.utils;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum PaintbrushColors {
	WHITE_DYE(Blocks.WHITE_WOOL, Blocks.WHITE_BANNER, Blocks.WHITE_CARPET,
			Blocks.WHITE_CONCRETE, Blocks.WHITE_CONCRETE_POWDER, Blocks.WHITE_TERRACOTTA,
			Blocks.WHITE_GLAZED_TERRACOTTA, Blocks.WHITE_STAINED_GLASS, Blocks.WHITE_STAINED_GLASS_PANE, Blocks.WHITE_WALL_BANNER, Blocks.WHITE_BED, Blocks.WHITE_SHULKER_BOX),
	ORANGE_DYE(Blocks.ORANGE_WOOL, Blocks.ORANGE_BANNER, Blocks.ORANGE_CARPET,
			Blocks.ORANGE_CONCRETE, Blocks.ORANGE_CONCRETE_POWDER, Blocks.ORANGE_TERRACOTTA,
			Blocks.ORANGE_GLAZED_TERRACOTTA, Blocks.ORANGE_STAINED_GLASS, Blocks.ORANGE_STAINED_GLASS_PANE, Blocks.ORANGE_WALL_BANNER, Blocks.ORANGE_BED, Blocks.ORANGE_SHULKER_BOX),
	MAGENTA_DYE(Blocks.MAGENTA_WOOL, Blocks.MAGENTA_BANNER, Blocks.MAGENTA_CARPET,
			Blocks.MAGENTA_CONCRETE, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.MAGENTA_TERRACOTTA,
			Blocks.MAGENTA_GLAZED_TERRACOTTA, Blocks.MAGENTA_STAINED_GLASS, Blocks.MAGENTA_STAINED_GLASS_PANE, Blocks.MAGENTA_WALL_BANNER, Blocks.MAGENTA_BED, Blocks.MAGENTA_SHULKER_BOX),
	LIGHT_BLUE_DYE(Blocks.LIGHT_BLUE_WOOL, Blocks.LIGHT_BLUE_BANNER, Blocks.LIGHT_BLUE_CARPET,
			Blocks.LIGHT_BLUE_CONCRETE, Blocks.LIGHT_BLUE_CONCRETE_POWDER, Blocks.LIGHT_BLUE_TERRACOTTA,
			Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, Blocks.LIGHT_BLUE_STAINED_GLASS, Blocks.LIGHT_BLUE_STAINED_GLASS_PANE, Blocks.LIGHT_BLUE_WALL_BANNER, Blocks.LIGHT_BLUE_BED, Blocks.LIGHT_BLUE_SHULKER_BOX),
	YELLOW_DYE(Blocks.YELLOW_WOOL, Blocks.YELLOW_BANNER, Blocks.YELLOW_CARPET,
			Blocks.YELLOW_CONCRETE, Blocks.YELLOW_CONCRETE_POWDER, Blocks.YELLOW_TERRACOTTA,
			Blocks.YELLOW_GLAZED_TERRACOTTA, Blocks.YELLOW_STAINED_GLASS, Blocks.YELLOW_STAINED_GLASS_PANE, Blocks.YELLOW_WALL_BANNER, Blocks.YELLOW_BED, Blocks.YELLOW_SHULKER_BOX),
	LIME_DYE(Blocks.LIME_WOOL, Blocks.LIME_BANNER, Blocks.LIME_CARPET,
			Blocks.LIME_CONCRETE, Blocks.LIME_CONCRETE_POWDER, Blocks.LIME_TERRACOTTA,
			Blocks.LIME_GLAZED_TERRACOTTA, Blocks.LIME_STAINED_GLASS, Blocks.LIME_STAINED_GLASS_PANE, Blocks.LIME_WALL_BANNER, Blocks.LIME_BED, Blocks.LIME_SHULKER_BOX),
	PINK_DYE(Blocks.PINK_WOOL, Blocks.PINK_BANNER, Blocks.PINK_CARPET,
			Blocks.PINK_CONCRETE, Blocks.PINK_CONCRETE_POWDER, Blocks.PINK_TERRACOTTA,
			Blocks.PINK_GLAZED_TERRACOTTA, Blocks.PINK_STAINED_GLASS, Blocks.PINK_STAINED_GLASS_PANE, Blocks.PINK_WALL_BANNER, Blocks.PINK_BED, Blocks.PINK_SHULKER_BOX),
	GRAY_DYE(Blocks.GRAY_WOOL, Blocks.GRAY_BANNER, Blocks.GRAY_CARPET,
			Blocks.GRAY_CONCRETE, Blocks.GRAY_CONCRETE_POWDER, Blocks.GRAY_TERRACOTTA,
			Blocks.GRAY_GLAZED_TERRACOTTA, Blocks.GRAY_STAINED_GLASS, Blocks.GRAY_STAINED_GLASS_PANE, Blocks.GRAY_WALL_BANNER, Blocks.GRAY_BED, Blocks.GRAY_SHULKER_BOX),
	LIGHT_GRAY_DYE(Blocks.LIGHT_GRAY_WOOL, Blocks.LIGHT_GRAY_BANNER, Blocks.LIGHT_GRAY_CARPET,
			Blocks.LIGHT_GRAY_CONCRETE, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.LIGHT_GRAY_TERRACOTTA,
			Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA, Blocks.LIGHT_GRAY_STAINED_GLASS, Blocks.LIGHT_GRAY_STAINED_GLASS_PANE, Blocks.LIGHT_GRAY_WALL_BANNER, Blocks.LIGHT_GRAY_BED, Blocks.LIGHT_GRAY_SHULKER_BOX),
	CYAN_DYE(Blocks.CYAN_WOOL, Blocks.CYAN_BANNER, Blocks.CYAN_CARPET,
			Blocks.CYAN_CONCRETE, Blocks.CYAN_CONCRETE_POWDER, Blocks.CYAN_TERRACOTTA,
			Blocks.CYAN_GLAZED_TERRACOTTA, Blocks.CYAN_STAINED_GLASS, Blocks.CYAN_STAINED_GLASS_PANE, Blocks.CYAN_WALL_BANNER, Blocks.CYAN_BED, Blocks.CYAN_SHULKER_BOX),
	PURPLE_DYE(Blocks.PURPLE_WOOL, Blocks.PURPLE_BANNER, Blocks.PURPLE_CARPET,
			Blocks.PURPLE_CONCRETE, Blocks.PURPLE_CONCRETE_POWDER, Blocks.PURPLE_TERRACOTTA,
			Blocks.PURPLE_GLAZED_TERRACOTTA, Blocks.PURPLE_STAINED_GLASS, Blocks.PURPLE_STAINED_GLASS_PANE, Blocks.PURPLE_WALL_BANNER, Blocks.PURPLE_BED, Blocks.PURPLE_SHULKER_BOX),
	BLUE_DYE(Blocks.BLUE_WOOL, Blocks.BLUE_BANNER, Blocks.BLUE_CARPET,
			Blocks.BLUE_CONCRETE, Blocks.BLUE_CONCRETE_POWDER, Blocks.BLUE_TERRACOTTA,
			Blocks.BLUE_GLAZED_TERRACOTTA, Blocks.BLUE_STAINED_GLASS, Blocks.BLUE_STAINED_GLASS_PANE, Blocks.BLUE_WALL_BANNER, Blocks.BLUE_BED, Blocks.BLUE_SHULKER_BOX),
	BROWN_DYE(Blocks.BROWN_WOOL, Blocks.BROWN_BANNER, Blocks.BROWN_CARPET,
			Blocks.BROWN_CONCRETE, Blocks.BROWN_CONCRETE_POWDER, Blocks.BROWN_TERRACOTTA,
			Blocks.BROWN_GLAZED_TERRACOTTA, Blocks.BROWN_STAINED_GLASS, Blocks.BROWN_STAINED_GLASS_PANE, Blocks.BROWN_WALL_BANNER, Blocks.BROWN_BED, Blocks.BROWN_SHULKER_BOX),
	GREEN_DYE(Blocks.GREEN_WOOL, Blocks.GREEN_BANNER, Blocks.GREEN_CARPET,
			Blocks.GREEN_CONCRETE, Blocks.GREEN_CONCRETE_POWDER, Blocks.GREEN_TERRACOTTA,
			Blocks.GREEN_GLAZED_TERRACOTTA, Blocks.GREEN_STAINED_GLASS, Blocks.GREEN_STAINED_GLASS_PANE, Blocks.GREEN_WALL_BANNER, Blocks.GREEN_BED, Blocks.GREEN_SHULKER_BOX),
	RED_DYE(Blocks.RED_WOOL, Blocks.RED_BANNER, Blocks.RED_CARPET,
			Blocks.RED_CONCRETE, Blocks.RED_CONCRETE_POWDER, Blocks.RED_TERRACOTTA,
			Blocks.RED_GLAZED_TERRACOTTA, Blocks.RED_STAINED_GLASS, Blocks.RED_STAINED_GLASS_PANE, Blocks.RED_WALL_BANNER, Blocks.RED_BED, Blocks.RED_SHULKER_BOX),
	BLACK_DYE(Blocks.BLACK_WOOL, Blocks.BLACK_BANNER, Blocks.BLACK_CARPET,
			Blocks.BLACK_CONCRETE, Blocks.BLACK_CONCRETE_POWDER, Blocks.BLACK_TERRACOTTA,
			Blocks.BLACK_GLAZED_TERRACOTTA, Blocks.BLACK_STAINED_GLASS, Blocks.BLACK_STAINED_GLASS_PANE, Blocks.BLACK_WALL_BANNER, Blocks.BLACK_BED, Blocks.BLACK_SHULKER_BOX),
	CLEAN(Blocks.WHITE_WOOL, Blocks.WHITE_BANNER, Blocks.WHITE_CARPET,
			Blocks.WHITE_CONCRETE, Blocks.WHITE_CONCRETE_POWDER, Blocks.TERRACOTTA,
			Blocks.WHITE_GLAZED_TERRACOTTA, Blocks.GLASS, Blocks.GLASS_PANE, Blocks.WHITE_WALL_BANNER, Blocks.WHITE_BED, Blocks.SHULKER_BOX);
	public final Block wool;
	public final Block banner;
	public final Block carpet;
	public final Block concrete;
	public final Block concretePowder;
	public final Block terracotta;
	public final Block glazedTerracotta;
	public final Block stainedGlass;
	public final Block stainedGlassPane;
	public final Block wallBanner;
	public final Block bed;
	public final Block shulkerBox;

	public static final Random random = new Random();
	public static final PaintbrushColors[] colors = PaintbrushColors.values();


	PaintbrushColors (Block wool,
					  Block banner,
					  Block carpet,
					  Block concrete,
					  Block concretePowder,
					  Block terracotta,
					  Block glazedTerracotta,
					  Block stainedGlass,
					  Block stainedGlassPane,
					  Block wallBanner,
					  Block bed,
					  Block shulkerBox) {
		this.wool = wool;
		this.banner = banner;
		this.carpet = carpet;
		this.concrete = concrete;
		this.concretePowder = concretePowder;
		this.terracotta = terracotta;
		this.glazedTerracotta = glazedTerracotta;
		this.stainedGlass = stainedGlass;
		this.stainedGlassPane = stainedGlassPane;
		this.wallBanner = wallBanner;
		this.bed = bed;
		this.shulkerBox = shulkerBox;
	}

	public static PaintbrushColors getFromDye (Item dye) {
		if (dye.equals(Items.WHITE_DYE)) {
			return WHITE_DYE;
		} else if (dye.equals(Items.ORANGE_DYE)) {
			return ORANGE_DYE;
		} else if (dye.equals(Items.MAGENTA_DYE)) {
			return MAGENTA_DYE;
		} else if (dye.equals(Items.LIGHT_BLUE_DYE)) {
			return LIGHT_BLUE_DYE;
		} else if (dye.equals(Items.YELLOW_DYE)) {
			return YELLOW_DYE;
		} else if (dye.equals(Items.LIME_DYE)) {
			return LIME_DYE;
		} else if (dye.equals(Items.PINK_DYE)) {
			return PINK_DYE;
		} else if (dye.equals(Items.GRAY_DYE)) {
			return GRAY_DYE;
		} else if (dye.equals(Items.LIGHT_GRAY_DYE)) {
			return LIGHT_GRAY_DYE;
		} else if (dye.equals(Items.CYAN_DYE)) {
			return CYAN_DYE;
		} else if (dye.equals(Items.PURPLE_DYE)) {
			return PURPLE_DYE;
		} else if (dye.equals(Items.BLUE_DYE)) {
			return BLUE_DYE;
		} else if (dye.equals(Items.BROWN_DYE)) {
			return BROWN_DYE;
		} else if (dye.equals(Items.GREEN_DYE)) {
			return GREEN_DYE;
		} else if (dye.equals(Items.RED_DYE)) {
			return RED_DYE;
		} else if (dye.equals(Items.BLACK_DYE)) {
			return BLACK_DYE;
		} else if (dye.equals(Items.WATER_BUCKET)) {
			return CLEAN;
		}
		return colors[random.nextInt(16)];
	}
}
