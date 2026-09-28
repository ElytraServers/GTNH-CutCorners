package cn.elytra.gtnh.cutcorners.mixins.late.gregtech;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import gregtech.common.misc.DrillingLogicDelegate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = DrillingLogicDelegate.class, remap = false)
public class GT_DrillingLogicDelegateMixin {

    /// Keeps {@code getMachineSpeed()} at a usable minimum while retracting the mining pipe.
    ///
    /// {@code onPostTickRetract} throttles itself with {@code aTick % (getMachineSpeed() / 5)}. This is an integer
    /// division, so any speed below 5 evaluates to 0 and the modulo throws {@link ArithmeticException}.
    /// Speeds of 5 and above are passed through untouched, hence this only affects degenerate values.
    @ModifyExpressionValue(
        method = "onPostTickRetract",
        at = @At(value = "INVOKE", target = "Lgregtech/common/misc/IDrillingLogicDelegateOwner;getMachineSpeed()I"))
    private int gtnhcc$ensureMinRetractSpeed(int original) {
        return Math.max(original, 5);
    }
}
