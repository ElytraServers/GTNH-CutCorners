package cn.elytra.gtnh.cutcorners.mixins.late.railcraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import cn.elytra.gtnh.cutcorners.util.ModConst;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import mods.railcraft.common.blocks.machine.alpha.TileCokeOven;

@Mixin(value = TileCokeOven.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.Railcraft))
public class TileCokeOvenMixin {

    @ModifyExpressionValue(method = "updateEntity", at = @At(value = "CONSTANT", args = "intValue=50"))
    private int gtnhcc$updateCookStepLength(int value) {
        return 1;
    }

}
