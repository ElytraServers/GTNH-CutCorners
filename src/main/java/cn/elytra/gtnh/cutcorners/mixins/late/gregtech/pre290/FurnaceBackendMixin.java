package cn.elytra.gtnh.cutcorners.mixins.late.gregtech.pre290;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import gregtech.api.recipe.maps.FurnaceBackend;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

@Mixin(value = FurnaceBackend.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.GregTech))
public class FurnaceBackendMixin {

    @ModifyExpressionValue(method = "overwriteFindRecipe", at = @At(value = "CONSTANT", args = "intValue=128"))
    private int gtnhcc$modifyDuration(int constant) {
        return CutCorners.getStrategy().getMaxFurnaceSmeltingTime(constant);
    }

}
