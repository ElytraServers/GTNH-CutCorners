package cn.elytra.gtnh.cutcorners.mixins.late.gregtech.pre290;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import gregtech.common.tileentities.machines.multi.MTEMultiFurnace;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = MTEMultiFurnace.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.GregTech))
public class GT_MetaTileEntity_MultiFurnaceMixin {

    @Redirect(method = "checkProcessing", at = @At(value = "FIELD", target = "Lgregtech/common/tileentities/machines/multi/MTEMultiFurnace;mMaxProgresstime:I", opcode = Opcodes.PUTFIELD))
    private void gtnhcc$overwriteRecipeDuration(MTEMultiFurnace instance, int value) {
        instance.mMaxProgresstime = CutCorners.getStrategy().getMaxProgressTime(this, value);
    }
}
