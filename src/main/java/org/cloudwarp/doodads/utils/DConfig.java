package org.cloudwarp.doodads.utils;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import org.cloudwarp.doodads.Doodads;

@Config(name = Doodads.MOD_ID)
public class DConfig implements ConfigData {


	@ConfigEntry.Gui.CollapsibleObject
	public DoodadItems doodadItems = new DoodadItems();
	@ConfigEntry.Gui.CollapsibleObject
	public DoodadTrinkets doodadTrinkets = new DoodadTrinkets();
	@ConfigEntry.Gui.CollapsibleObject
	public DoodadBlocks doodadBlocks = new DoodadBlocks();
	@ConfigEntry.Gui.CollapsibleObject
	public DoodadWorldGen doodadWorldGen = new DoodadWorldGen();

	public static class DoodadItems {
		public boolean enableSlingshot           = true;
		public boolean enableDuctTape            = true;
		public boolean enablePaintbrush          = true;
		public boolean enableSporeSword          = true;
		public boolean enableDoodadBundle        = true;
		public boolean enableGlareStaff          = true;
		public boolean enableMagicPlum           = true;
		public boolean enablePebble              = true;
		public boolean enableRubberBand          = true;
		public boolean enableScissors            = true;
	}
	public static class DoodadTrinkets {
		public boolean enableBeaverTeeth         = true;
		public boolean enableBendyStraw          = true;
		public boolean enableBlossomBelt         = true;
		public boolean enableCactusRing          = true;
		public boolean enableEnderGoggles        = true;
		public boolean enableGlarePlushie        = true;
		public boolean enableLoggersGlove        = true;
		public boolean enableMoonStoneRing       = true;
		public boolean enableSlimeyShoes         = true;
		public boolean enableCelestialStoneRing  = true;
		public boolean enableMidnightsEye        = true;
		public boolean enableShulkerAglet        = true;
		public boolean enableSoggyGlove          = true;
		public boolean enableSpeedySneakers      = true;
		public boolean enableSunStoneRing        = true;
	}
	public static class DoodadBlocks {
		public boolean enableAsphalt = true;
		public boolean enableBrickRoad = true;
		public boolean enableStoneBrickRoad = true;
		public boolean enableYellowBrickRoad = true;
		public boolean enablePlatform = true;
		public boolean enablePortableNether = true;
	}

	public static class DoodadWorldGen {
		public float chestDoodadSpawnRate = 19f;
		public boolean enableDoodadSpawnsInVillages = false;
		public float entityDoodadDropRate = 16f;

	}

}
