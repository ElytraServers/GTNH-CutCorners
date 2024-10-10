package com.github.wohaopa.GTNHModify.mixins.late.gregtech;

import cn.elytra.gtnh.cutcorners.CutCorners;
import gregtech.common.tileentities.machines.multi.MTEMultiFurnace;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = MTEMultiFurnace.class, remap = false)
public class GT_MetaTileEntity_MultiFurnaceMixin {

    @Redirect(method = "checkProcessing", at = @At(value = "FIELD", target = "Lgregtech/common/tileentities/machines/multi/MTEMultiFurnace;mMaxProgresstime:I", opcode = Opcodes.PUTFIELD))
    private void gtnhcc$overwriteRecipeDuration(MTEMultiFurnace instance, int value) {
        instance.mMaxProgresstime = CutCorners.getStrategy().getMaxProgressTime(this, value);
    }
}
