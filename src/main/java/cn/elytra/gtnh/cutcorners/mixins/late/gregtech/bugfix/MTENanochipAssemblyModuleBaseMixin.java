package cn.elytra.gtnh.cutcorners.mixins.late.gregtech.bugfix;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import cn.elytra.gtnh.cutcorners.util.ModConst;
import gregtech.common.tileentities.machines.multi.nanochip.MTENanochipAssemblyModuleBase;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

@Mixin(value = MTENanochipAssemblyModuleBase.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.GregTech))
public class MTENanochipAssemblyModuleBaseMixin {

    @ModifyArg(
        method = "transformRecipe",
        at = @At(value = "INVOKE", target = "Lgregtech/api/util/GTRecipe;setDuration(I)Lgregtech/api/util/GTRecipe;"))
    private int test(int aDuration) {
        // when the recipe is 1t, and there's duration discount, it will be 0 and blocks the module from running.
        return Math.max(1, aDuration);
    }
}
