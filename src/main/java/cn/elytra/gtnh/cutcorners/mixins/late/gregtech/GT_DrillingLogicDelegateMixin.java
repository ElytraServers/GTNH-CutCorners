package cn.elytra.gtnh.cutcorners.mixins.late.gregtech;

import cn.elytra.gtnh.cutcorners.CutCorners;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import gregtech.common.misc.DrillingLogicDelegate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = DrillingLogicDelegate.class, remap = false)
public class GT_DrillingLogicDelegateMixin {

    /// The divisor of the {@code aTick % (getMachineSpeed() / 5)} throttle in {@code onPostTickRetract}.
    ///
    /// The speed has to stay at or above this value, which also means this constant must be kept equal to the divisor
    /// in that expression: the division is an integer one, so anything below it evaluates to 0 and turns the modulo
    /// into a division by zero.
    private static final int RETRACT_SPEED_DIVISOR = 5;

    /// Scales the speed used while retracting the mining pipe, so that the duration modification applies here too.
    ///
    /// In immediate mode the speed is pinned to {@link #RETRACT_SPEED_DIVISOR}, giving the fastest achievable rate of
    /// one pipe segment per tick. This bypasses the scaling and its blacklist, as it is unrelated to recipe
    /// durations. Otherwise the scaled value is clamped to the divisor.
    ///
    /// The mining cycle itself is handled by {@link GT_MetaTileEntity_MinerMixin}.
    @ModifyExpressionValue(
        method = "onPostTickRetract",
        at = @At(value = "INVOKE", target = "Lgregtech/common/misc/IDrillingLogicDelegateOwner;getMachineSpeed()I"))
    private int gtnhcc$modifyRetractSpeed(int original) {
        if (CutCorners.getStrategy().isImmediateMode()) {
            return RETRACT_SPEED_DIVISOR;
        }
        return Math.max(CutCorners.getStrategy().getMaxProgressTime(this, original), RETRACT_SPEED_DIVISOR);
    }
}
