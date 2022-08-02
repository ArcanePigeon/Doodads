package org.cloudwarp.doodads.mixin;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.shorts.Short2BooleanMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.cloudwarp.doodads.registry.DBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlowableFluid.class)
public abstract class FlowableFluidMixin extends Fluid {
	@Shadow protected abstract int getLevelDecreasePerBlock(WorldView var1);

	@Redirect(method = "method_15744", at = @At(value = "INVOKE",target = "Lnet/minecraft/fluid/FlowableFluid;getLevelDecreasePerBlock(Lnet/minecraft/world/WorldView;)I", ordinal = 0))
	public int portableNetherHeightModification(FlowableFluid instance, WorldView worldView, WorldAccess world, BlockPos pos, FluidState fluidState, BlockState blockState){
		if(world.getBlockState(pos.down()).isOf(DBlocks.PORTALBE_NETHER)){
			return 1;
		}
		return this.getLevelDecreasePerBlock(world);
	}
	@Redirect(method = "getUpdatedState", at = @At(value = "INVOKE",target = "Lnet/minecraft/fluid/FlowableFluid;getLevelDecreasePerBlock(Lnet/minecraft/world/WorldView;)I", ordinal = 0))
	public int portableNetherHeightModification2(FlowableFluid instance, WorldView worldView, WorldView world, BlockPos pos, BlockState state){
		if(world.getBlockState(pos.down()).isOf(DBlocks.PORTALBE_NETHER)){
			return 1;
		}
		return this.getLevelDecreasePerBlock(world);
	}
}
