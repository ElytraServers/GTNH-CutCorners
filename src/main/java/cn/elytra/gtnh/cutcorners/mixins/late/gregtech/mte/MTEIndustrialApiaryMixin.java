package cn.elytra.gtnh.cutcorners.mixins.late.gregtech.mte;

import cn.elytra.gtnh.cutcorners.CutCorners;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import gregtech.common.tileentities.machines.basic.MTEIndustrialApiary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = MTEIndustrialApiary.class, remap = false)
public class MTEIndustrialApiaryMixin {

    @ModifyExpressionValue(method = "checkRecipe", at = @At(value = "CONSTANT", args = "floatValue=" + MTEIndustrialApiary.beeCycleLength))
    private float cc$beeCycle(float constant) {
        // constant is inlined as float, so we must explicitly convert them.
        return (float) CutCorners.getStrategy().getForestryQueenInterval((int) constant);
    }

    @Definition(id = "mMaxProgresstime", field = "Lgregtech/common/tileentities/machines/basic/MTEIndustrialApiary;mMaxProgresstime:I")
    @Expression("this.mMaxProgresstime / 100")
    @ModifyExpressionValue(method = "checkRecipe", at = @At("MIXINEXTRAS:EXPRESSION"))
    private int cc$divZeroFix(int original) {
        // in case of a small "mMaxProgressTime" (= cycle * cycleTime from above injection),
        // this value will be zero, and will cause divided-by-zero error.
        // flooring this to 1 will be all good.
        return Math.max(1, original);
    }

}
