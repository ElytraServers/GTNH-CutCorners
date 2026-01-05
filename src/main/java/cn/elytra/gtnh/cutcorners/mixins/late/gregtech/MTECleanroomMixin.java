package cn.elytra.gtnh.cutcorners.mixins.late.gregtech;

import cn.elytra.gtnh.cutcorners.CutCorners;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import gregtech.common.tileentities.machines.multi.MTECleanroom;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = MTECleanroom.class, remap = false)
public class MTECleanroomMixin {

    @WrapOperation(
        method = "checkProcessing", at = @At(
        value = "FIELD",
        target = "Lgregtech/common/tileentities/machines/multi/MTECleanroom;mEfficiencyIncrease:I",
        opcode = Opcodes.PUTFIELD))
    private void cc$modifyCleannessIncrement(MTECleanroom instance, int value, Operation<Void> original) {
        if (CutCorners.getStrategy().isImmediateMode()) {
            value = instance.getMaxEfficiency(null);
        }
        original.call(instance, value);
    }

}
