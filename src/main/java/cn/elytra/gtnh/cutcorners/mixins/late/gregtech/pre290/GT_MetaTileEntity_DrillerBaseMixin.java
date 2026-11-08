package cn.elytra.gtnh.cutcorners.mixins.late.gregtech.pre290;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.common.tileentities.machines.multi.MTEDrillerBase;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MTEDrillerBase.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.GregTech))
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
