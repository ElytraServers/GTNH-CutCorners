package cn.elytra.gtnh.cutcorners.mixins.late.gregtech;

import cn.elytra.gtnh.cutcorners.CutCorners;
import gregtech.common.tileentities.machines.multi.MTELargeBoilerBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = MTELargeBoilerBase.class, remap = false)
public abstract class MTELargeBoilerBaseMixin {

    @ModifyArg(
        method = "checkProcessing",
        at = @At(
            value = "INVOKE",
            target = "Lgregtech/common/tileentities/machines/multi/MTELargeBoilerBase;setupBoilerRecipe(IIZ)V",
            ordinal = 0),
        index = 0)
    private int gtnhcc$modifyLiquidFuelBurnTime(int burnTime) {
        return CutCorners.getStrategy().getLargeBoilerFuelBurnTime(this, burnTime);
    }

    @ModifyArg(
        method = "checkProcessing",
        at = @At(
            value = "INVOKE",
            target = "Lgregtech/common/tileentities/machines/multi/MTELargeBoilerBase;setupBoilerRecipe(IIZ)V",
            ordinal = 1),
        index = 0)
    private int gtnhcc$modifySolidFuelBurnTime(int burnTime) {
        return CutCorners.getStrategy().getLargeBoilerFuelBurnTime(this, burnTime);
    }
}
