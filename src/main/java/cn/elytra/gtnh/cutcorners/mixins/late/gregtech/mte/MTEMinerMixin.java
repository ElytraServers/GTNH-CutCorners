package cn.elytra.gtnh.cutcorners.mixins.late.gregtech.mte;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import gregtech.common.tileentities.machines.basic.MTEMiner;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

@Mixin(value = MTEMiner.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.GregTech))
public class MTEMinerMixin {

    @ModifyExpressionValue(
        method = "onPostTick",
        at = @At(
            value = "FIELD",
            target = "Lgregtech/common/tileentities/machines/basic/MTEMiner;mSpeed:I",
            opcode = Opcodes.GETFIELD))
    private int gtnhcc$modifyMiningSpeed(int original) {
        return CutCorners.getStrategy().getPumpAndMinerDuration(original);
    }
}
