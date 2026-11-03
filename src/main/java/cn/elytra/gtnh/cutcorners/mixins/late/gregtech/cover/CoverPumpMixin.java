package cn.elytra.gtnh.cutcorners.mixins.late.gregtech.cover;

import cn.elytra.gtnh.cutcorners.config.NewConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import gregtech.common.covers.CoverPump;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = CoverPump.class, remap = false)
public class CoverPumpMixin {

    /* pumps have always 1t interval between works, nothing to patch */

    @WrapOperation(method = "transferFluid", at = @At(value = "FIELD", target = "Lgregtech/common/covers/CoverPump;mTransferRate:I", opcode = Opcodes.GETFIELD))
    private int cc$batchSize(CoverPump instance, Operation<Integer> original) {
        if (NewConfig.maxBatchSizePump) {
            return Integer.MAX_VALUE;
        }
        return original.call(instance);
    }

}
