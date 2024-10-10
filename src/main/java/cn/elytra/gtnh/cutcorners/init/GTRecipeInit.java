package cn.elytra.gtnh.cutcorners.init;

import cn.elytra.gtnh.cutcorners.CutCorners;
import goodgenerator.api.recipe.GoodGeneratorRecipeMaps;
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
            if (map == GoodGeneratorRecipeMaps.naquadahReactorFuels) return;
            CutCorners.LOG.info("Updating GT_Recipe Map: {}", map.unlocalizedName);
            map.getAllRecipes().forEach(recipe -> CutCorners.getStrategy().updateGTRecipe(recipe));
        });
    }

    private static void updateAssemblyLineRecipes() {
        CutCorners.LOG.info("Updating Assembly Line Recipes");
        GTRecipe.RecipeAssemblyLine.sAssemblylineRecipes.forEach((recipe) -> CutCorners.getStrategy().updateAssemblyLineRecipe(recipe));
    }

    private static void updateEOHRecipes() {
        CutCorners.LOG.info("Updating Eye of Harmony Recipes");
        TecTechRecipeMaps.eyeOfHarmonyRecipes.getAllRecipes().forEach(recipe -> CutCorners.getStrategy().updateGTRecipe(recipe));

        var recipeMap = getRecipeHashMap(TecTech.eyeOfHarmonyRecipeStorage);
        recipeMap.forEach((s, recipe) -> CutCorners.getStrategy().updateEOHRecipe(recipe));
    }

    private static void updateResearchStationRecipes() {
        CutCorners.LOG.info("Updating Research Station Recipes");
        TecTechRecipeMaps.researchStationFakeRecipes.getAllRecipes().forEach(recipe -> CutCorners.getStrategy().updateResearchStationRecipe(recipe));
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
