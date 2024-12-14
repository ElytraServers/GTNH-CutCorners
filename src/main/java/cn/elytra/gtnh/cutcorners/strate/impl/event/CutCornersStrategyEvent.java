package cn.elytra.gtnh.cutcorners.strate.impl.event;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.strate.ICutCornerStrategy;
import goodgenerator.api.recipe.GoodGeneratorRecipeMaps;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTRecipe;
import mods.railcraft.api.crafting.IBlastFurnaceRecipe;
import mods.railcraft.api.crafting.ICokeOvenRecipe;
import net.minecraft.item.ItemStack;
import tectech.recipe.EyeOfHarmonyRecipe;

import java.util.HashMap;
import java.util.List;

public class CutCornersStrategyEvent implements ICutCornerStrategy {

    @Override
    public void updateGTRecipeMap(RecipeMap<?> recipeMap) {
        if (recipeMap == GoodGeneratorRecipeMaps.naquadahReactorFuels) return;

        CutCorners.LOG.info("Updating GTRecipe Map: {}", recipeMap.unlocalizedName);
        recipeMap.getAllRecipes().forEach(this::updateGTRecipe);
    }

    @SuppressWarnings("deprecation")
    public void updateGTRecipe(GTRecipe recipe) {
        CutCornersEventDispatchHelper.onGTRecipe(recipe);
    }

    @Override
    public void updateAssemblyLineRecipeList(List<GTRecipe.RecipeAssemblyLine> recipes) {
        recipes.forEach(this::updateAssemblyLineRecipe);
    }

    @SuppressWarnings("deprecation")
    public void updateAssemblyLineRecipe(GTRecipe.RecipeAssemblyLine recipe) {
        CutCornersEventDispatchHelper.onAssemblyLineRecipe(recipe);
    }

    @Override
    public void updateEOHRecipeMap(HashMap<String, EyeOfHarmonyRecipe> recipeMap) {
        recipeMap.values().forEach(this::updateEOHRecipe);
    }

    @SuppressWarnings("deprecation")
    public void updateEOHRecipe(EyeOfHarmonyRecipe recipe) {
        CutCornersEventDispatchHelper.onEyeOfHarmonyRecipe(recipe);
    }

    @Override
    public void updateResearchStationRecipeMap(RecipeMap<?> recipeMap) {
        recipeMap.getAllRecipes().forEach(this::updateResearchStationRecipe);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void updateResearchStationRecipe(GTRecipe recipe) {
        CutCornersEventDispatchHelper.onResearchStationRecipe(recipe);
    }

    public void updateFurnaceRecipe_size(ItemStack stackIn, ItemStack stackOut) {
        // noop
    }

    public int getMaxFurnaceSmeltingTime(int original) {
        return CutCornersEventDispatchHelper.getFurnaceDuration(original);
    }

    public int getBotaniaSpreaderPingbackTicks(int original) {
        return CutCornersEventDispatchHelper.getBotaniaSpreadPingback(original);
    }

    public int getMaxProgressTime(Object instance, int original) {
        return CutCornersEventDispatchHelper.getGTMaxProgressTime(instance, original);
    }

    public int getThaumcraftFurnaceSmeltingTime(int original) {
        return CutCornersEventDispatchHelper.getTCFurnaceSmeltingTime(original);
    }

    public int getThaumcraftNodeRegenerationTime(int original) {
        return CutCornersEventDispatchHelper.getTCNodeRegenerationTime(original);
    }

    public void updateRailcraftCokeOvenRecipe(ICokeOvenRecipe recipe) {
        CutCornersEventDispatchHelper.onCokeOvenRecipe(recipe);
    }

    public void updateRailcraftBlastFurnaceRecipe(IBlastFurnaceRecipe recipe) {
        CutCornersEventDispatchHelper.onBlastFurnaceRecipe(recipe);
    }

}
