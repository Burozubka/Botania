/**
 * This class was created by <Hubry>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 *
 * File Created @ [Sep 23 2019, 3:04 PM (GMT)]
 */
package vazkii.botania.client.patchouli;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import vazkii.patchouli.api.IComponentRenderContext;
import vazkii.patchouli.api.PatchouliAPI;
import vazkii.patchouli.client.book.gui.GuiBook;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class PatchouliUtils {

	/**
	 * Extracts the tick counter from the current render context, if possible.
	 * Note: uses Patchouli internals. TODO expose tick counter in the Patchouli API?
	 */
	public static int getBookTicksElapsed(IComponentRenderContext context) {
		return context instanceof GuiBook ? ((GuiBook) context).ticksInBook : 0;
	}

	/**
	 * Combines the ingredients, returning the first matching stack of each, then the second stack of each, etc.
	 * looping back ingredients that run out of matched stacks, until the ingredients reach the length
	 * of the longest ingredient in the recipe set.
	 * <p>
	 * Note: As Patchouli internally converts the list of stacks into an ingredient, which will ignore empty stacks,
	 * empty ingredients will result in displaying stacks where nothing should be displayed instead.
	 *
	 * @param ingredients           List of ingredients in the specific slot
	 * @param longestIngredientSize Longest ingredient in the entire recipe
	 * @return Serialized Patchouli ingredient string
	 */
	public static String interweaveIngredients(List<Ingredient> ingredients, int longestIngredientSize) {
		if(ingredients.size() == 1) {
			return PatchouliAPI.instance.serializeIngredient(ingredients.get(0));
		}

		ItemStack[] empty = {ItemStack.EMPTY};
		List<ItemStack[]> stacks = new ArrayList<>();
		for(Ingredient ingredient : ingredients) {
			if(ingredient != null && !ingredient.hasNoMatchingItems()) {
				stacks.add(ingredient.getMatchingStacks());
			} else {
				stacks.add(empty);
			}
		}
		StringJoiner joiner = new StringJoiner(",");
		for(int i = 0; i < longestIngredientSize; i++) {
			for(ItemStack[] stack : stacks) {
				joiner.add(PatchouliAPI.instance.serializeItemStack(stack[i % stack.length]));
			}
		}
		return joiner.toString();
	}

	/**
	 * Overload of the method above that uses the provided list's longest ingredient size.
	 */
	public static String interweaveIngredients(List<Ingredient> ingredients) {
		return interweaveIngredients(ingredients, ingredients.stream().mapToInt(ingr -> ingr.getMatchingStacks().length).max().orElse(1));
	}
}
