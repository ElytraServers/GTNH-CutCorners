package cn.elytra.gtnh.cutcorners.mixins.late.gregtech;

import cn.elytra.gtnh.cutcorners.CutCorners;
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
        return 5;
    }
}
