package cn.elytra.gtnh.cutcorners.strate.impl.conf;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.config.NewConfig;
import cn.elytra.gtnh.cutcorners.mixins.late.gregtech.EyeOfHarmonyRecipeAccessor;
import cn.elytra.gtnh.cutcorners.mixins.late.railcraft.BlastFurnaceRecipeAccessor;
import cn.elytra.gtnh.cutcorners.mixins.late.railcraft.CokeOvenRecipeAccessor;
import cn.elytra.gtnh.cutcorners.strate.ICutCornerStrategy;
import cn.elytra.gtnh.cutcorners.util.ResearchStationHelper;
import gregtech.api.enums.TierEU;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTRecipe;
import mods.railcraft.api.crafting.IBlastFurnaceRecipe;
import mods.railcraft.api.crafting.ICokeOvenRecipe;
import org.jetbrains.annotations.Nullable;
import tectech.recipe.EyeOfHarmonyRecipe;

import java.util.HashMap;
import java.util.List;

public class NewConfigStrategy implements ICutCornerStrategy {

    public NewConfigStrategy() {
    }

    @Override
    public boolean isImmediateMode() {
        return NewConfig.immediateMode;
    }

    @Override
    public void updateGTRecipe(GTRecipe recipe, @Nullable RecipeMap<?> recipeMap) {
        if (recipeMap != null) {
            boolean blacklisted = NewConfig.BLACKLIST.get().contains(recipeMap.unlocalizedName);
            if (blacklisted != NewConfig.whitelistMode) {
                CutCorners.LOG.info("Skipped GTRecipe in {}", recipeMap.unlocalizedName);
                return;
            }
        }

        recipe.mDuration = NewConfig.GENERAL_MODIFICATION.get().apply(recipe.mDuration);
        if (NewConfig.recipesToLVTier) {
            recipe.mEUt = (int) TierEU.RECIPE_LV;
        }
    }

    @Override
    public void updateAssemblyLineRecipeList(List<GTRecipe.RecipeAssemblyLine> recipes) {
        if (NewConfig.BLACKLIST.get().contains(NewConfig.BL_GT_AL)) {
            CutCorners.LOG.info("Skipped GregTech Assembly Line Recipes");
            return;
        }

        CutCorners.LOG.info("Hacking GregTech Assembly Line Recipes");

        for (GTRecipe.RecipeAssemblyLine recipe : recipes) {
            recipe.mDuration = NewConfig.GENERAL_MODIFICATION.get().apply(recipe.mDuration);
            if (NewConfig.recipesToLVTier) {
                recipe.mEUt = (int) TierEU.RECIPE_LV;
            }
        }
    }

    @Override
    public void updateEOHRecipeMap(HashMap<String, EyeOfHarmonyRecipe> recipeMap) {
        if (NewConfig.BLACKLIST.get().contains(NewConfig.BL_GT_EOH)) {
            CutCorners.LOG.info("Skipped EOH Recipes");
            return;
        }

        CutCorners.LOG.info("Hacking EOH Recipes");

        for (EyeOfHarmonyRecipe recipe : recipeMap.values()) {
            var recipeAcc = (EyeOfHarmonyRecipeAccessor) recipe;
            recipeAcc.set_miningTimeSeconds(NewConfig.GENERAL_MODIFICATION.get().apply(recipeAcc.get_miningTimeSeconds()));
            recipeAcc.set_euStartCost(NewConfig.EYE_OF_HARMONY_START_EU_COST.get().apply(recipeAcc.get_euStartCost()));
        }
    }

    @Override
    public int getLargeBoilerFuelBurnTime(Object instance, int original) {
        return NewConfig.LARGE_BOILER_FUEL_BURN_TIME.get().apply(original);
    }

    @Override
    public void updateResearchStationRecipeMap(RecipeMap<?> recipeMap) {
        if (NewConfig.BLACKLIST.get().contains(NewConfig.BL_GT_RS)) {
            CutCorners.LOG.info("Skipped Research Station Recipes");
            return;
        }

        CutCorners.LOG.info("Hacking Research Station Recipes");

        for (GTRecipe recipe : recipeMap.getAllRecipes()) {
            recipe.mDuration = NewConfig.GENERAL_MODIFICATION.get().apply(recipe.mDuration);
            recipe.mSpecialValue = ResearchStationHelper.getSpecialValueAtAmp(
                recipe.mSpecialValue,
                NewConfig.RESEARCH_STATION_AMP.get()
                    .apply(ResearchStationHelper.getAmp(recipe.mSpecialValue)));
            recipe.mSpecialValue = ResearchStationHelper.getSpecialValueAtMinComputation(
                recipe.mSpecialValue,
                NewConfig.RESEARCH_STATION_MIN_COMPUTATION.get()
                    .apply(ResearchStationHelper.getMinComputation(recipe.mSpecialValue)));
        }
    }

