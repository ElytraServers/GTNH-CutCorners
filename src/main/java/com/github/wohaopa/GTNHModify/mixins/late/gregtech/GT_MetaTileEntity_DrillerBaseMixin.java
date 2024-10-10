package com.github.wohaopa.GTNHModify.mixins.late.gregtech;

import cn.elytra.gtnh.cutcorners.CutCorners;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.common.tileentities.machines.multi.MTEDrillerBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MTEDrillerBase.class, remap = false)
public abstract class GT_MetaTileEntity_DrillerBaseMixin {

    @Inject(
        method = "checkProcessing",
        at = @At(
            value = "INVOKE",
            target = "Lgregtech/common/tileentities/machines/multi/MTEDrillerBase;setElectricityStats()V",
            shift = At.Shift.AFTER))
    private void gtnhcc$updateMaxProgressTime(CallbackInfoReturnable<CheckRecipeResult> cir) {
        MTEDrillerBase self = (MTEDrillerBase) (Object) this;
        self.mMaxProgresstime = CutCorners.getStrategy().getMaxProgressTime(self, self.mMaxProgresstime);
    }
}
