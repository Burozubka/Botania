package vazkii.botania.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.List;

/**
 * Implementation detail, you shouldn't need to use this.
 */
public interface IModRecipe {
    List<Ingredient> getInputs();
    ItemStack getOutput();
    ResourceLocation getId();
}
