package org.cloudwarp.doodads.mixin;

import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.cloudwarp.doodads.registry.DBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LavaFluid.class)
public abstract class LavaFluidMixin extends FlowableFluid {

	@Redirect(method = "getNextTickDelay", at = @At(value = "INVOKE",target = "Lnet/minecraft/fluid/LavaFluid;getTickRate(Lnet/minecraft/world/WorldView;)I", ordinal = 0))
	public int portableNetherTickModification(LavaFluid instance, WorldView world, World world2, BlockPos pos, FluidState oldState, FluidState newState){
		if(world.getBlockState(pos.down()).isOf(DBlocks.PORTALBE_NETHER)){
			return 4;
		}
		return this.getTickRate(world);
	}
}
