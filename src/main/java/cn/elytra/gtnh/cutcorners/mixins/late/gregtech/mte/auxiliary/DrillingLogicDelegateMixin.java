package cn.elytra.gtnh.cutcorners.mixins.late.gregtech.mte.auxiliary;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import gregtech.common.misc.DrillingLogicDelegate;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

@Mixin(value = DrillingLogicDelegate.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.GregTech))
public class DrillingLogicDelegateMixin {

    @Expression("?.?() / 5")
    @ModifyExpressionValue(method = "onPostTickRetract", at = @At("MIXINEXTRAS:EXPRESSION"))
    private int gtnhcc$modifyRetractSpeed(int original) {
        return CutCorners.getStrategy().isImmediateMode() ? 1 : original;
    }
}
