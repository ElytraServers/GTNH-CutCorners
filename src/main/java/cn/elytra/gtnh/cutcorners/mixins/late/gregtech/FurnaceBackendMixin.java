package cn.elytra.gtnh.cutcorners.mixins.late.gregtech;

import cn.elytra.gtnh.cutcorners.CutCorners;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import gregtech.api.recipe.maps.FurnaceBackend;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = FurnaceBackend.class, remap = false)
public class FurnaceBackendMixin {

    @ModifyExpressionValue(method = "overwriteFindRecipe", at = @At(value = "CONSTANT", args = "intValue=128"))
    private int gtnhcc$modifyDuration(int constant) {
        return CutCorners.getStrategy().getMaxFurnaceSmeltingTime(constant);
    }

}
