package cn.elytra.gtnh.cutcorners.mixins.late.binnie;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import binnie.genetics.machine.acclimatiser.AcclimatiserComponentLogic;
import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

@Mixin(value = AcclimatiserComponentLogic.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.Binnie_Genetics))
public class AcclimatiserComponentLogicMixin {

    @WrapOperation(method = "onTickTask", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I"))
    private static int cc$ticking(Random instance, int bound, Operation<Integer> original) {
        if (CutCorners.getStrategy().isBinnieAcclimatiserAccelerated()) {
            return 0;
        }
        return original.call(instance, bound);
    }

}
