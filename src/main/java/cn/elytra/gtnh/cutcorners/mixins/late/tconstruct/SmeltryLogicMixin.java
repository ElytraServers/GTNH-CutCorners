package cn.elytra.gtnh.cutcorners.mixins.late.tconstruct;

import cn.elytra.gtnh.cutcorners.CutCorners;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import tconstruct.smeltery.logic.SmelteryLogic;

@Mixin(value = SmelteryLogic.class, remap = false)
public class SmeltryLogicMixin {

    @WrapOperation(
        method = "updateTemperatures", at = @At(
        value = "INVOKE",
        target = "Ltconstruct/library/crafting/Smeltery;getLiquifyTemperature(Lnet/minecraft/item/ItemStack;)Ljava/lang/Integer;"))
    private Integer gtnhcc$tconMoltenTemperature(ItemStack item, Operation<Integer> original) {
        Integer value = original.call(item);
        // if value is 20, it means there's no recipe for the item, so we return it as-is.
        if (value == 20) return value;
        // the base line is 20 (multiplied by 10 in the arrays), which ingredients with lower molten point won't melt.
        value = Math.max(1, CutCorners.getStrategy().getMaxProgressTime(this, value - 20)) + 20;
        return value;
    }

}
