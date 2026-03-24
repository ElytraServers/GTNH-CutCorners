package cn.elytra.gtnh.cutcorners.mixins.late.gregtech;

import cn.elytra.gtnh.cutcorners.CutCorners;
import gregtech.common.tileentities.machines.multi.purification.MTEPurificationPlant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = MTEPurificationPlant.class, remap = false)
public class MTEPurificationPlantMixin {

    @ModifyConstant(method = "startCycle", constant = @Constant(intValue = 2400))
    private int gtnhcc$modifyCycleTime(int constant) {
        return CutCorners.getStrategy().getWaterPurificationCycleTime(constant);
    }

}
