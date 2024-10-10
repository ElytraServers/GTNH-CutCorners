package cn.elytra.gtnh.cutcorners.strate;

import gregtech.api.util.GTRecipe;
import mods.railcraft.api.crafting.IBlastFurnaceRecipe;
import mods.railcraft.api.crafting.ICokeOvenRecipe;
import net.minecraft.item.ItemStack;
import tectech.recipe.EyeOfHarmonyRecipe;

public interface ICutCornerStrategy {

    default void updateGTRecipe(GTRecipe recipe) {
    }

    default void updateAssemblyLineRecipe(GTRecipe.RecipeAssemblyLine recipe) {
    }

    default void updateEOHRecipe(EyeOfHarmonyRecipe recipe) {
    }

    default void updateResearchStationRecipe(GTRecipe recipe) {
    }

    default void updateFurnaceRecipe_size(ItemStack stackIn, ItemStack stackOut) {
    }

    default int getMaxFurnaceSmeltingTime(int original) {
        return original;
    }

    default int getBotaniaSpreaderPingbackTicks(int original) {
        return original;
    }

    default int getMaxProgressTime(Object instance, int original) {
        return original;
    }

    default int getThaumcraftFurnaceSmeltingTime(int original) {
        return original;
    }

    default int getThaumcraftNodeRegenerationTime(int original) {
        return original;
    }

    default void updateRailcraftCokeOvenRecipe(ICokeOvenRecipe recipe) {
    }

    default void updateRailcraftBlastFurnaceRecipe(IBlastFurnaceRecipe recipe) {
    }

}
