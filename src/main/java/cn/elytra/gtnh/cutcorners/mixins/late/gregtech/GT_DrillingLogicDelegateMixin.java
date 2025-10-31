package cn.elytra.gtnh.cutcorners.mixins.late.gregtech;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import gregtech.common.misc.DrillingLogicDelegate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = DrillingLogicDelegate.class, remap = false)
public class GT_DrillingLogicDelegateMixin {

    @ModifyExpressionValue(
        method = "onPostTickRetract",
        at = @At(value = "INVOKE", target = "Lgregtech/common/misc/IDrillingLogicDelegateOwner;getMachineSpeed()I"))
    private int gtnhcc$ensureMinRetractSpeed(int original) {
        return Math.max(original, 5);
    }
}
