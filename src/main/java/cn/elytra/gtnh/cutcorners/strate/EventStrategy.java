package cn.elytra.gtnh.cutcorners.strate;

import cn.elytra.gtnh.cutcorners.strate.event.CutCornersEvents;
import gregtech.api.util.GTRecipe;
import mods.railcraft.api.crafting.IBlastFurnaceRecipe;
import mods.railcraft.api.crafting.ICokeOvenRecipe;
import net.minecraft.item.ItemStack;
import tectech.recipe.EyeOfHarmonyRecipe;

public class EventStrategy implements ICutCornerStrategy {

    public void updateGTRecipe(GTRecipe recipe) {
        CutCornersEvents.onGTRecipe(recipe);
    }

    public void updateAssemblyLineRecipe(GTRecipe.RecipeAssemblyLine recipe) {
        CutCornersEvents.onAssemblyLineRecipe(recipe);
    }

    public void updateEOHRecipe(EyeOfHarmonyRecipe recipe) {
        CutCornersEvents.onEyeOfHarmonyRecipe(recipe);
    }

    @Override
    public void updateResearchStationRecipe(GTRecipe recipe) {
        CutCornersEvents.onResearchStationRecipe(recipe);
    }

    public void updateFurnaceRecipe_size(ItemStack stackIn, ItemStack stackOut) {
        // noop
    }

    public int getMaxFurnaceSmeltingTime(int original) {
        return CutCornersEvents.getFurnaceDuration(original);
    }

    public int getBotaniaSpreaderPingbackTicks(int original) {
        return CutCornersEvents.getBotaniaSpreadPingback(original);
    }

    public int getMaxProgressTime(Object instance, int original) {
        return CutCornersEvents.getGTMaxProgressTime(instance, original);
    }

    public int getThaumcraftFurnaceSmeltingTime(int original) {
        return CutCornersEvents.getTCFurnaceSmeltingTime(original);
    }

    public int getThaumcraftNodeRegenerationTime(int original) {
        return CutCornersEvents.getTCNodeRegenerationTime(original);
    }

    public void updateRailcraftCokeOvenRecipe(ICokeOvenRecipe recipe) {
        CutCornersEvents.onCokeOvenRecipe(recipe);
    }

    public void updateRailcraftBlastFurnaceRecipe(IBlastFurnaceRecipe recipe) {
        CutCornersEvents.onBlastFurnaceRecipe(recipe);
    }

}
