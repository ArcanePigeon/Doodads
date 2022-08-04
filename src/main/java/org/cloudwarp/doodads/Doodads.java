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
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cloudwarp.doodads.registry.*;
import org.cloudwarp.doodads.test.ItemLoadedTest;
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
	public static DConfig loadedConfig;

	@Override
	public void onInitialize () {
		AutoConfig.register(DConfig.class, Toml4jConfigSerializer::new);
		configHolder = AutoConfig.getConfigHolder(DConfig.class);
		loadedConfig = getConfig();
		DEventHandler.registerEvents();
		ItemLoadedTest.init();
		DEntities.init();
		DParticles.init();
		DItems.registerItems();
		DBlocks.init();
		DLootTableModifiers.init();
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

	public static DConfig getConfig() {
		return configHolder.getConfig();
	}

	public static NbtCompound configToNBT(){
		DConfig config = getConfig();
		NbtCompound nbt = new NbtCompound();
		// Items
		nbt.putBoolean("enableSlingshot", config.doodadItems.enableSlingshot);
		nbt.putBoolean("enableDuctTape", config.doodadItems.enableDuctTape);
		nbt.putBoolean("enablePaintbrush", config.doodadItems.enablePaintbrush);
		nbt.putBoolean("enableSporeSword", config.doodadItems.enableSporeSword);
		nbt.putBoolean("enableDoodadBundle", config.doodadItems.enableDoodadBundle);
		nbt.putBoolean("enableGlareStaff", config.doodadItems.enableGlareStaff);
		nbt.putBoolean("enableMagicPlum", config.doodadItems.enableMagicPlum);
		nbt.putBoolean("enablePebble", config.doodadItems.enablePebble);
		nbt.putBoolean("enableRubberBand", config.doodadItems.enableRubberBand);
		nbt.putBoolean("enableScissors", config.doodadItems.enableScissors);
		// Trinkets
		nbt.putBoolean("enableBeaverTeeth", config.doodadTrinkets.enableBeaverTeeth);
		nbt.putBoolean("enableBendyStraw", config.doodadTrinkets.enableBendyStraw);
		nbt.putBoolean("enableBlossomBelt", config.doodadTrinkets.enableBlossomBelt);
		nbt.putBoolean("enableCactusRing", config.doodadTrinkets.enableCactusRing);
		nbt.putBoolean("enableEnderGoggles", config.doodadTrinkets.enableEnderGoggles);
		nbt.putBoolean("enableGlarePlushie", config.doodadTrinkets.enableGlarePlushie);
		nbt.putBoolean("enableLoggersGlove", config.doodadTrinkets.enableLoggersGlove);
		nbt.putBoolean("enableMoonStoneRing", config.doodadTrinkets.enableMoonStoneRing);
		nbt.putBoolean("enableSlimeyShoes", config.doodadTrinkets.enableSlimeyShoes);
		nbt.putBoolean("enableCelestialStoneRing", config.doodadTrinkets.enableCelestialStoneRing);
		nbt.putBoolean("enableMidnightsEye", config.doodadTrinkets.enableMidnightsEye);
		nbt.putBoolean("enableShulkerAglet", config.doodadTrinkets.enableShulkerAglet);
		nbt.putBoolean("enableSoggyGlove", config.doodadTrinkets.enableSoggyGlove);
		nbt.putBoolean("enableSpeedySneakers", config.doodadTrinkets.enableSpeedySneakers);
		nbt.putBoolean("enableSunStoneRing", config.doodadTrinkets.enableSunStoneRing);
		// Blocks
		nbt.putBoolean("enableAsphalt", config.doodadBlocks.enableAsphalt);
		nbt.putBoolean("enableBrickRoad", config.doodadBlocks.enableBrickRoad);
		nbt.putBoolean("enableStoneBrickRoad", config.doodadBlocks.enableStoneBrickRoad);
		nbt.putBoolean("enableYellowBrickRoad", config.doodadBlocks.enableYellowBrickRoad);
		nbt.putBoolean("enablePlatform", config.doodadBlocks.enablePlatform);
		nbt.putBoolean("enablePortableNether", config.doodadBlocks.enablePortableNether);
		// World Gen
		nbt.putFloat("chestDoodadSpawnRate", config.doodadWorldGen.chestDoodadSpawnRate);
		nbt.putFloat("zombieDoodadDropRate", config.doodadWorldGen.entityDoodadDropRate);
		nbt.putBoolean("enableDoodadSpawnsInVillages", config.doodadWorldGen.enableDoodadSpawnsInVillages);

		return nbt;
	}
	public static DConfig nbtToConfig(NbtCompound nbt){
		DConfig config = new DConfig();
		if(nbt == null){
			return config;
		}
		//Items
		config.doodadItems.enableSlingshot = nbt.getBoolean("enableSlingshot");
		config.doodadItems.enableDuctTape = nbt.getBoolean("enableDuctTape");
		config.doodadItems.enablePaintbrush = nbt.getBoolean("enablePaintbrush");
		config.doodadItems.enableSporeSword = nbt.getBoolean("enableSporeSword");
		config.doodadItems.enableDoodadBundle = nbt.getBoolean("enableDoodadBundle");
		config.doodadItems.enableGlareStaff = nbt.getBoolean("enableGlareStaff");
		config.doodadItems.enableMagicPlum = nbt.getBoolean("enableMagicPlum");
		config.doodadItems.enablePebble = nbt.getBoolean("enablePebble");
		config.doodadItems.enableRubberBand = nbt.getBoolean("enableRubberBand");
		config.doodadItems.enableScissors = nbt.getBoolean("enableScissors");

		// Trinkets
		config.doodadTrinkets.enableBeaverTeeth = nbt.getBoolean("enableBeaverTeeth");
		config.doodadTrinkets.enableBendyStraw = nbt.getBoolean("enableBendyStraw");
		config.doodadTrinkets.enableBlossomBelt = nbt.getBoolean("enableBlossomBelt");
		config.doodadTrinkets.enableCactusRing = nbt.getBoolean("enableCactusRing");
		config.doodadTrinkets.enableEnderGoggles = nbt.getBoolean("enableEnderGoggles");
		config.doodadTrinkets.enableGlarePlushie = nbt.getBoolean("enableGlarePlushie");
		config.doodadTrinkets.enableLoggersGlove = nbt.getBoolean("enableLoggersGlove");
		config.doodadTrinkets.enableMoonStoneRing = nbt.getBoolean("enableMoonStoneRing");
		config.doodadTrinkets.enableSlimeyShoes = nbt.getBoolean("enableSlimeyShoes");
		config.doodadTrinkets.enableCelestialStoneRing = nbt.getBoolean("enableCelestialStoneRing");
		config.doodadTrinkets.enableMidnightsEye = nbt.getBoolean("enableMidnightsEye");
		config.doodadTrinkets.enableShulkerAglet = nbt.getBoolean("enableShulkerAglet");
		config.doodadTrinkets.enableSoggyGlove = nbt.getBoolean("enableSoggyGlove");
		config.doodadTrinkets.enableSpeedySneakers = nbt.getBoolean("enableSpeedySneakers");
		config.doodadTrinkets.enableSunStoneRing = nbt.getBoolean("enableSunStoneRing");
		// Blocks
		config.doodadBlocks.enableAsphalt = nbt.getBoolean("enableAsphalt");
		config.doodadBlocks.enableBrickRoad = nbt.getBoolean("enableBrickRoad");
		config.doodadBlocks.enableStoneBrickRoad = nbt.getBoolean("enableStoneBrickRoad");
		config.doodadBlocks.enableYellowBrickRoad = nbt.getBoolean("enableYellowBrickRoad");
		config.doodadBlocks.enablePlatform = nbt.getBoolean("enablePlatform");
		config.doodadBlocks.enablePortableNether = nbt.getBoolean("enablePortableNether");
		// World Gen
		config.doodadWorldGen.chestDoodadSpawnRate = nbt.getFloat("chestDoodadSpawnRate");
		config.doodadWorldGen.entityDoodadDropRate = nbt.getFloat("zombieDoodadDropRate");
		config.doodadWorldGen.enableDoodadSpawnsInVillages = nbt.getBoolean("enableDoodadSpawnsInVillages");
		return config;
	}
}
