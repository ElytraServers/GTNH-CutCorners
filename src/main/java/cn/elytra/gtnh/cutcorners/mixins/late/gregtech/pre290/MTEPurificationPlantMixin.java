package cn.elytra.gtnh.cutcorners.mixins.late.gregtech.pre290;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import gregtech.common.tileentities.machines.multi.purification.MTEPurificationPlant;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

@Mixin(value = MTEPurificationPlant.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.GregTech))
public class MTEPurificationPlantMixin {

    @ModifyExpressionValue(
        method = "startCycle",
        at = @At(value = "CONSTANT", args = "intValue=" + MTEPurificationPlant.CYCLE_TIME_TICKS))
    private int gtnhcc$modifyCycleTime(int constant) {
        return CutCorners.getStrategy().getWaterPurificationCycleTime(constant);
    }

}
