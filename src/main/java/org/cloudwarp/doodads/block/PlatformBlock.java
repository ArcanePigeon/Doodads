package org.cloudwarp.doodads.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.cloudwarp.doodads.registry.DBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Random;

public class PlatformBlock extends Block
		implements Waterloggable {
	private static final int field_31238 = 1;
	private static final VoxelShape NORMAL_OUTLINE_SHAPE;
	private static final VoxelShape BOTTOM_OUTLINE_SHAPE;
	private static final VoxelShape COLLISION_SHAPE;
	private static final VoxelShape OUTLINE_SHAPE;
	public static final int MAX_DISTANCE = 7;
	public static final IntProperty DISTANCE;
	public static final BooleanProperty WATERLOGGED;
	public static final BooleanProperty BOTTOM;

	public PlatformBlock(AbstractBlock.Settings settings) {
		super(settings);
		this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(DISTANCE, 7)).with(WATERLOGGED, false)).with(BOTTOM, false));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(DISTANCE, WATERLOGGED, BOTTOM);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		if (!context.isHolding(state.getBlock().asItem())) {
			return state.get(BOTTOM) != false ? BOTTOM_OUTLINE_SHAPE : NORMAL_OUTLINE_SHAPE;
		}
		return VoxelShapes.fullCube();
	}

	@Override
	public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
		return VoxelShapes.fullCube();
	}

	@Override
	public boolean canReplace(BlockState state, ItemPlacementContext context) {
		return context.getStack().isOf(this.asItem());
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockPos blockPos = ctx.getBlockPos();
		World world = ctx.getWorld();
		int i = PlatformBlock.calculateDistance(world, blockPos);
		return (BlockState)((BlockState)((BlockState)this.getDefaultState().with(WATERLOGGED, world.getFluidState(blockPos).getFluid() == Fluids.WATER)).with(DISTANCE, i)).with(BOTTOM, this.shouldBeBottom(world, blockPos, i));
	}

	@Override
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		if (!world.isClient) {
			world.createAndScheduleBlockTick(pos, this, 1);
		}
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(WATERLOGGED).booleanValue()) {
			world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}
		if (!world.isClient()) {
			world.createAndScheduleBlockTick(pos, this, 1);
		}
		return state;
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		int i = PlatformBlock.calculateDistance(world, pos);
		BlockState blockState = (BlockState)((BlockState)state.with(DISTANCE, i)).with(BOTTOM, this.shouldBeBottom(world, pos, i));
		if(state != blockState) {
			world.setBlockState(pos, blockState, Block.NOTIFY_ALL);
		}
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return PlatformBlock.calculateDistance(world, pos) < 7;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {

		if (!context.isAbove(VoxelShapes.fullCube(), pos, true) || context.isDescending()) {
			if (state.get(DISTANCE) != 0 && state.get(BOTTOM) && context.isAbove(OUTLINE_SHAPE, pos, true)) {
				return COLLISION_SHAPE;
			}
			return VoxelShapes.empty();
		}
		return NORMAL_OUTLINE_SHAPE;
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		if (state.get(WATERLOGGED)) {
			return Fluids.WATER.getStill(false);
		}
		return super.getFluidState(state);
	}

	private boolean shouldBeBottom(BlockView world, BlockPos pos, int distance) {
		return distance > 0 && !world.getBlockState(pos.down()).isOf(this);
	}

	public static int calculateDistance(BlockView world, BlockPos pos) {
		Direction direction;
		BlockState blockState2;
		BlockPos.Mutable mutable = pos.mutableCopy().move(Direction.DOWN);
		BlockState blockState = world.getBlockState(mutable);
		int i = 7;
		if (blockState.isOf(DBlocks.PLATFORM)) {
			i = blockState.get(DISTANCE);
		} else if (blockState.isSideSolidFullSquare(world, mutable, Direction.UP)) {
			return 0;
		}
		Iterator<Direction> iterator = Direction.Type.HORIZONTAL.iterator();
		while (iterator.hasNext() && (!(blockState2 = world.getBlockState(mutable.set((Vec3i)pos, direction = iterator.next()))).isOf(DBlocks.PLATFORM) || (i = Math.min(i, blockState2.get(DISTANCE) + 1)) != 1)) {
		}
		return i;
	}

	static {
		COLLISION_SHAPE = Block.createCuboidShape(0.0, 14.0, 0.0, 16.0, 16.0, 16.0);
		OUTLINE_SHAPE = VoxelShapes.fullCube().offset(0.0, -1.0, 0.0);
		DISTANCE = Properties.DISTANCE_0_7;
		WATERLOGGED = Properties.WATERLOGGED;
		BOTTOM = Properties.BOTTOM;
		NORMAL_OUTLINE_SHAPE = Block.createCuboidShape(0.0, 14.0, 0.0, 16.0, 16.0, 16.0);
		BOTTOM_OUTLINE_SHAPE = VoxelShapes.union(COLLISION_SHAPE, NORMAL_OUTLINE_SHAPE);
	}
}
