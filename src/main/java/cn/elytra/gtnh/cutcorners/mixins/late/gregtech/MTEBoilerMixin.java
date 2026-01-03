package cn.elytra.gtnh.cutcorners.mixins.late.gregtech;

import cn.elytra.gtnh.cutcorners.CutCorners;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import gregtech.common.tileentities.boilers.MTEBoiler;
import gregtech.common.tileentities.boilers.MTEBoilerSolar;
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
            if(instance instanceof MTEBoilerSolar && instance.mFluid.amount < 1000) {
                // don't heat up solar boilers immediately if it doesn't have sufficient water.
                return 0;
            }
            return getMaxTemperature();
        }
        return original.call(instance);
    }

}
