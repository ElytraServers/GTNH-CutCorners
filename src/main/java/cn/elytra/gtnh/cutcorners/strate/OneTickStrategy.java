package cn.elytra.gtnh.cutcorners.strate;

import com.github.wohaopa.GTNHModify.mixins.late.gregtech.EyeOfHarmonyRecipeAccessor;
import com.github.wohaopa.GTNHModify.mixins.late.railcraft.BlastFurnaceRecipeAccessor;
import com.github.wohaopa.GTNHModify.mixins.late.railcraft.CokeOvenRecipeAccessor;
import gregtech.api.util.GTRecipe;
import mods.railcraft.api.crafting.IBlastFurnaceRecipe;
import mods.railcraft.api.crafting.ICokeOvenRecipe;
import tectech.recipe.EyeOfHarmonyRecipe;

@Deprecated
public class OneTickStrategy implements ICutCornerStrategy {

    @Override
    public void updateGTRecipe(GTRecipe recipe) {
        recipe.mDuration = 1;
    }

    @Override
    public void updateAssemblyLineRecipe(GTRecipe.RecipeAssemblyLine recipe) {
        recipe.mDuration = 1;
    }

    @Override
    public void updateEOHRecipe(EyeOfHarmonyRecipe recipe) {
        EyeOfHarmonyRecipeAccessor r = (EyeOfHarmonyRecipeAccessor) recipe;
        r.set_miningTimeSeconds(1);
        r.set_baseSuccessChance(5);
        r.set_euOutput(Integer.MAX_VALUE);
    }

    @Override
    public int getMaxFurnaceSmeltingTime(int original) {
        return 1;
    }

    @Override
    public int getBotaniaSpreaderPingbackTicks(int original) {
        return 1;
    }

    @Override
    public int getMaxProgressTime(Object instance, int original) {
        return 1;
    }

    @Override
    public int getThaumcraftFurnaceSmeltingTime(int original) {
        return 1;
    }

    @Override
    public int getThaumcraftNodeRegenerationTime(int original) {
        return 1;
    }

    @Override
    public void updateRailcraftCokeOvenRecipe(ICokeOvenRecipe recipe) {
        CokeOvenRecipeAccessor r = (CokeOvenRecipeAccessor) recipe;
        r.set_cookTime(1);
    }

    @Override
    public void updateRailcraftBlastFurnaceRecipe(IBlastFurnaceRecipe recipe) {
        BlastFurnaceRecipeAccessor r = (BlastFurnaceRecipeAccessor) recipe;
        r.set_cookTime(1);
    }
}
