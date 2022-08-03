package org.cloudwarp.doodads.item;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.minecraft.block.*;
import net.minecraft.block.enums.BedPart;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.cloudwarp.doodads.registry.DTagKeys;
import org.cloudwarp.doodads.utils.DoodadsItemTypes;
import org.cloudwarp.doodads.utils.PaintbrushColors;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class PaintbrushItem extends Item {
	public DoodadsItemTypes doodadsItemType;


	public PaintbrushItem (Settings settings, DoodadsItemTypes doodadsItemType) {
		super(settings);
		this.doodadsItemType = doodadsItemType;
	}


	@Override
	public void appendTooltip (ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		if (Screen.hasShiftDown()) {
			tooltip.add(Text.translatable("item.doodads." + doodadsItemType.name + ".tooltip.shift"));
			tooltip.add(Text.translatable("item.doodads." + doodadsItemType.name + ".tooltip2.shift"));
			tooltip.add(Text.translatable("item.doodads." + doodadsItemType.name + ".tooltip3.shift"));
		} else {
			tooltip.add(Text.translatable("item.doodads.generic_tooltip"));
		}
	}

	public ActionResult useOnBlock (ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		BlockState state = world.getBlockState(pos);
		PlayerEntity player = context.getPlayer();
		ItemStack itemStack = context.getStack();
		PaintbrushColors color;
		color = PaintbrushColors.getFromDye(player.getMainHandStack().getItem());
		boolean success = false;
		if (state.isIn(BlockTags.WOOL)) {
			if (world.isClient) {
				playSound(world, player, pos);
				return ActionResult.SUCCESS;
			}
			world.setBlockState(pos, color.wool.getDefaultState());
			success = true;
		} else if (state.isIn(BlockTags.BANNERS)) {
			if (world.isClient) {
				playSound(world, player, pos);
				return ActionResult.SUCCESS;
			}
			if (state.getBlock() instanceof WallBannerBlock) {
				world.setBlockState(pos, color.wallBanner.getDefaultState().with(WallBannerBlock.FACING, state.get(WallBannerBlock.FACING)));
			} else {
				world.setBlockState(pos, color.banner.getDefaultState().with(BannerBlock.ROTATION, state.get(BannerBlock.ROTATION)));
			}
			success = true;
		} else if (state.isIn(BlockTags.WOOL_CARPETS)) {
			if (world.isClient) {
				playSound(world, player, pos);
				return ActionResult.SUCCESS;
			}
			world.setBlockState(pos, color.carpet.getDefaultState());
			success = true;
		} else if (state.isIn(DTagKeys.CONCRETES)) {
			if (world.isClient) {
				playSound(world, player, pos);
				return ActionResult.SUCCESS;
			}
			world.setBlockState(pos, color.concrete.getDefaultState());
			success = true;
		} else if (state.isIn(BlockTags.TERRACOTTA)) {
			if (world.isClient) {
				playSound(world, player, pos);
				return ActionResult.SUCCESS;
			}
			world.setBlockState(pos, color.terracotta.getDefaultState());
			success = true;
		} else if (state.isIn(ConventionalBlockTags.GLASS_BLOCKS)) {
			if (world.isClient) {
				playSound(world, player, pos);
				return ActionResult.SUCCESS;
			}
			world.setBlockState(pos, color.stainedGlass.getDefaultState());
			success = true;
		} else if (state.isIn(ConventionalBlockTags.GLASS_PANES)) {
			if (world.isClient) {
				playSound(world, player, pos);
				return ActionResult.SUCCESS;
			}
			world.setBlockState(pos, color.stainedGlassPane.getDefaultState()
					.with(HorizontalConnectingBlock.NORTH, state.get(HorizontalConnectingBlock.NORTH))
					.with(HorizontalConnectingBlock.SOUTH, state.get(HorizontalConnectingBlock.SOUTH))
					.with(HorizontalConnectingBlock.EAST, state.get(HorizontalConnectingBlock.EAST))
					.with(HorizontalConnectingBlock.WEST, state.get(HorizontalConnectingBlock.WEST))
					.with(HorizontalConnectingBlock.WATERLOGGED, state.get(HorizontalConnectingBlock.WATERLOGGED)));
			success = true;
		} else if (state.isIn(DTagKeys.CONCRETE_POWDER)) {
			if (world.isClient) {
				playSound(world, player, pos);
				return ActionResult.SUCCESS;
			}
			world.setBlockState(pos, color.concretePowder.getDefaultState());
			success = true;
		} else if (state.isIn(DTagKeys.GLAZED_TERRACOTTA)) {
			if (world.isClient) {
				playSound(world, player, pos);
				return ActionResult.SUCCESS;
			}
			world.setBlockState(pos, color.glazedTerracotta.getDefaultState().with(HorizontalFacingBlock.FACING, state.get(HorizontalFacingBlock.FACING)));
			success = true;
		} else if (state.isIn(BlockTags.SHULKER_BOXES)) {
			if (world.isClient) {
				playSound(world, player, pos);
				return ActionResult.SUCCESS;
			}
			BlockState newShulker = color.shulkerBox.getDefaultState().with(ShulkerBoxBlock.FACING, state.get(ShulkerBoxBlock.FACING));
			NbtCompound nbt = world.getBlockEntity(pos).createNbtWithIdentifyingData();
			world.setBlockState(pos, newShulker);
			world.getBlockEntity(pos).readNbt(nbt);
			success = true;
		} else if (state.isIn(BlockTags.BEDS)) {
			if (world.isClient) {
				playSound(world, player, pos);
				return ActionResult.SUCCESS;
			}
			BlockPos opposite = pos.offset(BedBlock.getOppositePartDirection(state));
			BlockState oppositeState = world.getBlockState(opposite);
			BlockState bed = color.bed.getDefaultState().with(BedBlock.FACING, state.get(BedBlock.FACING)).with(BedBlock.PART, state.get(BedBlock.PART)).with(BedBlock.OCCUPIED, state.get(BedBlock.OCCUPIED));
			BlockState bedOpposite = color.bed.getDefaultState().with(BedBlock.FACING, oppositeState.get(BedBlock.FACING)).with(BedBlock.PART, oppositeState.get(BedBlock.PART)).with(BedBlock.OCCUPIED, oppositeState.get(BedBlock.OCCUPIED));
			if (BedBlock.getBedPart(state).equals(DoubleBlockProperties.Type.SECOND)) {
				world.setBlockState(opposite, Blocks.AIR.getDefaultState());
				world.setBlockState(pos, Blocks.AIR.getDefaultState());
				world.setBlockState(opposite, bedOpposite);
				world.setBlockState(pos, bed);
			} else {
				world.setBlockState(pos, Blocks.AIR.getDefaultState());
				world.setBlockState(opposite, Blocks.AIR.getDefaultState());
				world.setBlockState(pos, bed);
				world.setBlockState(opposite, bedOpposite);
			}
			success = true;
		}
		if(success){
			itemStack.damage(1, player, p -> p.sendToolBreakStatus(context.getHand()));
			return ActionResult.SUCCESS;
		}
		return ActionResult.PASS;
	}

	public void playSound (World world, PlayerEntity player, BlockPos pos) {
		world.playSound(player, pos, SoundEvents.BLOCK_HONEY_BLOCK_BREAK, SoundCategory.BLOCKS, 0.65f, 0.8f);
	}
}

