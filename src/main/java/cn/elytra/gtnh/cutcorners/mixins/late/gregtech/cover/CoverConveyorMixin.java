package cn.elytra.gtnh.cutcorners.mixins.late.gregtech.cover;

import cn.elytra.gtnh.cutcorners.config.NewConfig;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import gregtech.common.covers.CoverConveyor;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = CoverConveyor.class, remap = false)
public class CoverConveyorMixin {

    @ModifyReturnValue(method = "getMinimumTickRate", at = @At("RETURN"))
    private int cc$tickRate(int original) {
        if (NewConfig.maxTickRateConveyorCover) {
            return 1;
        }
        return original;
    }

    @WrapOperation(method = "doTransfer", at = @At(value = "FIELD", target = "Lgregtech/common/covers/CoverConveyor;itemsPerStack:I", opcode = Opcodes.GETFIELD))
    private int cc$batchSize(CoverConveyor instance, Operation<Integer> original) {
        if (NewConfig.maxBatchSizeConveyor) {
            return Integer.MAX_VALUE;
        }
        return original.call(instance);
    }

}
