package cn.elytra.gtnh.cutcorners.internal;

import cn.elytra.gtnh.cutcorners.CutCorners;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMapBackend;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.util.GTRecipe;
import gregtech.api.recipe.maps.LargeBoilerFuelBackend;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

public class AddRecipeHook {

    private static final Logger LOG = LogManager.getLogger();
    private static final ThreadLocal<RecipeMap<?>> RECIPE_MAP_CONTEXT = new ThreadLocal<>();

    @ApiStatus.Internal
    public static void setRecipeMapContext(@Nullable Object recipeMapUncast) {
        if (recipeMapUncast instanceof RecipeMap<?> recipeMap) {
            RECIPE_MAP_CONTEXT.set(recipeMap);
        } else if (recipeMapUncast == null) {
            RECIPE_MAP_CONTEXT.remove();
        } else {
            RECIPE_MAP_CONTEXT.remove();
            LOG.warn(
                "Attempted to set recipe map context, but got {}",
                recipeMapUncast.getClass(),
                new IllegalArgumentException("recipeMapUncast"));
        }
    }

    public static @Nullable RecipeMap<?> getRecipeMapContext() {
        return RECIPE_MAP_CONTEXT.get();
    }

    @ApiStatus.Internal
    public static void onRecipeAddedToRecipeMapBackend(RecipeMapBackend backend, GTRecipe recipe) {
        RecipeMap<?> recipeMapContext = backend instanceof LargeBoilerFuelBackend
            ? RecipeMaps.largeBoilerFakeFuels
            : getRecipeMapContext();
        try {
            CutCorners.getStrategy().updateGTRecipe(recipe, recipeMapContext);
        } catch (Exception e) {
            LOG.warn(
                "Failed to update the GTRecipe in {}",
                recipeMapContext != null ? recipeMapContext.unlocalizedName : "<RecipeMap context isn't provided>",
                e);
        }
    }

}
