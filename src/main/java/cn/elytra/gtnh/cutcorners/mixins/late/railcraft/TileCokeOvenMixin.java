package cn.elytra.gtnh.cutcorners.mixins.late.railcraft;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import mods.railcraft.common.blocks.machine.alpha.TileCokeOven;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = TileCokeOven.class, remap = false)
public class TileCokeOvenMixin {

    @ModifyExpressionValue(method = "updateEntity", at = @At(value = "CONSTANT", args = "intValue=50"))
    private int gtnhcc$updateCookStepLength(int value) {
        return 1;
    }

}