    @Override
    public int getMaxFurnaceSmeltingTime(int original) {
        return NewConfig.BLACKLIST.get().contains(NewConfig.BL_FURNACE)
            ? original
            : NewConfig.GENERAL_MODIFICATION.get().apply(original);
    }

    @Override
    public int getMaxSpecialFurnaceSmeltingTime(int original) {
        // delegate to furnace.
        // to rational, it works as intended.
        // to fixed, everything is pull to a same speed, so it's not a problem.
        return getMaxFurnaceSmeltingTime(original);
    }

    @Override
    public int getMaxProgressTime(Object instance, int original) {
        return NewConfig.BLACKLIST.get().contains(NewConfig.BL_GT_WILDCARD)
            ? original
            : NewConfig.GENERAL_MODIFICATION.get().apply(original);
    }

    @Override
    public int getBotaniaSpreaderPingbackTicks(int original) {
        return NewConfig.BLACKLIST.get().contains(NewConfig.BL_BOT)
            ? original
            : NewConfig.GENERAL_MODIFICATION.get().apply(original);
    }

    @Override
    public int getThaumcraftFurnaceSmeltingTime(int original) {
        return NewConfig.BLACKLIST.get().contains(NewConfig.BL_TC)
            ? original
            : NewConfig.GENERAL_MODIFICATION.get().apply(original);
    }

    @Override
    public int getThaumcraftNodeRegenerationTime(int original) {
        return NewConfig.BLACKLIST.get().contains(NewConfig.BL_TC)
            ? original
            : NewConfig.GENERAL_MODIFICATION.get().apply(original);
    }

    @Override
    public void updateRailcraftCokeOvenRecipe(ICokeOvenRecipe recipe) {
        if (NewConfig.BLACKLIST.get().contains(NewConfig.BL_RC)) {
            return;
        }

        var recipeAcc = (CokeOvenRecipeAccessor) recipe;
        recipeAcc.set_cookTime(NewConfig.GENERAL_MODIFICATION.get().apply(recipeAcc.get_cookTime()));
    }

    @Override
    public void updateRailcraftBlastFurnaceRecipe(IBlastFurnaceRecipe recipe) {
        if (NewConfig.BLACKLIST.get().contains(NewConfig.BL_RC)) {
            return;
        }

        var recipeAcc = (BlastFurnaceRecipeAccessor) recipe;
        recipeAcc.set_cookTime(NewConfig.GENERAL_MODIFICATION.get().apply(recipeAcc.get_cookTime()));
    }

    @Override
    public int getWaterPurificationCycleTime(int original) {
        if (NewConfig.BLACKLIST.get().contains(NewConfig.BL_GT_WPP)) {
            return original;
        }
        // fallback to the max progress time
        return NewConfig.WATER_PURIFICATION_CYCLE.get().apply(original);
    }

    @Override
    public float getPurificationSuccessChance(Object instance, float original) {
        return NewConfig.WATER_PURIFICATION_SUCCESS_CHANCE.get().apply(original);
    }

    @Override
    public float getBlockStrength(float original) {
        // prevent from breaking bedrocks.
        if (!NewConfig.allBreakable && original <= 0.0F) return original;
        return NewConfig.BLOCK_STRENGTH.get().safeApply(original);
    }

    @Override
    public boolean canHarvestBlock(boolean original) {
        return NewConfig.allHarvestable || original;
    }

    @Override
    public float getCropsNHMultiplier(float original) {
        float value = NewConfig.growthMultiplierCropsNH;
        return value > 0 ? value : original;
    }

    @Override
    public int getCropsNHStickTickRate(int original) {
        int value = NewConfig.tickRateCropsNH;
        return value > 0 ? value : original;
    }

    @Override
    public int getForestryQueenInterval(int original) {
        int value = NewConfig.queenIntervalForestry;
        return value > 0 ? value : original;
    }
}
