package cn.elytra.gtnh.cutcorners.mixins.late.crops_nh;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import com.gtnewhorizon.cropsnh.tileentity.TileEntityCropSticks;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = TileEntityCropSticks.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.CropsNH))
public class TECropSticksMixin {

    @ModifyConstant(method = "updateEntity", constant = @Constant(intValue = TileEntityCropSticks.TICK_RATE), remap = true)
    private int cc$tickRate(int constant) {
        return CutCorners.getStrategy().getCropsNHStickTickRate(constant);
    }

    @ModifyExpressionValue(method = "doGrowth", at = @At(value = "FIELD", target = "Lcom/gtnewhorizon/cropsnh/handler/ConfigurationHandler;growthMultiplier:F", opcode = Opcodes.GETSTATIC))
    private float cc$replaceMultiplier(float original) {
        return CutCorners.getStrategy().getCropsNHMultiplier(original);
    }

    @ModifyExpressionValue(method = "onGrowthTick", at = @At(value = "FIELD", target = "Lcom/gtnewhorizon/cropsnh/handler/ConfigurationHandler;weedSpawnChance:I", opcode = Opcodes.GETSTATIC))
    private int cc$weedSpawnChanceRebalance(int original) {
        // we increase the "chance" number, so that the weed won't shit all over the farmland.
        // (for example, 256 (by default) / 32 (current) = 8),
        int speedUpRatio = TileEntityCropSticks.TICK_RATE / CutCorners.getStrategy().getCropsNHStickTickRate(TileEntityCropSticks.TICK_RATE);
        // in case of someone wants a slower world (ratio <= 1), we don't decrease the weed chance. LOL.
        return speedUpRatio > 1 ? original * speedUpRatio : original;
    }
}
