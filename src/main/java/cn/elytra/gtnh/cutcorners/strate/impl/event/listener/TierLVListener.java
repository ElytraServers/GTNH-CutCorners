package cn.elytra.gtnh.cutcorners.strate.impl.event.listener;

import cn.elytra.gtnh.cutcorners.strate.impl.event.event.ModifyRecipeEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import gregtech.api.enums.TierEU;

public class TierLVListener {

    private static final int RECIPE_LV = (int) TierEU.RECIPE_LV;

    @SubscribeEvent
    public void onGTRecipe(ModifyRecipeEvent.GregTech e) {
        e.recipe.mEUt = RECIPE_LV;
    }

    @SubscribeEvent
    public void onAssembly(ModifyRecipeEvent.GT_AssemblyLine e) {
        e.recipe.mEUt = RECIPE_LV;
    }

    @SubscribeEvent
    public void onEOH(ModifyRecipeEvent.GT_EyeOfHarmony e) {
        e.getAccessor().set_euStartCost(RECIPE_LV);
    }

    @SubscribeEvent
    public void onResearchStation(ModifyRecipeEvent.GT_ResearchStation e) {
        e.setAmp(1);
    }

}
