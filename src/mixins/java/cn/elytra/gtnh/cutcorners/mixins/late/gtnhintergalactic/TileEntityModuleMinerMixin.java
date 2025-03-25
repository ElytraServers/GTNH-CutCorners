package cn.elytra.gtnh.cutcorners.mixins.late.gtnhintergalactic;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.gtnewhorizons.gtnhintergalactic.tile.multi.elevatormodules.TileEntityModuleMiner;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;

@Mixin(value = TileEntityModuleMiner.class, remap = false)
public abstract class TileEntityModuleMinerMixin {

    @ModifyReturnValue(method = "getRecipeTime", at = @At("RETURN"))
    private int fixZeroRecipeTimeBug(int original) {
        return Math.max(original, 1);
    }
}
