package cn.elytra.gtnh.cutcorners.mixins.late.forestry;

import cn.elytra.gtnh.cutcorners.config.NewConfig;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import forestry.apiculture.BeekeepingLogic;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Replaces Forestry's hardcoded "one unit of throttle per work tick" with the configured value, so the throttle
 * counter reaches its 550 limit after ceil(550 / increment) ticks instead of 550 ticks.
 * <p>
 * 550 is also the cycle length hardcoded into the GregTech industrial apiary, see
 * {@link cn.elytra.gtnh.cutcorners.mixins.late.gregtech.MTEIndustrialApiaryMixin}.
 */
@Mixin(value = BeekeepingLogic.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.Forestry))
public class BeekeepingLogicMixin {

    @ModifyConstant(method = "queenWorkTick", constant = @Constant(intValue = 1))
    private int gtnhcc$queenWorkCycleThrottleIncrement(int constant) {
        return NewConfig.getQueenWorkCycleThrottleIncrement();
    }
}
