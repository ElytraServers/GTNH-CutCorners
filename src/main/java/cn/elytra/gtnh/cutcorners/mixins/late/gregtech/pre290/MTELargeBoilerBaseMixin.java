package cn.elytra.gtnh.cutcorners.mixins.late.gregtech.pre290;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import gregtech.common.tileentities.machines.multi.MTELargeBoilerBase;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = MTELargeBoilerBase.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.GregTech))
public abstract class MTELargeBoilerBaseMixin {

    @ModifyVariable(method = "setupBoilerRecipe", at = @At("HEAD"), argsOnly = true, name = "rawBurnTime")
    private int gtnhcc$modifyBurnTime(int rawBurnTime) {
        return CutCorners.getStrategy().getLargeBoilerFuelBurnTime(this, rawBurnTime);
    }
}
