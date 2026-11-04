package cn.elytra.gtnh.cutcorners.mixins.late.crops_nh;

import cn.elytra.gtnh.cutcorners.config.NewConfig;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import com.gtnewhorizon.cropsnh.tileentity.TileEntityCropSticks;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = TileEntityCropSticks.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.CropsNH))
public class TECropSticksMixin {

    @ModifyConstant(method = "updateEntity", constant = @Constant(intValue = 256))
    private int cc$tickRate(int constant) {
        if (NewConfig.tickRateCropsNH > 0) {
            return NewConfig.tickRateCropsNH;
        }
        return constant;
    }
}
