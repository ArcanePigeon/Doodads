package org.cloudwarp.doodads.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RoadBlock extends Block {
	public RoadBlock (Settings settings) {
		super(settings);
	}

	@Override
	public void onSteppedOn (World world, BlockPos pos, BlockState state, Entity entity) {
		super.onSteppedOn(world, pos, state, entity);
		if(entity instanceof LivingEntity livingEntity){
			livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 30,0,false,false));
		}
	}
}
