package com.github.wohaopa.GTNHModify.mixins.late.gregtech;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import tectech.recipe.EyeOfHarmonyRecipe;
import tectech.util.FluidStackLong;
import tectech.util.ItemStackLong;

import java.util.ArrayList;

@Mixin(value = EyeOfHarmonyRecipe.class, remap = false)
public interface EyeOfHarmonyRecipeAccessor {

    @Accessor("outputItems")
    ArrayList<ItemStackLong> get_outputItems();

    @Accessor("outputItems")
    @Mutable
    void set_outputItems(ArrayList<ItemStackLong> value);

    @Accessor("outputFluids")
    ArrayList<FluidStackLong> get_outputFluids();

    @Accessor("outputFluids")
    @Mutable
    void set_outputFluids(ArrayList<FluidStackLong> value);

    @Accessor("hydrogenRequirement")
    long get_hydrogenRequirement();

    @Accessor("hydrogenRequirement")
    @Mutable
    void set_hydrogenRequirement(long value);

    @Accessor("heliumRequirement")
    long get_heliumRequirement();

    @Accessor("heliumRequirement")
    @Mutable
    void set_heliumRequirement(long value);

    @Accessor("euOutput")
    long get_euOutput();

    @Accessor("euOutput")
    @Mutable
    void set_euOutput(long value);

    @Accessor("euStartCost")
    long get_euStartCost();

    @Accessor("euStartCost")
    @Mutable
    void set_euStartCost(long value);

    @Accessor("baseSuccessChance")
    double get_baseSuccessChance();

    @Accessor("baseSuccessChance")
    @Mutable
    void set_baseSuccessChance(double value);

    @Accessor("spacetimeCasingTierRequired")
    long get_spacetimeCasingTierRequired();

    @Accessor("spacetimeCasingTierRequired")
    @Mutable
    void set_spacetimeCasingTierRequired(long value);

    @Accessor("miningTimeSeconds")
    long get_miningTimeSeconds();

    @Accessor("miningTimeSeconds")
    @Mutable
    void set_miningTimeSeconds(long value);

    @Accessor("recipeEnergyEfficiency")
    double get_recipeEnergyEfficiency();

    @Accessor("recipeEnergyEfficiency")
    @Mutable
    void set_recipeEnergyEfficiency(double value);

    @Accessor("recipeTriggerItem")
    ItemStack get_recipeTriggerItem();

    @Accessor("recipeTriggerItem")
    @Mutable
    void set_recipeTriggerItem(ItemStack value);

    @Accessor("sumOfItems")
    long get_sumOfItems();

    @Accessor("sumOfItems")
    @Mutable
    void set_sumOfItems(long value);

    @Accessor("rocketTier")
    long get_rocketTier();

    @Accessor("rocketTier")
    @Mutable
    void set_rocketTier(long value);

}
