package cn.elytra.gtnh.cutcorners.mixins.late.gregtech;

import cn.elytra.gtnh.cutcorners.CutCorners;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import gregtech.common.tileentities.boilers.MTEBoiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = MTEBoiler.class, remap = false)
public abstract class MTEBoilerMixin {

    @Shadow
    protected abstract int getMaxTemperature();

    @WrapOperation(method = "calculateHeatUp", at = @At(value = "INVOKE", target = "Lgregtech/common/tileentities/boilers/MTEBoiler;getHeatUpAmount()I"))
    private int gtnhcc$modifyHeatUp(MTEBoiler instance, Operation<Integer> original) {
        if (CutCorners.getStrategy().isImmediateMode()) {
            return getMaxTemperature();
        }
        return original.call(instance);
    }

}
