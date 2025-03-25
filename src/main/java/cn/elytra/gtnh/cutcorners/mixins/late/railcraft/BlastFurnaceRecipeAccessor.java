package cn.elytra.gtnh.cutcorners.mixins.late.railcraft;

import mods.railcraft.common.util.crafting.BlastFurnaceCraftingManager;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = BlastFurnaceCraftingManager.BlastFurnaceRecipe.class, remap = false)
public interface BlastFurnaceRecipeAccessor {

    @Accessor("input")
    ItemStack get_input();

    @Accessor("input")
    void set_input(ItemStack value);

    @Accessor("matchDamage")
    boolean get_matchDamage();

    @Accessor("matchDamage")
    void set_matchDamage(boolean value);

    @Accessor("matchNBT")
    boolean get_matchNBT();

    @Accessor("matchNBT")
    void set_matchNBT(boolean value);

    @Accessor("cookTime")
    int get_cookTime();

    @Accessor("cookTime")
    void set_cookTime(int value);

    @Accessor("output")
    ItemStack get_output();

    @Accessor("output")
    void set_output(ItemStack value);

}
