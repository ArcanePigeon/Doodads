package org.cloudwarp.doodads.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;

public class PortableNetherBlock extends Block {
	public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;
	public PortableNetherBlock (Settings settings) {
		super(settings);
		this.setDefaultState((BlockState)this.getDefaultState().with(LIT, false));
	}

	@Override
	public BlockState getStateForNeighborUpdate (BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (direction == Direction.UP && neighborState.isOf(Blocks.WET_SPONGE)) {
			world.setBlockState(neighborPos, Blocks.SPONGE.getDefaultState(), Block.NOTIFY_ALL);
			world.syncWorldEvent(WorldEvents.WET_SPONGE_DRIES_OUT, neighborPos, 0);
			world.playSound(null, neighborPos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0f, (1.0f + world.getRandom().nextFloat() * 0.2f) * 0.7f);
		}else if(direction == Direction.UP && neighborState.isOf(Blocks.WATER)){
			for (int l = 0; l < 8; ++l) {
				world.addParticle(ParticleTypes.LARGE_SMOKE, (double)neighborPos.getX() + Math.random(), (double)neighborPos.getY() + Math.random(), (double)neighborPos.getZ() + Math.random(), 0.0, 0.0, 0.0);
			}
			world.setBlockState(neighborPos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);
			world.playSound(null, neighborPos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5f, 2.6f + (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.8f);
		}
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
		if (world.isClient) {
			return;
		}
		boolean bl = state.get(LIT);
		if (bl != world.isReceivingRedstonePower(pos)) {
			if (bl) {
				world.createAndScheduleBlockTick(pos, this, 4);
			} else {
				world.setBlockState(pos, (BlockState)state.cycle(LIT), Block.NOTIFY_LISTENERS);
			}
		}
	}

	@Override
	public void onPlaced (World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		BlockPos upPos = pos.up();
		if(world.getBlockState(upPos).isOf(Blocks.WATER)){
			for (int l = 0; l < 8; ++l) {
				world.addParticle(ParticleTypes.LARGE_SMOKE, (double)upPos.getX() + Math.random(), (double)upPos.getY() + Math.random(), (double)upPos.getZ() + Math.random(), 0.0, 0.0, 0.0);
			}
			world.setBlockState(upPos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);
			world.playSound(null, upPos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5f, 2.6f + (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.8f);
		}
		super.onPlaced(world, pos, state, placer, itemStack);
	}

	@Override
	public void randomDisplayTick (BlockState state, World world, BlockPos pos, Random random) {
		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		for (int l = 0; world.isReceivingRedstonePower(pos) && l < 164; ++l) {
			mutable.set(i + MathHelper.nextInt(random, -10, 10), j + random.nextInt(10), k + MathHelper.nextInt(random, -10, 10));
			BlockState blockState = world.getBlockState(mutable);
			if (blockState.isFullCube(world, mutable)) continue;
			world.addParticle(ParticleTypes.WHITE_ASH, (double)mutable.getX() + random.nextDouble(), (double)mutable.getY() + random.nextDouble(), (double)mutable.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
		}
		super.randomDisplayTick(state, world, pos, random);
	}
	@Override
	@Nullable
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return (BlockState)this.getDefaultState().with(LIT, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos()));
	}
	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (state.get(LIT) && !world.isReceivingRedstonePower(pos)) {
			world.setBlockState(pos, (BlockState)state.cycle(LIT), Block.NOTIFY_LISTENERS);
		}
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(LIT);
	}
}
