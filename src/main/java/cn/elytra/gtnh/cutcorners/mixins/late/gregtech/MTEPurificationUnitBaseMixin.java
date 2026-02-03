package cn.elytra.gtnh.cutcorners.mixins.late.gregtech;

import cn.elytra.gtnh.cutcorners.CutCorners;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import gregtech.common.tileentities.machines.multi.purification.MTEPurificationUnitBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = MTEPurificationUnitBase.class, remap = false)
public class MTEPurificationUnitBaseMixin {

    @WrapOperation(
        method = "endCycle",
        at = @At(
            value = "INVOKE",
            target = "Lgregtech/common/tileentities/machines/multi/purification/MTEPurificationUnitBase;calculateFinalSuccessChance()F"))
    private float gtnhcc$hookFinalSuccessChance(MTEPurificationUnitBase<?> instance, Operation<Float> original) {
        return CutCorners.getStrategy().getPurificationSuccessChance(instance, original.call(instance));
    }

}
