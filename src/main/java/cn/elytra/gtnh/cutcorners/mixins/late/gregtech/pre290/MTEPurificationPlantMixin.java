package cn.elytra.gtnh.cutcorners.mixins.late.gregtech.pre290;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import gregtech.common.tileentities.machines.multi.purification.MTEPurificationPlant;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = MTEPurificationPlant.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.GregTech))
public class MTEPurificationPlantMixin {

    @ModifyConstant(method = "startCycle", constant = @Constant(intValue = 2400))
    private int gtnhcc$modifyCycleTime(int constant) {
        return CutCorners.getStrategy().getWaterPurificationCycleTime(constant);
    }

}
