package cn.elytra.gtnh.cutcorners.strate.event;

import cpw.mods.fml.common.eventhandler.EventBus;
import gregtech.api.util.GTRecipe;
import mods.railcraft.api.crafting.IBlastFurnaceRecipe;
import mods.railcraft.api.crafting.ICokeOvenRecipe;
import tectech.recipe.EyeOfHarmonyRecipe;

public class CutCornersEvents {

    public static final EventBus CC_EVENTS = new EventBus();

    public static void onGTRecipe(GTRecipe recipe) {
        CC_EVENTS.post(new ModifyRecipeEvent.GregTech(recipe));
    }

    public static void onAssemblyLineRecipe(GTRecipe.RecipeAssemblyLine recipe) {
        CC_EVENTS.post(new ModifyRecipeEvent.GT_AssemblyLine(recipe));
    }

    public static void onEyeOfHarmonyRecipe(EyeOfHarmonyRecipe recipe) {
        CC_EVENTS.post(new ModifyRecipeEvent.GT_EyeOfHarmony(recipe));
    }

    public static void onResearchStationRecipe(GTRecipe recipe) {
        CC_EVENTS.post(new ModifyRecipeEvent.GT_ResearchStation(recipe));
    }

    public static void onCokeOvenRecipe(ICokeOvenRecipe recipe) {
        CC_EVENTS.post(new ModifyRecipeEvent.RC_CokeOven(recipe));
    }

    public static void onBlastFurnaceRecipe(IBlastFurnaceRecipe recipe) {
        CC_EVENTS.post(new ModifyRecipeEvent.RC_BlastFurnace(recipe));
    }

    public static int getFurnaceDuration(int duration) {
        GetDurationEvent e = new GetDurationEvent.FurnaceSmeltingTime(duration);
        CC_EVENTS.post(e);
        return e.getDuration();
    }

    public static int getBotaniaSpreadPingback(int pingback) {
        GetDurationEvent e = new GetDurationEvent.Botania_SpreadPingback(pingback);
        CC_EVENTS.post(e);
        return e.getDuration();
    }

    public static int getGTMaxProgressTime(Object target, int duration) {
        GetDurationEvent.GT_MaxProgressTime e = new GetDurationEvent.GT_MaxProgressTime(target, duration);
        CC_EVENTS.post(e);
        return e.getDuration();
    }

    public static int getTCFurnaceSmeltingTime(int duration) {
        GetDurationEvent e = new GetDurationEvent.TC_FurnaceSmeltingTime(duration);
        CC_EVENTS.post(e);
        return e.getDuration();
    }

    public static int getTCNodeRegenerationTime(int duration) {
        GetDurationEvent e = new GetDurationEvent.TC_NodeRegenerationTime(duration);
        CC_EVENTS.post(e);
        return e.getDuration();
    }
}
