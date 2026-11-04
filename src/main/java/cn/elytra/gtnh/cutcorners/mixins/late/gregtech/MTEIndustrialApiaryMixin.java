package cn.elytra.gtnh.cutcorners.mixins.late.gregtech;

import cn.elytra.gtnh.cutcorners.config.NewConfig;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import gregtech.common.tileentities.machines.basic.MTEIndustrialApiary;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Speeds up the GregTech industrial apiary's production cycle.
 * <p>
 * The vanilla code computes its cycle along these lines:
 *
 * <pre>
 * this.usedBeeLife = beeLifespan * 550.0f;
 * this.mMaxProgresstime = (int) usedBeeLife;
 * int minTime = mMaxProgresstime / 100;
 * int speed = 1 &lt;&lt; mSpeed;
 * mMaxProgresstime = mMaxProgresstime / Math.min(speed, minTime);
 * </pre>
 * <p>
 * The base constant is divided by the configured throttle, shortening the cycle. The {@code / 100} is turned into
 * {@code / 1} as well: that division feeds {@code Math.min}, and once the cycle drops below 100 ticks it evaluates to 0
 * and the machine throws an {@code ArithmeticException} on every recipe start. Replacing it removes both the crash and
 * GregTech's built-in 100 tick floor.
 * <p>
 * When the bee speedups are disabled in the config, both constants are restored to their vanilla values.
 */
@Mixin(value = MTEIndustrialApiary.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.GregTech))
public class MTEIndustrialApiaryMixin {

    @ModifyConstant(method = "checkRecipe", constant = @Constant(floatValue = 550.0f))
    private float gtnhcc$getBeeCycleLength(float constant) {
        return 550.0f / NewConfig.getQueenWorkCycleThrottleIncrement();
    }

    @ModifyConstant(method = "checkRecipe", constant = @Constant(intValue = 100, ordinal = 0))
    private int gtnhcc$removeMinimumCycleFloor(int constant) {
        return NewConfig.isBeeSpeedupEnabled() ? 1 : 100;
    }
}
