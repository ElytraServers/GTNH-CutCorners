package cn.elytra.gtnh.cutcorners.strate;

import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTRecipe;
import mods.railcraft.api.crafting.IBlastFurnaceRecipe;
import mods.railcraft.api.crafting.ICokeOvenRecipe;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import tectech.recipe.EyeOfHarmonyRecipe;

import java.util.HashMap;
import java.util.List;

/**
 * The strategy of how to work with modifications.
 * <p>
 * Methods naming starting with <i>update</i> are mostly invoked once per recipe on game load completion, which they are
 * not changing before next game restart.
 * <p>
 * Others are dynamically invoked during the machine updates.
 * They are changeable while the game is still running, but it is not recommended!
 */
public interface ICutCornerStrategy {

    /**
     * Used as a flag indicate whether CutCorner is in 1-tick mode or not.
     *
     * @return {@code true} if in immediate mode (or 1-tick mode).
     */
    default boolean isImmediateMode() {
        return false;
    }

    /**
     * Modify the {@link GTRecipe} in the recipe map.
     * It should be invoked when the recipe is added to the recipe map.
     */
    default void updateGTRecipe(GTRecipe recipe, @Nullable RecipeMap<?> recipeMap) {
    }

    /**
     * Modify the {@link GTRecipe.RecipeAssemblyLine} list.
     */
    default void updateAssemblyLineRecipeList(List<GTRecipe.RecipeAssemblyLine> recipes) {
    }

    default void updateEOHRecipeMap(HashMap<String, EyeOfHarmonyRecipe> recipeMap) {
    }

    /**
     * Return the burn time of fuels in Large Boilers.
     * It will be invoked when a Large Boiler starts burning a fuel.
     */
    default int getLargeBoilerFuelBurnTime(Object instance, int original) {
        return original;
    }

    /**
     * Modify the {@link GTRecipe Research Station Recipe} map.
     */
    default void updateResearchStationRecipeMap(RecipeMap<?> recipeMap) {
    }

    /**
     * Modify the {@link GTRecipe Research Station GT_Recipe}.
     * It should be invoked once per recipe on game load completion.
     */
    @Deprecated
    default void updateResearchStationRecipe(GTRecipe recipe) {
    }

    /**
     * Modify the input and output of furnace recipes.
     * <p>
     * You should make modification directly on the itemstack references, like editing sizes.
     * <p>
     * It should be invoked once per recipe on game load completion.
     *
     * @param stackIn  the input itemstack
     * @param stackOut the output itemstack
     */
    default void updateFurnaceRecipe_size(ItemStack stackIn, ItemStack stackOut) {
    }

    /**
     * Return the max smelting time (working value) of furnaces.
     * It will be invoked on furnace updates.
     */
    default int getMaxFurnaceSmeltingTime(int original) {
        return original;
    }

    /**
     * Return the max smelting time of Blast Furnaces and Smokers from Et Futurum Requiem.
     * It will be invoked on their updates.
     */
    default int getMaxSpecialFurnaceSmeltingTime(int original) {
        return original;
    }

    /**
     * Return the pingback ticks of Botania spreaders.
     * It will be invoked on spreader updates.
     */
    default int getBotaniaSpreaderPingbackTicks(int original) {
        return original;
    }

    /**
     * Return the max progress time of GregTech machines.
     * It will be invoked on machine updates.
     *
     * @param instance the reference instance of the GregTech machine.
     *                 You can make conditions based on this instance.
     */
    default int getMaxProgressTime(Object instance, int original) {
        return original;
    }

    /**
     * Return the smelting time of Thaumcraft Furnaces.
     * It will be invoked on TC furnace updates.
     */
    default int getThaumcraftFurnaceSmeltingTime(int original) {
        return original;
    }

    /**
     * Return the node regeneration time of Thaumcraft Nodes.
     * It will be invoked on TC node updates.
     */
    default int getThaumcraftNodeRegenerationTime(int original) {
        return original;
    }

    /**
     * Modify the {@link ICokeOvenRecipe}.
     * It should be invoked once per recipe on game load completion.
     */
    default void updateRailcraftCokeOvenRecipe(ICokeOvenRecipe recipe) {
    }

    /**
     * Modify the {@link IBlastFurnaceRecipe}.
     * It should be invoked once per recipe on game load completion.
     */
    default void updateRailcraftBlastFurnaceRecipe(IBlastFurnaceRecipe recipe) {
    }

    /**
     * Return the max cycle time of the water purification plant.
     */
    default int getWaterPurificationCycleTime(int original) {
        return original;
    }

    default float getPurificationSuccessChance(Object instance, float original) {
        return original;
    }

    default float getBlockStrength(float original, boolean harvested) {
        return original;
    }

    default boolean canHarvestBlock(boolean original) {
        return original;
    }

    default float getCropsNHMultiplier(float original) {
        return original;
    }

    default int getCropsNHStickTickRate(int original) {
        return original;
    }

    default int getForestryQueenInterval(int original) {
        return original;
    }

    default boolean isNoFlyingDigPenalty() {
        return false;
    }

    default int getQuantumTankOutputInterval(int original) {
        return original;
    }

    default int getQuantumTankOutputAmount(int original) {
        return original;
    }

    default int getAshroomAspectOrbValue(int original) {
        return original;
    }

    default int getAshroomDropCount(int original) {
        return original;
    }
}
