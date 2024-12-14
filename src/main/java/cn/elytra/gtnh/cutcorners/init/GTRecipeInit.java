package cn.elytra.gtnh.cutcorners.init;

import cn.elytra.gtnh.cutcorners.CutCorners;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTRecipe;
import tectech.TecTech;
import tectech.recipe.EyeOfHarmonyRecipe;
import tectech.recipe.EyeOfHarmonyRecipeStorage;
import tectech.recipe.TecTechRecipeMaps;

import java.lang.reflect.Field;
import java.util.HashMap;

public class GTRecipeInit {

    public static void init() {
        updateGeneralRecipes();
        updateAssemblyLineRecipes();
        updateEOHRecipes();
        updateResearchStationRecipes();
    }

    private static void updateGeneralRecipes() {
        RecipeMap.ALL_RECIPE_MAPS.forEach((s, map) -> {
            CutCorners.getStrategy().updateGTRecipeMap(map);
        });
    }

    private static void updateAssemblyLineRecipes() {
        CutCorners.LOG.info("Updating Assembly Line Recipes");
        CutCorners.getStrategy().updateAssemblyLineRecipeList(GTRecipe.RecipeAssemblyLine.sAssemblylineRecipes);
    }

    private static void updateEOHRecipes() {
        CutCorners.LOG.info("Updating Eye of Harmony Recipes");
        CutCorners.getStrategy().updateGTRecipeMap(TecTechRecipeMaps.eyeOfHarmonyRecipes);

        var recipeMap = getRecipeHashMap(TecTech.eyeOfHarmonyRecipeStorage);
        CutCorners.getStrategy().updateEOHRecipeMap(recipeMap);
    }

    private static void updateResearchStationRecipes() {
        CutCorners.LOG.info("Updating Research Station Recipes");
        CutCorners.getStrategy().updateResearchStationRecipeMap(TecTechRecipeMaps.researchStationFakeRecipes);
    }

    private static final Field FIELD_RECIPE_HASH_MAP;

    static {
        try {
            FIELD_RECIPE_HASH_MAP = TecTech.eyeOfHarmonyRecipeStorage.getClass().getDeclaredField("recipeHashMap");
            FIELD_RECIPE_HASH_MAP.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private static HashMap<String, EyeOfHarmonyRecipe> getRecipeHashMap(EyeOfHarmonyRecipeStorage storage) {
        try {
            //noinspection unchecked
            return (HashMap<String, EyeOfHarmonyRecipe>) FIELD_RECIPE_HASH_MAP.get(storage);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
