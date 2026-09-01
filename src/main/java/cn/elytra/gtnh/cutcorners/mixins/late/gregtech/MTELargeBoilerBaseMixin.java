package cn.elytra.gtnh.cutcorners.mixins.late.gregtech;

import cn.elytra.gtnh.cutcorners.CutCorners;
import gregtech.common.tileentities.machines.multi.MTELargeBoilerBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = MTELargeBoilerBase.class, remap = false)
public abstract class MTELargeBoilerBaseMixin {

    @ModifyVariable(method = "setupBoilerRecipe", at = @At("HEAD"), argsOnly = true, name = "rawBurnTime")
    private int gtnhcc$modifyBurnTime(int rawBurnTime) {
        return CutCorners.getStrategy().getLargeBoilerFuelBurnTime(this, rawBurnTime);
    }
}
