package cn.elytra.gtnh.cutcorners.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.sugar.Local;

import cn.elytra.gtnh.cutcorners.core.WorldTickingManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

@SuppressWarnings("SpellCheckingInspection")
@Mixin(World.class)
public class WorldMixin {

    @Inject(
        method = "updateEntities",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/tileentity/TileEntity;updateEntity()V",
            shift = At.Shift.AFTER))
    private void cc$tickMore(CallbackInfo ci, @Local(name = "tileentity") TileEntity tile) {
        WorldTickingManager.onBlockPosTick(tile.xCoord, tile.yCoord, tile.zCoord, tile);
    }

}
