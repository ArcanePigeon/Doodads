package org.cloudwarp.doodads.blockdetails;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.cloudwarp.doodads.entities.MSEntityTypes;
import org.cloudwarp.doodads.entities.SmallPlushieEntity;
import org.cloudwarp.doodads.registry.MSSounds;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class DoodadsItem extends Item {
	public MSEntityTypes scarecrowType;

	public DoodadsItem (Item.Settings settings, MSEntityTypes scarecrowType) {
		super(settings);
		this.scarecrowType = scarecrowType;
	}

	@Override
	public ActionResult useOnBlock (ItemUsageContext context) {
		Direction direction = context.getSide();
		if (direction == Direction.DOWN) {
			return ActionResult.FAIL;
		}
		World world = context.getWorld();
		ItemPlacementContext itemPlacementContext = new ItemPlacementContext(context);
		BlockPos blockPos = itemPlacementContext.getBlockPos();
		ItemStack itemStack = context.getStack();
		Vec3d vec3d = Vec3d.ofBottomCenter(blockPos);
		Box box = scarecrowType.getEntityType().getDimensions().getBoxAt(vec3d.getX(), vec3d.getY(), vec3d.getZ());
		if (! world.isSpaceEmpty(null, box) || ! world.getOtherEntities(null, box).isEmpty()) {
			return ActionResult.FAIL;
		}
		if (world instanceof ServerWorld) {
			ServerWorld serverWorld = (ServerWorld) world;
			SmallPlushieEntity scarecrowEntity = scarecrowType.getEntityType().create(serverWorld, itemStack.getNbt(), null, context.getPlayer(), blockPos, SpawnReason.SPAWN_EGG, true, true);
			if (scarecrowEntity == null) {
				return ActionResult.FAIL;
			}
			float f = (float) MathHelper.floor((MathHelper.wrapDegrees(context.getPlayerYaw() - 180.0f) + 22.5f) / 45.0f) * 45.0f;
			scarecrowEntity.refreshPositionAndAngles(scarecrowEntity.getX(), scarecrowEntity.getY(), scarecrowEntity.getZ(), f, 0.0f);
			//this.setRotations(scarecrowEntity, world.random);
			serverWorld.spawnEntityAndPassengers(scarecrowEntity);
			world.playSound(null, scarecrowEntity.getX(), scarecrowEntity.getY(), scarecrowEntity.getZ(), scarecrowType == MSEntityTypes.DEFAULT_SCARECROW ? SoundEvents.BLOCK_GRASS_PLACE : MSSounds.PLUSHIE_BREAK_EVENT, SoundCategory.BLOCKS, 0.75f, 0.8f);
			scarecrowEntity.emitGameEvent(GameEvent.ENTITY_PLACE, context.getPlayer());
		}
		itemStack.decrement(1);
		return ActionResult.success(world.isClient);
	}

	private void setRotations (SmallPlushieEntity stand, Random random) {
		EulerAngle eulerAngle = stand.getHeadRotation();
		//float f = random.nextFloat() * 5.0f;
		//float g = random.nextFloat() * 20.0f - 10.0f;
		//EulerAngle eulerAngle2 = new EulerAngle(eulerAngle.getPitch() + f, eulerAngle.getYaw() + g, eulerAngle.getRoll());
		EulerAngle eulerAngle2 = new EulerAngle(eulerAngle.getPitch(), eulerAngle.getYaw(), eulerAngle.getRoll());
		stand.setHeadRotation(eulerAngle2);
		eulerAngle = stand.getBodyRotation();
		//f = random.nextFloat() * 10.0f - 5.0f;
		//eulerAngle2 = new EulerAngle(eulerAngle.getPitch(), eulerAngle.getYaw() + f, eulerAngle.getRoll());
		eulerAngle2 = new EulerAngle(eulerAngle.getPitch(), eulerAngle.getYaw(), eulerAngle.getRoll());
		stand.setBodyRotation(eulerAngle2);
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		if(Screen.hasShiftDown()){
			tooltip.add(Text.translatable("item.mobscarecrow."+ scarecrowType.id +".tooltip.shift"));
		}else{
			tooltip.add(Text.translatable("item.mobscarecrow.generic_scarecrow.tooltip"));
		}
	}

}

