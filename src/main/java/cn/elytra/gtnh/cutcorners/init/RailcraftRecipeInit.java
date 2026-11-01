package cn.elytra.gtnh.cutcorners.init;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.config.CutCornersConfig;
import mods.railcraft.common.util.crafting.BlastFurnaceCraftingManager;
import mods.railcraft.common.util.crafting.CokeOvenCraftingManager;

import java.util.Objects;

public class RailcraftRecipeInit {

    public static void init() {
        updateCokeOvenRecipes();
        updateBlastFurnaceRecipes();
    }

    private static void updateCokeOvenRecipes() {
        if (isRailcraftBlacklisted()) {
            CutCorners.LOG.info("Skipped Railcraft Coke Oven Recipes");
            return;
        }

        CutCorners.LOG.info("Updating Railcraft Coke Oven Recipes");
        CokeOvenCraftingManager.getInstance().getRecipes().forEach((recipe) -> CutCorners.getStrategy().updateRailcraftCokeOvenRecipe(recipe));
    }

    private static void updateBlastFurnaceRecipes() {
        if (isRailcraftBlacklisted()) {
            CutCorners.LOG.info("Skipped Railcraft Blast Furnace Recipes");
            return;
        }

        CutCorners.LOG.info("Updating Railcraft Blast Furnace Recipes");
        BlastFurnaceCraftingManager.getInstance().getRecipes().forEach((recipe) -> CutCorners.getStrategy().updateRailcraftBlastFurnaceRecipe(recipe));
    }

    private static boolean isRailcraftBlacklisted() {
        return Objects.requireNonNull(CutCornersConfig.instance).doesBlacklistRailcraft();
    }

}
