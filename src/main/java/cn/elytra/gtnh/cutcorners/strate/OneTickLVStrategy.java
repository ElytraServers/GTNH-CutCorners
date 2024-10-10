package cn.elytra.gtnh.cutcorners.strate;

import com.github.wohaopa.GTNHModify.mixins.late.gregtech.EyeOfHarmonyRecipeAccessor;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTRecipe;
import tectech.recipe.EyeOfHarmonyRecipe;

@Deprecated
public class OneTickLVStrategy extends OneTickStrategy {

    private static final int RECIPE_LV = (int) TierEU.RECIPE_LV;

    @Override
    public int getMaxProgressTime(Object instance, int original) {
        return super.getMaxProgressTime(instance, original);
    }

    @Override
    public void updateGTRecipe(GTRecipe recipe) {
        super.updateGTRecipe(recipe);
        recipe.mEUt = RECIPE_LV;
    }

    @Override
    public void updateAssemblyLineRecipe(GTRecipe.RecipeAssemblyLine recipe) {
        super.updateAssemblyLineRecipe(recipe);
        recipe.mEUt = RECIPE_LV;
    }

    @Override
    public void updateEOHRecipe(EyeOfHarmonyRecipe recipe) {
        super.updateEOHRecipe(recipe);
        EyeOfHarmonyRecipeAccessor r = (EyeOfHarmonyRecipeAccessor) recipe;
        r.set_euStartCost(RECIPE_LV);
    }
}
