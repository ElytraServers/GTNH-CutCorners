package cn.elytra.gtnh.cutcorners.mixins.late.tconstruct;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import tconstruct.smeltery.logic.SmelteryLogic;

@Mixin(value = SmelteryLogic.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.TinkersConstruct))
public class SmelteryLogicMixin {

    @WrapOperation(method = "heatItems", at = @At(value = "INVOKE", target = "Ltconstruct/smeltery/logic/SmelteryLogic;getInternalTemperature()I"))
    private int test(SmelteryLogic instance, Operation<Integer> original) {
        if (CutCorners.getStrategy().isImmediateMode()) {
            // the value will be "*10" later the code, so we need to ensure not to overflow.
            return Integer.MAX_VALUE / 10;
        }
        return original.call(instance);
    }
}
