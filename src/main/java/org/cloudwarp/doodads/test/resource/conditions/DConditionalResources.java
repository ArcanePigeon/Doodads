package org.cloudwarp.doodads.test.resource.conditions;

import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import org.cloudwarp.doodads.Doodads;

public class DConditionalResources {

	@GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE)
	public void conditionalRecipes(TestContext context) {
		RecipeManager manager = context.getWorld().getRecipeManager();

		if (manager.get(Doodads.id("not_loaded")).isPresent()) {
			throw new AssertionError("not_loaded recipe should not have been loaded.");
		}

		if (manager.get(Doodads.id("loaded")).isEmpty()) {
			throw new AssertionError("loaded recipe should have been loaded.");
		}

		if (manager.get(Doodads.id("item_tags_populated")).isEmpty()) {
			throw new AssertionError("item_tags_populated recipe should have been loaded.");
		}

		long loadedRecipes = manager.values().stream().filter(r -> r.getId().getNamespace().equals(Doodads.MOD_ID)).count();
		if (loadedRecipes != 2) throw new AssertionError("Unexpected loaded recipe count: " + loadedRecipes);

		context.complete();
	}
}
