package cn.elytra.gtnh.cutcorners.mixins.late.gregtech;

import cn.elytra.gtnh.cutcorners.CutCorners;
import gregtech.api.recipe.maps.FurnaceBackend;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = FurnaceBackend.class, remap = false)
public class FurnaceBackendMixin {

    @ModifyConstant(method = "overwriteFindRecipe", constant = @Constant(intValue = 128))
    private int gtnhcc$modifyDuration(int constant) {
        return CutCorners.getStrategy().getMaxFurnaceSmeltingTime(constant);
    }

}
