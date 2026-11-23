package cn.elytra.gtnh.cutcorners.mixins.late.botania;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import vazkii.botania.common.block.tile.mana.TileSpreader;

@Mixin(value = TileSpreader.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.Botania))
public abstract class TileSpreaderMixin {

    @ModifyExpressionValue(method = "pingback", at = @At(value = "CONSTANT", args = "intValue=20"))
    private int gtnhcc$updatePingbackTicks(int value) {
        return CutCorners.getStrategy().getBotaniaSpreaderPingbackTicks(value);
    }

}
