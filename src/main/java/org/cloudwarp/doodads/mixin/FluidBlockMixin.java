package org.cloudwarp.doodads.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.cloudwarp.doodads.registry.DBlocks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidBlock.class)
public abstract class FluidBlockMixin extends Block {
	public FluidBlockMixin (Settings settings) {
		super(settings);
	}
	@Shadow
	private boolean receiveNeighborFluids(World world, BlockPos pos, BlockState state) {return false;}

	@Shadow @Final protected FlowableFluid fluid;

	@Inject(method = "onBlockAdded", at = @At(value = "HEAD"), cancellable = true)
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
		if ((this.fluid.matchesType(Fluids.LAVA) || this.fluid.matchesType(Fluids.FLOWING_LAVA)) && world.getBlockState(pos.down()).isOf(DBlocks.PORTALBE_NETHER) && this.receiveNeighborFluids(world, pos, state)) {
			world.createAndScheduleFluidTick(pos, state.getFluidState().getFluid(), 10);
			ci.cancel();
		}

	}

	@Inject(method = "getStateForNeighborUpdate", at = @At(value = "HEAD"), cancellable = true)
	public void getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> cir) {
		if ((this.fluid.matchesType(Fluids.LAVA) || this.fluid.matchesType(Fluids.FLOWING_LAVA)) && world.getBlockState(pos.down()).isOf(DBlocks.PORTALBE_NETHER) && state.getFluidState().isStill() || neighborState.getFluidState().isStill()) {
			world.createAndScheduleFluidTick(pos, state.getFluidState().getFluid(), 10);
			cir.setReturnValue(super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos));
			cir.cancel();
		}
	}

	@Inject(method = "neighborUpdate", at = @At(value = "HEAD"), cancellable = true)
	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify, CallbackInfo ci) {
		if ((this.fluid.matchesType(Fluids.LAVA) || this.fluid.matchesType(Fluids.FLOWING_LAVA)) && world.getBlockState(pos.down()).isOf(DBlocks.PORTALBE_NETHER) &&this.receiveNeighborFluids(world, pos, state)) {
			world.createAndScheduleFluidTick(pos, state.getFluidState().getFluid(), 10);
			ci.cancel();
		}
	}
}
