package cn.elytra.gtnh.cutcorners.mixins.late.gregtech;

import cn.elytra.gtnh.cutcorners.CutCorners;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import gregtech.common.tileentities.machines.multi.purification.MTEPurificationPlant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = MTEPurificationPlant.class, remap = false)
public class MTEPurificationPlantMixin {

    @ModifyExpressionValue(method = "startCycle", at = @At(value = "CONSTANT", args = "intValue=" + MTEPurificationPlant.CYCLE_TIME_TICKS))
    private int gtnhcc$modifyCycleTime(int constant) {
        return CutCorners.getStrategy().getWaterPurificationCycleTime(constant);
    }

}
