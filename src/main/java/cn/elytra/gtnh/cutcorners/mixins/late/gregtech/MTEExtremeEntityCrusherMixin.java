package cn.elytra.gtnh.cutcorners.mixins.late.gregtech;

import cn.elytra.gtnh.cutcorners.CutCorners;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import kubatech.tileentity.gregtech.multiblock.MTEExtremeEntityCrusher;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = MTEExtremeEntityCrusher.class, remap = false)
public abstract class MTEExtremeEntityCrusherMixin {

    @WrapOperation(
        method = "checkProcessing", at = @At(
        value = "FIELD",
        target = "Lkubatech/tileentity/gregtech/multiblock/MTEExtremeEntityCrusher;mMaxProgresstime:I",
        opcode = Opcodes.PUTFIELD))
    private void gtnhcc$modifyTime(MTEExtremeEntityCrusher instance, int value, Operation<Void> original) {
        value = CutCorners.getStrategy().getMaxProgressTime(this, value);
        original.call(instance, value);
    }

    @ModifyReturnValue(method = "getOverclockTimeLimit", at = @At("RETURN"))
    private int gtnhcc$modifyOverclockLimit(int original) {
        return 1;
    }

}
