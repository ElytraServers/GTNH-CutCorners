package cn.elytra.gtnh.cutcorners.mixins.late.gregtech;

import gregtech.api.util.GTUtility;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import tectech.util.TTUtility;

@Mixin(targets = { "tectech.recipe.EyeOfHarmonyFrontend$EyeOfHarmonySpecialValueFormatter" }, remap = false)
@Restriction(
    // bug was fixed in #3861, and released in 5.09.51.97
    require = @Condition(value = "gregtech", versionPredicates = "metadata:[,5.09.51.97)"))
public class EyeOfHarmonyFrontendFixMixin {

    @Redirect(
        method = "format",
        at = @At(value = "INVOKE", target = "Ltectech/util/TTUtility;toExponentForm(J)Ljava/lang/String;"))
    private String toExponentForm_crashFix(long number) {
        if (number > 1000) {
            // this function will crash the game if the number is less than 1,000,
            // so we should use normal one instead if it happens.
            return TTUtility.toExponentForm(number);
        } else {
            return GTUtility.formatNumbers(number);
        }
    }

}
