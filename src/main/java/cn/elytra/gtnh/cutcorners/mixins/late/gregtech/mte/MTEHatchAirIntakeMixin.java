package cn.elytra.gtnh.cutcorners.mixins.late.gregtech.mte;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import cn.elytra.gtnh.cutcorners.CutCorners;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.MTEHatchAirIntake;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.MTEHatchAirIntakeAtmosphere;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.MTEHatchAirIntakeExtreme;

@Mixin(
    value = { MTEHatchAirIntake.class, MTEHatchAirIntakeExtreme.class, MTEHatchAirIntakeAtmosphere.class },
    remap = false)
public class MTEHatchAirIntakeMixin {

    @ModifyReturnValue(method = "getAmountOfFluidToGenerate", at = @At("RETURN"))
    private int cc$modifyAmount(int original) {
        return CutCorners.getStrategy().getAirIntakeAmount(original);
    }

}
