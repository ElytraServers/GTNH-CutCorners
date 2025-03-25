package cn.elytra.gtnh.cutcorners.mixins.late.thaumcraft;

import cn.elytra.gtnh.cutcorners.CutCorners;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import thaumcraft.common.tiles.TileAlchemyFurnace;

@Mixin(value = TileAlchemyFurnace.class, remap = false)
public abstract class TileAlchemyFurnaceMixin {

    @Redirect(
        method = "canSmelt",
        at = @At(
            value = "FIELD",
            target = "Lthaumcraft/common/tiles/TileAlchemyFurnace;smeltTime:I",
            opcode = Opcodes.PUTFIELD))
    private void gtnhcc$overwriteSmeltingTime(TileAlchemyFurnace furnace, int smeltTime) {
        // furnace.smeltTime = ThaumcraftHandler.handle(furnace, smeltTime);
        furnace.smeltTime = CutCorners.getStrategy().getThaumcraftFurnaceSmeltingTime(smeltTime);
    }
}
