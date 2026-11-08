package cn.elytra.gtnh.cutcorners.mixins.late.gregtech.pre290;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import gregtech.api.interfaces.ITexture;
import gregtech.api.metatileentity.implementations.MTEBasicMachine;
import gregtech.common.tileentities.machines.steam.MTESteamFurnaceBronze;
import gregtech.common.tileentities.machines.steam.MTESteamFurnaceSteel;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = { MTESteamFurnaceBronze.class, MTESteamFurnaceSteel.class }, remap = false)
@Restriction(require = @Condition(ModConst.ModId.GregTech))
public abstract class MTESteamFurnaceMixin extends MTEBasicMachine {

    public MTESteamFurnaceMixin(int aID, String aName, String aNameRegional, int aTier, int aAmperage,
        String aDescription, int aInputSlotCount, int aOutputSlotCount, ITexture... aOverlays) {
        super(aID, aName, aNameRegional, aTier, aAmperage, aDescription, aInputSlotCount, aOutputSlotCount, aOverlays);
    }

    @Inject(method = "checkRecipe", at = @At("RETURN"))
    private void gtnhcc$overwriteTime(CallbackInfoReturnable<Integer> cir) {
        if(cir.getReturnValueI() == FOUND_AND_SUCCESSFULLY_USED_RECIPE) {
            // set progress time to 1 if successfully found the recipe
            this.mMaxProgresstime = CutCorners.getStrategy().getMaxProgressTime(this, mMaxProgresstime);
        }
    }

}
