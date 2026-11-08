package cn.elytra.gtnh.cutcorners.mixins.late.botania;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import vazkii.botania.common.block.tile.mana.TileSpreader;

@Mixin(value = TileSpreader.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.Botania))
public abstract class TileSpreaderMixin {

    @ModifyConstant(method = "pingback", constant = @Constant(intValue = 20 /* TICKS_ALLOWED_WITHOUT_PINGBACK */))
    private int gtnhcc$updatePingbackTicks(int value) {
        return CutCorners.getStrategy().getBotaniaSpreaderPingbackTicks(value);
    }

}
