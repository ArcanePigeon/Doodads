package org.cloudwarp.doodads.item;

import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.cloudwarp.doodads.block.GlareLight;
import org.cloudwarp.doodads.registry.DBlocks;
import org.cloudwarp.doodads.registry.DTagKeys;
import org.cloudwarp.doodads.utils.DoodadsItemTypes;
import org.cloudwarp.doodads.utils.PaintbrushColors;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GlareStaffItem extends Item {
	public DoodadsItemTypes doodadsItemType;


	public GlareStaffItem (Settings settings, DoodadsItemTypes doodadsItemType) {
		super(settings);
		this.doodadsItemType = doodadsItemType;
	}


	@Override
	public void appendTooltip (ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
			tooltip.add(new TranslatableText("item.doodads." + doodadsItemType.name + ".tooltip"));
	}

	public ActionResult useOnBlock (ItemUsageContext context) {
		return this.place(new ItemPlacementContext(context));
	}
	public ActionResult place(ItemPlacementContext context) {
		if (!context.canPlace()) {
			return ActionResult.FAIL;
		}
		ItemPlacementContext itemPlacementContext = this.getPlacementContext(context);
		if (itemPlacementContext == null) {
			return ActionResult.FAIL;
		}
		BlockState blockState = this.getPlacementState(itemPlacementContext);
		if (blockState == null) {
			return ActionResult.FAIL;
		}
		if (!this.place(itemPlacementContext, blockState)) {
			return ActionResult.FAIL;
		}
		BlockPos blockPos = itemPlacementContext.getBlockPos();
		World world = itemPlacementContext.getWorld();
		PlayerEntity playerEntity = itemPlacementContext.getPlayer();
		ItemStack itemStack = itemPlacementContext.getStack();
		BlockState blockState2 = world.getBlockState(blockPos);
		if (blockState2.isOf(blockState.getBlock())) {
			blockState2.getBlock().onPlaced(world, blockPos, blockState2, playerEntity, itemStack);
			if (playerEntity instanceof ServerPlayerEntity) {
				Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)playerEntity, blockPos, itemStack);
			}
		}
		BlockSoundGroup blockSoundGroup = blockState2.getSoundGroup();
		world.playSound(playerEntity, blockPos, this.getPlaceSound(blockState2), SoundCategory.BLOCKS, (blockSoundGroup.getVolume() + 1.0f) / 2.0f, blockSoundGroup.getPitch() * 0.8f);
		world.emitGameEvent(playerEntity, GameEvent.BLOCK_PLACE, blockPos);
		return ActionResult.success(world.isClient);
	}
	protected boolean place(ItemPlacementContext context, BlockState state) {
		return context.getWorld().setBlockState(context.getBlockPos(), state, Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
	}
	@Nullable
	public ItemPlacementContext getPlacementContext(ItemPlacementContext context) {
		return context;
	}
	@Nullable
	protected BlockState getPlacementState(ItemPlacementContext context) {
		BlockState blockState = DBlocks.GLARE_LIGHT.getDefaultState();
		return blockState != null && this.canPlace(context, blockState) ? blockState : null;
	}

	protected boolean canPlace(ItemPlacementContext context, BlockState state) {
		PlayerEntity playerEntity = context.getPlayer();
		ShapeContext shapeContext = playerEntity == null ? ShapeContext.absent() : ShapeContext.of(playerEntity);
		return (state.canPlaceAt(context.getWorld(), context.getBlockPos())) && context.getWorld().canPlace(state, context.getBlockPos(), shapeContext);
	}
	protected SoundEvent getPlaceSound(BlockState state) {
		return state.getSoundGroup().getPlaceSound();
	}
	public void playSound (World world, PlayerEntity player, BlockPos pos) {
		world.playSound(player, pos, SoundEvents.BLOCK_HONEY_BLOCK_BREAK, SoundCategory.BLOCKS, 0.65f, 0.8f);
	}
}

