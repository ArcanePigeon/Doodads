package org.cloudwarp.doodads.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;

import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class GlareLight extends Block {
	protected static final VoxelShape SHAPE = Block.createCuboidShape(6.0D, 6.0D, 6.0D, 10.0D, 10.0D, 10.0D);
	public GlareLight (Settings settings) {
		super(settings);
	}


	@Override
	@SuppressWarnings("deprecation")
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.INVISIBLE;
	}

	@Override
	public VoxelShape getOutlineShape (BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}

	@Override
	public boolean isTranslucent (BlockState state, BlockView world, BlockPos pos) {
		return true;
	}

	@Override
	public void randomDisplayTick (BlockState state, World world, BlockPos pos, Random random) {
		double d0 = (double) pos.getX() + 0.5D;
		double d1 = (double) pos.getY() + 0.5D;
		double d2 = (double) pos.getZ() + 0.5D;
		world.addParticle(ParticleTypes.FLAME,d0,d1,d2,0d,0d,0d);
	}
}
