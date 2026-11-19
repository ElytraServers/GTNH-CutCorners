package cn.elytra.gtnh.cutcorners.mixins.late.gregtech.mte;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import cn.elytra.gtnh.cutcorners.CutCorners;
import gregtech.common.tileentities.machines.multi.MTEResearchCompleter;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.tiles.TileNode;

@Mixin(value = MTEResearchCompleter.class, remap = false)
public class MTEResearchCompleterMixin {

    // Skip the node vis base drain so the Research Completer never depletes the node it feeds from.
    @WrapOperation(
        method = "onRunningTick",
        at = @At(
            value = "INVOKE",
            target = "Lthaumcraft/common/tiles/TileNode;setNodeVisBase(Lthaumcraft/api/aspects/Aspect;S)V"))
    private void cc$onRunningTick(TileNode instance, Aspect aspect, short nodeVisBase, Operation<Void> original) {
        if (CutCorners.getStrategy().isDisableResearchCompleterConsumingNodeCapacity()) {
            // prevent from Research completer truncate the vis capacity of the node in the structure.
            return;
        }
        original.call(instance, aspect, nodeVisBase);
    }
}
