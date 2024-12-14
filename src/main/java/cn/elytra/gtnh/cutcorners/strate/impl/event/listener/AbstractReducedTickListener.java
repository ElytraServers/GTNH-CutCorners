package cn.elytra.gtnh.cutcorners.strate.impl.event.listener;

import cn.elytra.gtnh.cutcorners.strate.impl.event.event.GetDurationEvent;
import cn.elytra.gtnh.cutcorners.strate.impl.event.event.IHasDuration;
import cn.elytra.gtnh.cutcorners.strate.impl.event.event.IHasLongDuration;
import cn.elytra.gtnh.cutcorners.strate.impl.event.event.ModifyRecipeEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public abstract class AbstractReducedTickListener {

    protected abstract void transform(IHasDuration i);

    protected abstract void transform(IHasLongDuration i);

    @SubscribeEvent
    public void onGTRecipe(ModifyRecipeEvent.GregTech e) {
        transform(e);
    }

    @SubscribeEvent
    public void onGTAssemblyLine(ModifyRecipeEvent.GT_AssemblyLine e) {
        transform(e);
    }

    @SubscribeEvent
    public void onEOHRecipe(ModifyRecipeEvent.GT_EyeOfHarmony e) {
        transform(e);
    }

    @SubscribeEvent
    public void onResearchStation(ModifyRecipeEvent.GT_ResearchStation e) {
        transform(e);
    }

    @SubscribeEvent
    public void onRCCokeOven(ModifyRecipeEvent.RC_CokeOven e) {
        transform(e);
    }

    @SubscribeEvent
    public void onBlastFurnace(ModifyRecipeEvent.RC_BlastFurnace e) {
        transform(e);
    }

    @SubscribeEvent
    public void onFurnaceSmelting(GetDurationEvent.FurnaceSmeltingTime e) {
        transform(e);
    }

    @SubscribeEvent
    public void onBotaniaSpreader(GetDurationEvent.Botania_SpreadPingback e) {
        transform(e);
    }

    @SubscribeEvent
    public void onGTMachine(GetDurationEvent.GT_MaxProgressTime e) {
        transform(e);
    }

    @SubscribeEvent
    public void onTCFurnaceSmelting(GetDurationEvent.TC_FurnaceSmeltingTime e) {
        transform(e);
    }

    @SubscribeEvent
    public void onTCNodeRegen(GetDurationEvent.TC_NodeRegenerationTime e) {
        transform(e);
    }

}
