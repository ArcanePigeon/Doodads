package org.cloudwarp.doodads.registry;

import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import org.cloudwarp.doodads.Doodads;

public class DModelPredicateProvider {

	public static void init(){
		registerSlingshot(DItems.get("slingshot"));
	}

	private static void registerSlingshot(Item slingshot){
		ModelPredicateProviderRegistry.register(slingshot,Doodads.id("pull"),(stack, world, entity, seed) ->{
			if(entity == null || entity.getActiveItem() != stack){
				return 0.0f;
			}
			return ((float) stack.getMaxUseTime() - entity.getItemUseTimeLeft()) / 20.0f;
		});
		ModelPredicateProviderRegistry.register(slingshot,Doodads.id("pulling"),
				(stack,world,entity,seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1f : 0f);
	}
}
