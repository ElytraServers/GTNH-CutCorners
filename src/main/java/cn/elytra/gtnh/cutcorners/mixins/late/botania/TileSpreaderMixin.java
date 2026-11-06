package cn.elytra.gtnh.cutcorners.mixins.late.botania;

import cn.elytra.gtnh.cutcorners.CutCorners;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import vazkii.botania.common.block.tile.mana.TileSpreader;

@Mixin(value = TileSpreader.class, remap = false)
public abstract class TileSpreaderMixin {

    @ModifyExpressionValue(method = "pingback", at = @At(value = "CONSTANT", args = "intValue=20"))
    private int gtnhcc$updatePingbackTicks(int value) {
        return CutCorners.getStrategy().getBotaniaSpreaderPingbackTicks(value);
    }

}
