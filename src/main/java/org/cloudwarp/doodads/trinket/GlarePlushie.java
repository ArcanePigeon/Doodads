package org.cloudwarp.doodads.trinket;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.cloudwarp.doodads.registry.DBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static org.cloudwarp.doodads.utils.DoodadsItemTypes.GLARE_PLUSHIE;
import static org.cloudwarp.doodads.utils.DoodadsItemTypes.MIDNIGHTS_EYE;

public class GlarePlushie extends TrinketItem {
	public GlarePlushie (Settings settings) {
		super(settings);
	}

	@Override
	public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
		World world = entity.getWorld();
		BlockPos pos = entity.getBlockPos();
		BlockState downState = world.getBlockState(pos.down());

		if (entity instanceof PlayerEntity && !entity.world.isClient && entity.age % 20 == 0 &&
				!downState.isAir() && entity.world.getLightLevel(LightType.BLOCK,pos) == 0 && entity.getRandom().nextFloat() < 0.7f){

			BlockPos checkPos = pos.up(2);
			checkPos = checkPos.offset(Direction.Axis.X, entity.getRandom().nextInt(2) - 1);
			checkPos = checkPos.offset(Direction.Axis.Y, entity.getRandom().nextInt(2) - 1);
			checkPos = checkPos.offset(Direction.Axis.Z, entity.getRandom().nextInt(2) - 1);
			BlockState state = world.getBlockState(checkPos);

			for(int i = 0; i < 3; i++){
				if(state.isAir()){
					world.setBlockState(checkPos, DBlocks.GLARE_LIGHT.getDefaultState());
					world.playSound(null,checkPos,SoundEvents.BLOCK_CAVE_VINES_PLACE,SoundCategory.BLOCKS,1f,1f);
					break;
				}
				checkPos = checkPos.down();
				state = world.getBlockState(checkPos);
			}
		}
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(new TranslatableText("item.doodads."+ GLARE_PLUSHIE.name +".tooltip"));
	}
}
