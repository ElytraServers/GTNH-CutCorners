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
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TileEntityCropSticks.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.CropsNH))
public class TECropSticksMixin {

    // a separated ticker used to consume the fertilizer and water
    @Unique
    private int cc$tickCount;
    // set to true every 256 ticks, to consume fertilizer and water
    @Unique
    private boolean cc$consumeThisTick;

    @ModifyConstant(method = "updateEntity", constant = @Constant(intValue = TileEntityCropSticks.TICK_RATE), remap = true)
    private int cc$tickRate(int constant) {
        return CutCorners.getStrategy().getCropsNHStickTickRate(constant);
    }

    @Inject(method = "updateEntity", at = @At("HEAD"), remap = true)
    private void cc$onUpdate(CallbackInfo ci) {
        if (++cc$tickCount % TileEntityCropSticks.TICK_RATE == 0) {
            cc$tickCount = 0;
            cc$consumeThisTick = true;
        }
    }

    @Inject(method = "updateEntity", at = @At("RETURN"), remap = true)
    private void cc$onUpdateEnd(CallbackInfo ci) {
        cc$consumeThisTick = false;
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

    @Definition(id = "fertilizerStorage", field = "Lcom/gtnewhorizon/cropsnh/tileentity/TileEntityCropSticks;fertilizerStorage:I")
    @Expression("this.fertilizerStorage > 0")
    @ModifyExpressionValue(method = "onGrowthTick", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean cc$shouldConsumeFertilizer(boolean original) {
        return original && cc$consumeThisTick;
    }

    @Definition(id = "waterStorage", field = "Lcom/gtnewhorizon/cropsnh/tileentity/TileEntityCropSticks;waterStorage:I")
    @Expression("this.waterStorage > 0")
    @ModifyExpressionValue(method = "onGrowthTick", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean cc$shouldConsumeWater(boolean original) {
        return original && cc$consumeThisTick;
    }
}
