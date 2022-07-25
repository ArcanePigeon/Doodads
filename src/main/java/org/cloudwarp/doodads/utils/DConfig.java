package org.cloudwarp.doodads.utils;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import org.cloudwarp.doodads.Doodads;

@Config(name = Doodads.MOD_ID)
public class DConfig implements ConfigData {


	@ConfigEntry.Gui.CollapsibleObject
	public DoodadItems doodadItems = new DoodadItems();

	public static class DoodadItems {
		@ConfigEntry.Gui.Tooltip()
		public boolean enableSlingShot = true;
	}

}
