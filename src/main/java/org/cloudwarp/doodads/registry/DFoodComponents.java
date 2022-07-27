package org.cloudwarp.doodads.registry;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class DFoodComponents {
	public static final FoodComponent MAGIC_PLUM_FOOD = new FoodComponent.Builder()
			.hunger(6)
			.saturationModifier(1.2f)
			.statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 3000, 0), 1.0f)
			.alwaysEdible()
			.build();
}
