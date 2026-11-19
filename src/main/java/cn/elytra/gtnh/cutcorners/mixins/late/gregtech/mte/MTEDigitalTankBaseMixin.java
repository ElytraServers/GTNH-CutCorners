package cn.elytra.gtnh.cutcorners.mixins.late.gregtech.mte;

import cn.elytra.gtnh.cutcorners.CutCorners;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import gregtech.common.tileentities.storage.MTEDigitalTankBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = MTEDigitalTankBase.class, remap = false)
public class MTEDigitalTankBaseMixin {

    @ModifyExpressionValue(method = "onPostTick", at = @At(value = "CONSTANT", args = "intValue=20"))
    private int cc$outputInterval(int original) {
        return CutCorners.getStrategy().getQuantumTankOutputInterval(original);
    }

    @ModifyArg(method = "onPostTick", at = @At(value = "INVOKE", target = "Lgregtech/common/tileentities/storage/MTEDigitalTankBase;drain(IZ)Lnet/minecraftforge/fluids/FluidStack;", ordinal = 0))
    private int cc$outputAmount(int original) {
        return CutCorners.getStrategy().getQuantumTankOutputAmount(original);
    }

}
