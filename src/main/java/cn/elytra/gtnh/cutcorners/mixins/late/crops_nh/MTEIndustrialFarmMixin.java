package cn.elytra.gtnh.cutcorners.mixins.late.crops_nh;

import cn.elytra.gtnh.cutcorners.config.NewConfig;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import com.gtnewhorizon.cropsnh.tileentity.multi.MTEIndustrialFarm;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Scales the industrial farm's per-cycle growth progress to the same tick rate used by
 * {@link TECropSticksMixin}, keeping the rest of the vanilla formula untouched.
 * <p>
 * {@code MTEIndustrialFarm.getGrowthProgressPerCycle} ends with
 *
 * <pre>
 * return 1.0 / ceil(growthDuration / unscaledSpeed) * CYCLE_TICK_RATE_SCALAR * getGrowthSpeedMultiplier();
 * </pre>
 *
 * where {@code CYCLE_TICK_RATE_SCALAR = CYCLE_DURATION / TileEntityCropSticks.TICK_RATE = 100 / 256 = 0.390625} is a
 * compile-time constant inlined by javac into a single {@code ldc2_w 0.390625d}. Rebasing it to
 * {@code 100 / tickRate} keeps the per-cycle drops proportional to the crop stick speedup. When the crop speedups are
 * disabled the getter returns the vanilla 256, making the scalar bit-for-bit the original {@code 0.390625d}.
 */
@Mixin(value = MTEIndustrialFarm.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.CropsNH))
public class MTEIndustrialFarmMixin {

    @ModifyConstant(method = "getGrowthProgressPerCycle", constant = @Constant(doubleValue = 0.390625d))
    private double gtnhcc$cycleTickRateScalar(double constant) {
        return 100.0d / NewConfig.getCropCycleTickRate();
    }
}
