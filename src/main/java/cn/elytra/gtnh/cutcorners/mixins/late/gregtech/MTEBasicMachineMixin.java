package cn.elytra.gtnh.cutcorners.mixins.late.gregtech;

import cn.elytra.gtnh.cutcorners.CutCorners;
import gregtech.api.metatileentity.implementations.MTEBasicMachine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = MTEBasicMachine.class, remap = false)
public class MTEBasicMachineMixin {

    @ModifyConstant(method = "onPostTick", constant = @Constant(intValue = 1000))
    private int gtnhcc$modifyAutoOutputFluidAmount(int constant) {
        if (CutCorners.getStrategy().isImmediateMode()) {
            return Integer.MAX_VALUE;
        }
        return constant;
    }

}
