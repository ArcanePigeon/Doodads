package org.cloudwarp.doodads;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cloudwarp.doodads.registry.*;

public class Doodads implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "doodads";

	public static Identifier id (String path) {
		return new Identifier(MOD_ID, path);
	}

	@Override
	public void onInitialize () {
		DEntities.init();
		DItems.registerItems();
	}

}
