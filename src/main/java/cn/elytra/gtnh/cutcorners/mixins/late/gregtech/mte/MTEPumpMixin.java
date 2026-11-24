package cn.elytra.gtnh.cutcorners.mixins.late.gregtech.mte;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import gregtech.common.tileentities.machines.basic.MTEPump;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

@Mixin(value = MTEPump.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.GregTech))
public class MTEPumpMixin {

    @ModifyExpressionValue(
        method = "onPostTick",
        at = @At(value = "INVOKE", target = "Lgregtech/api/util/GTUtility;safeInt(J)I"))
    private int cc$modifyDuration1(int original) {
        // mPumpTimer is used as the duration variable.
        // replacing this one will change the duration of fluid draining.
        // mining pipe down putting is not affected.
        return CutCorners.getStrategy().getPumpAndMinerDuration(original);
    }

    @ModifyExpressionValue(method = "onPostTick", at = @At(value = "CONSTANT", args = "longValue=5"))
    private long cc$retractInterval(long original) {
        return CutCorners.getStrategy().isImmediateMode() ? 1 : original;
    }

}
