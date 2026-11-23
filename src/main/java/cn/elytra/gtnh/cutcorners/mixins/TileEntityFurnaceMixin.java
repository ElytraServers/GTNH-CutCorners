package cn.elytra.gtnh.cutcorners.mixins;

import cn.elytra.gtnh.cutcorners.CutCorners;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.tileentity.TileEntityFurnace;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(TileEntityFurnace.class)
public abstract class TileEntityFurnaceMixin {

    @ModifyExpressionValue(method = "updateEntity", at = @At(value = "CONSTANT", args = "intValue=200"))
    private int gtnhcc$getMaxSmeltingTime(int value) {
        return CutCorners.getStrategy().getMaxFurnaceSmeltingTime(value);
    }
}
