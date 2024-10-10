package com.github.wohaopa.GTNHModify.mixins.late.gregtech;

import cn.elytra.gtnh.cutcorners.CutCorners;
import gregtech.common.tileentities.machines.basic.MTEMiner;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MTEMiner.class, remap = false)
public class GT_MetaTileEntity_MinerMixin {

    @Mutable
    @Final
    @Shadow
    private int mSpeed;

    @Inject(method = "<init>*", at = @At("RETURN"))
    private void gtnhcc$overwriteSpeed(CallbackInfo info) {
        mSpeed = CutCorners.getStrategy().getMaxProgressTime(this, mSpeed);
    }
}
