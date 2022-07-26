package org.cloudwarp.doodads;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cloudwarp.doodads.registry.*;
import org.cloudwarp.doodads.utils.DConfig;

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
	}

}
