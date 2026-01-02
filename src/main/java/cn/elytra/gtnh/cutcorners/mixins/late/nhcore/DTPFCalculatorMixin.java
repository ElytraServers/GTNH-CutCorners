package cn.elytra.gtnh.cutcorners.mixins.late.nhcore;

import com.dreammaster.gthandler.DTPFCalculator;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = DTPFCalculator.class, remap = false)
public class DTPFCalculatorMixin {

    @WrapOperation(
        method = "determineEBFParams", at = @At(
        value = "FIELD", target = "Lcom/dreammaster/gthandler/DTPFCalculator;ebfDuration:J", opcode = Opcodes.PUTFIELD))
    private void clampEbfDuration(DTPFCalculator instance, long value, Operation<Void> original) {
        original.call(instance, Math.max(1, value));
    }

}
