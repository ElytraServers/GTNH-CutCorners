package cn.elytra.gtnh.cutcorners.mixins.late.gregtech;

import cn.elytra.gtnh.cutcorners.CutCorners;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import gregtech.common.tileentities.machines.basic.MTEMiner;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = MTEMiner.class, remap = false)
public class GT_MetaTileEntity_MinerMixin {

    /// Shortens the mining cycle by scaling {@code mSpeed} where it is read, rather than writing it back into the
    /// field.
    ///
    /// Writing the scaled value back every tick compounds it: with the default 0.5 multiplier an IV miner decays
    /// 20 -> 10 -> 5 -> 2 -> 1 within four ticks. Once {@code mSpeed} drops below 5, the integer division in
    /// {@code DrillingLogicDelegate#onPostTickRetract} turns {@code getMachineSpeed() / 5} into 0, and then
    /// {@code aTick % 0} throws {@link ArithmeticException}.
    @ModifyExpressionValue(
        method = "onPostTick",
        at = @At(
            value = "FIELD",
            target = "Lgregtech/common/tileentities/machines/basic/MTEMiner;mSpeed:I",
            opcode = Opcodes.GETFIELD))
    private int gtnhcc$modifyMiningSpeed(int original) {
        return CutCorners.getStrategy().getMaxProgressTime(this, original);
    }
}
