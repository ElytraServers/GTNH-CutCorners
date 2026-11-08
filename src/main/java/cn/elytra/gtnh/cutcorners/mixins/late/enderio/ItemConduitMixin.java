package cn.elytra.gtnh.cutcorners.mixins.late.enderio;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import crazypants.enderio.conduit.item.ItemConduit;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = ItemConduit.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.EnderIO))
public class ItemConduitMixin {

    @ModifyReturnValue(method = "getMaximumExtracted", at = @At("RETURN"))
    private int cc$maxAmount(int original) {
        return CutCorners.getStrategy().getEnderIoItemConduitMaxExtractAmount(original);
    }

}
