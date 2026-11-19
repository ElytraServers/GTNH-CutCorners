package cn.elytra.gtnh.cutcorners.mixins.late.enderio;

import cn.elytra.gtnh.cutcorners.CutCorners;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import crazypants.enderio.conduit.liquid.EnderLiquidConduitNetwork;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = EnderLiquidConduitNetwork.class, remap = false)
public class EnderLiquidConduitNetworkMixin {

    @ModifyExpressionValue(
        method = "extractFrom",
        at = @At(value = "INVOKE", target = "Lcrazypants/enderio/conduit/liquid/AbstractEnderLiquidConduit$Type;getMaxExtractPerTick()I"))
    private int cc$maxExtract(int original) {
        return CutCorners.getStrategy().getEnderIoLiquidConduitMaxExtractAmount(original);
    }

    @ModifyExpressionValue(
        method = "fillFrom(Lcrazypants/enderio/conduit/liquid/EnderLiquidConduitNetwork$NetworkTank;Lnet/minecraftforge/fluids/FluidStack;Z)I",
        at = @At(value = "INVOKE", target = "Lcrazypants/enderio/conduit/liquid/AbstractEnderLiquidConduit$Type;getMaxIoPerTick()I"))
    private int cc$maxInsert(int original) {
        return CutCorners.getStrategy().getEnderIoLiquidConduitMaxInsertAmount(original);
    }

}
