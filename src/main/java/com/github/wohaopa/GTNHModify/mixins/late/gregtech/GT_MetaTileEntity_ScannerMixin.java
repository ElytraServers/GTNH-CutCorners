package com.github.wohaopa.GTNHModify.mixins.late.gregtech;

import cn.elytra.gtnh.cutcorners.CutCorners;
import gregtech.common.tileentities.machines.basic.MTEScanner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = MTEScanner.class, remap = false)
public abstract class GT_MetaTileEntity_ScannerMixin {

    @ModifyArg(method = "checkRecipe", at = @At(value = "INVOKE", target = "Lgregtech/common/tileentities/machines/basic/MTEScanner;calculateOverclockedNess(II)V"), index = 1)
    private int gtnhcc$overwriteOverclockedNessCall(int value) {
        return CutCorners.getStrategy().getMaxProgressTime(this, value);
    }
}
