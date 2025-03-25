package cn.elytra.gtnh.cutcorners.mixins.late.railcraft;

import mods.railcraft.common.util.crafting.CokeOvenCraftingManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = CokeOvenCraftingManager.CokeOvenRecipe.class, remap = false)
public interface CokeOvenRecipeAccessor {

    @Accessor("input")
    ItemStack get_input();

    @Mutable
    @Accessor("input")
    void set_input(ItemStack value);

    @Accessor("matchDamage")
    boolean get_matchDamage();

    @Mutable
    @Accessor("matchDamage")
    void set_matchDamage(boolean value);

    @Accessor("matchNBT")
    boolean get_matchNBT();

    @Mutable
    @Accessor("matchNBT")
    void set_matchNBT(boolean value);

    @Accessor("fluidOutput")
    FluidStack get_fluidOutput();

    @Mutable
    @Accessor("fluidOutput")
    void set_fluidOutput(FluidStack value);

    @Accessor("cookTime")
    int get_cookTime();

    @Mutable
    @Accessor("cookTime")
    void set_cookTime(int value);

    @Accessor("output")
    ItemStack get_output();

    @Mutable
    @Accessor("output")
    void set_output(ItemStack value);


}
