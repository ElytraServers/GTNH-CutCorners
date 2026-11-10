package cn.elytra.gtnh.cutcorners.mixins.late.thaumicbases;

import cn.elytra.gtnh.cutcorners.CutCorners;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import tb.common.block.BlockAshroom;

@Mixin(value = BlockAshroom.class, remap = false)
public abstract class AshroomMixin {

    @ModifyExpressionValue(method = "onBlockHarvested", at = @At(value = "CONSTANT", args = "intValue=1", ordinal = 1, remap = true))
    private int cc$aspectOrbValue(int original) {
        return CutCorners.getStrategy().getAshroomAspectOrbValue(original);
    }

    @Definition(id = "dropCount", local = @Local(type = int.class, name = "dropCount"))
    @Expression("dropCount")
    @ModifyExpressionValue(method = "onBlockHarvested", at = @At("MIXINEXTRAS:EXPRESSION"), remap = true)
    private int cc$dropCount(int original) {
        return CutCorners.getStrategy().getAshroomDropCount(original);
    }
}
