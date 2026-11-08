package cn.elytra.gtnh.cutcorners.mixins.late.gtnhintergalactic;

import cn.elytra.gtnh.cutcorners.util.ModConst;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import gtnhintergalactic.tile.multi.elevatormodules.TileEntityModuleMiner;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;

@Mixin(value = TileEntityModuleMiner.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.Intergalactic))
public abstract class TileEntityModuleMinerMixin {

    @ModifyReturnValue(method = "getRecipeTime", at = @At("RETURN"))
    private int fixZeroRecipeTimeBug(int original) {
        return Math.max(original, 1);
    }
}
