package cn.elytra.gtnh.cutcorners.mixins.late.crops_nh;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import com.gtnewhorizon.cropsnh.tileentity.TileEntityCropSticks;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import static com.gtnewhorizon.cropsnh.tileentity.TileEntityCropSticks.TICK_RATE;

@Mixin(value = TileEntityCropSticks.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.CropsNH))
public class TECropSticksMixin {

    @Unique
    private static int cc$getTickRate() {
        return CutCorners.getStrategy().getCropsNHStickTickRate(TICK_RATE);
    }

    @ModifyExpressionValue(method = "updateEntity", at = @At(value = "CONSTANT", args = "intValue=" + TICK_RATE), remap = true)
    private int cc$tickRate(int constant) {
        assert constant == TICK_RATE;
        return cc$getTickRate();
    }

    @ModifyExpressionValue(method = "doGrowth", at = @At(value = "FIELD", target = "Lcom/gtnewhorizon/cropsnh/handler/ConfigurationHandler;growthMultiplier:F", opcode = Opcodes.GETSTATIC))
    private float cc$replaceMultiplier(float original) {
        return CutCorners.getStrategy().getCropsNHMultiplier(original);
    }

    @Unique
    private static int cc$getSpeedUpRatio() {
        return Math.max(1, TICK_RATE / cc$getTickRate());
    }

    @ModifyExpressionValue(method = "onGrowthTick", at = @At(value = "FIELD", target = "Lcom/gtnewhorizon/cropsnh/handler/ConfigurationHandler;weedSpawnChance:I", opcode = Opcodes.GETSTATIC))
    private int cc$weedSpawnChanceRebalance(int original) {
        // we increase the "chance" number, so that the weed won't shit all over the farmland.
        // (for example, 256 (by default) / 32 (current) = 8),
        int ratio = cc$getSpeedUpRatio();
        // in case of someone wants a slower world (ratio <= 1), we don't decrease the weed chance. LOL.
        return ratio > 1 ? original * ratio : original;
    }

    @ModifyExpressionValue(method = "onGrowthTick", at = @At(value = "FIELD", target = "Lcom/gtnewhorizon/cropsnh/handler/ConfigurationHandler;weedSpreadChance:I", opcode = Opcodes.GETSTATIC))
    private int cc$weedSpreadChanceRebalance(int original) {
        int ratio = cc$getSpeedUpRatio();
        return ratio > 1 ? original * ratio : original;
    }

    // the counter of fertilizer and water being consumed.
    // the real consumption will take place once this count reaches the speed-up ratio (= 256 / modified rate).
    // for example, given 3t/op, the speed-up ratio is 256 / 3 = 85,
    // so the real duration is 3 * 85 = 255t (near 256).
    // but anyway, choose a better number will result in a true 256t/consumption, like 1, 2, 4, 8.
    @Unique
    private int cc$fertilizerUseCount, cc$waterUseCount;

    @Definition(id = "fertilizerStorage", field = "Lcom/gtnewhorizon/cropsnh/tileentity/TileEntityCropSticks;fertilizerStorage:I")
    @Expression("this.fertilizerStorage > 0")
    @ModifyExpressionValue(method = "onGrowthTick", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean cc$shouldConsumeFertilizer(boolean original) {
        cc$fertilizerUseCount = ++cc$fertilizerUseCount % cc$getSpeedUpRatio();
        return original && cc$fertilizerUseCount == 0;
    }

    @Definition(id = "waterStorage", field = "Lcom/gtnewhorizon/cropsnh/tileentity/TileEntityCropSticks;waterStorage:I")
    @Expression("this.waterStorage > 0")
    @ModifyExpressionValue(method = "onGrowthTick", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean cc$shouldConsumeWater(boolean original) {
        cc$waterUseCount = ++cc$waterUseCount % cc$getSpeedUpRatio();
        return original && cc$waterUseCount == 0;
    }
}
