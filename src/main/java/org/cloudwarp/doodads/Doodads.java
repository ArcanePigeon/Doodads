package org.cloudwarp.doodads;

import dev.emi.trinkets.api.TrinketsApi;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cloudwarp.doodads.registry.*;
import org.cloudwarp.doodads.utils.DConfig;

import java.util.Optional;
import static org.cloudwarp.doodads.utils.DoodadsItemTypes.*;

public class Doodads implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "doodads";

	public static Identifier id (String path) {
		return new Identifier(MOD_ID, path);
	}
	public static ConfigHolder<DConfig> configHolder;

	@Override
	public void onInitialize () {
		AutoConfig.register(DConfig.class, Toml4jConfigSerializer::new);
		configHolder = AutoConfig.getConfigHolder(DConfig.class);
		DEntities.init();
		DParticles.init();
		DItems.registerItems();
		DBlocks.init();
		UseBlockCallback.EVENT.register((player,world,hand,hitResult) ->
		{

			BlockPos pos = hitResult.getBlockPos();
			BlockState state =  world.getBlockState(pos);
			Optional<BlockState> optional = this.getStrippedState(state);
			if(player.getMainHandStack().isEmpty() && TrinketsApi.getTrinketComponent(player).get().isEquipped(DItems.get(BEAVER_TEETH.name)) && optional.isPresent()){
				world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0f, 1.0f);
				if(player instanceof ServerPlayerEntity) {
					Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, pos, new ItemStack(DItems.get(BEAVER_TEETH.name)));
				}
				world.setBlockState(pos, (BlockState)optional.get(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
				world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, (BlockState)optional.get()));
				return ActionResult.SUCCESS;
			}
			return ActionResult.PASS;
		});
	}
	private Optional<BlockState> getStrippedState(BlockState state) {
		return Optional.ofNullable(AxeItem.STRIPPED_BLOCKS.get(state.getBlock())).map(block -> (BlockState)block.getDefaultState().with(PillarBlock.AXIS, state.get(PillarBlock.AXIS)));
	}
}
