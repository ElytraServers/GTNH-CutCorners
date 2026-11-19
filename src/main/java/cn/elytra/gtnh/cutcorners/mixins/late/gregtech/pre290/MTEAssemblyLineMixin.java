package cn.elytra.gtnh.cutcorners.mixins.late.gregtech.pre290;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import gregtech.common.tileentities.machines.multi.MTEAssemblyLine;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = MTEAssemblyLine.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.GregTech))
public class MTEAssemblyLineMixin {

    @WrapOperation(
        method = "checkProcessing", at = @At(
        value = "FIELD",
        target = "Lgregtech/common/tileentities/machines/multi/MTEAssemblyLine;mMaxProgresstime:I",
        opcode = Opcodes.PUTFIELD))
    private void gtnhcc$modifyCookingTime(MTEAssemblyLine instance, int value, Operation<Void> original) {
        value = CutCorners.getStrategy().getMaxProgressTime(instance, value);
        original.call(instance, value);
    }

}
