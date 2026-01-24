package cn.elytra.gtnh.cutcorners.mixins.late.gregtech;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.internal.GTPPTickableItemHook;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import gtPlusPlus.core.tileentities.general.TileEntityDecayablesChest;
import org.spongepowered.asm.mixin.Mixin;

@SuppressWarnings("CommentedOutCode")
@Mixin(value = TileEntityDecayablesChest.class, remap = false)
public abstract class TileEntityDecayablesChestMixin {

    @WrapMethod(method = "updateEntity", remap = true)
    private void gtnhcc$inject(Operation<Void> original) {
        if (CutCorners.getStrategy().isImmediateMode()) {
            // when in immediate mode, we set the context to "max tick = 1".
            // so that the item will decay immediately.
            try {
                GTPPTickableItemHook.setShouldOverrideMaxTickTime(true);
                original.call();
            } finally {
                GTPPTickableItemHook.setShouldOverrideMaxTickTime(false);
            }
        } else {
            original.call();
        }
    }

    /*
    @Shadow
    @Final
    private InventoryDecayablesChest inventoryContents;

    @Shadow
    protected abstract int updateSlots();

    @WrapOperation(
        method = "updateEntity",
        at = @At(
            value = "INVOKE",
            target = "LgtPlusPlus/core/tileentities/general/TileEntityDecayablesChest;tryUpdateDecayable(LgtPlusPlus/core/item/materials/DustDecayable;Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;)V"),
        remap = true)
    private void gtnhcc$replaceDecayableCall(TileEntityDecayablesChest instance, DustDecayable replacement,
        ItemStack stack, World world, Operation<Void> original) {
        gtnhcc$tryUpdateDecayable(replacement, stack, world);
    }

    @Unique
    private void gtnhcc$tryUpdateDecayable(DustDecayable dust, ItemStack stack, World world) {
        // what's the fuck is this? GTPP??
        if (world == null || stack == null) return;
        if (world.isRemote) return;

        // dust.isTicking(): true if the item is decaying (progressing)
        for (int i = 0; i < 20 && dust.isTicking(world, stack); i++) {
            // tick the item, and we'll check if it's decayed in the next loop
            dust.tickItemTag(world, stack);
        }

        // if fully decayed, replace it with the decayed result
        if (!dust.isTicking(world, stack)) {
            ItemStack decayedResultStack = dust.getDecayResult();
            decayedResultStack.stackSize = 1;
            for (int i = 0; i < this.inventoryContents.getSizeInventory(); i++) {
                if (this.inventoryContents.getStackInSlot(i) == stack) {
                    this.inventoryContents.setInventorySlotContents(i, decayedResultStack.copy());
                }
            }

            updateSlots();
            this.inventoryContents.markDirty();
        }
    }
     */

}
