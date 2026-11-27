package cn.elytra.gtnh.cutcorners.mixins.late.binnie;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import binnie.genetics.machine.acclimatiser.ToleranceSystem;
import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

@Mixin(value = ToleranceSystem.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.Binnie_Genetics))
public class ToleranceSystemMixin {

    @ModifyExpressionValue(method = "alter", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextFloat()F"))
    private float cc$bypassFailure(float original) {
        // Acclimatiser will expand the tolerance of the active chromo,
        // like Up 2 (active) and Up 1 (inactive), the new tolerance value will always be Up 3 (Up 2 + 1).
        // However, it may select the inactive chromo, and result in an unchanged instance when the inactive chromo is
        // higher.
        // E.g., Up 1 (active) and Up 2 (inactive), the new tolerance will be Up 2 (Up 1 + 1) from the active chromo,
        // and in this case, if it selects the inactive chromo, which is already Up 2, there's nothing changed.
        // But I'll consider this as success, because I don't want to break the default behavior.
        if (CutCorners.getStrategy().isBinnieAcclimatiserAlwaysSuccess()) {
            return 0F;
        }
        return original;
    }

}
