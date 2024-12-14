package cn.elytra.gtnh.cutcorners.strate.impl.event.event;

import com.github.wohaopa.GTNHModify.mixins.late.gregtech.EyeOfHarmonyRecipeAccessor;
import com.github.wohaopa.GTNHModify.mixins.late.railcraft.BlastFurnaceRecipeAccessor;
import com.github.wohaopa.GTNHModify.mixins.late.railcraft.CokeOvenRecipeAccessor;
import cpw.mods.fml.common.eventhandler.Event;
import gregtech.api.util.GTRecipe;
import mods.railcraft.api.crafting.IBlastFurnaceRecipe;
import mods.railcraft.api.crafting.ICokeOvenRecipe;
import tectech.recipe.EyeOfHarmonyRecipe;

public abstract class ModifyRecipeEvent<T> extends Event {

    public final T recipe;

    public ModifyRecipeEvent(T recipe) {
        this.recipe = recipe;
    }

    public static class GregTech extends ModifyRecipeEvent<GTRecipe> implements IHasDuration {
        public GregTech(GTRecipe recipe) {
            super(recipe);
        }

        @Override
        public int getDuration() {
            return recipe.mDuration;
        }

        @Override
        public void setDuration(int duration) {
            recipe.mDuration = duration;
        }
    }

    public static class GT_AssemblyLine extends ModifyRecipeEvent<GTRecipe.RecipeAssemblyLine> implements IHasDuration {
        public GT_AssemblyLine(GTRecipe.RecipeAssemblyLine recipe) {
            super(recipe);
        }

        @Override
        public int getDuration() {
            return recipe.mDuration;
        }

        @Override
        public void setDuration(int duration) {
            recipe.mDuration = duration;
        }
    }

    public static class GT_EyeOfHarmony extends ModifyRecipeEvent<EyeOfHarmonyRecipe> implements IHasLongDuration {
        public GT_EyeOfHarmony(EyeOfHarmonyRecipe recipe) {
            super(recipe);
        }

        public EyeOfHarmonyRecipeAccessor getAccessor() {
            return (EyeOfHarmonyRecipeAccessor) recipe;
        }

        @Override
        public long getDuration() {
            return getAccessor().get_miningTimeSeconds();
        }

        @Override
        public void setDuration(long duration) {
            getAccessor().set_miningTimeSeconds(duration);
        }
    }

    public static class RC_CokeOven extends ModifyRecipeEvent<ICokeOvenRecipe> implements IHasDuration {
        public RC_CokeOven(ICokeOvenRecipe recipe) {
            super(recipe);
        }

        public CokeOvenRecipeAccessor getAccessor() {
            return (CokeOvenRecipeAccessor) recipe;
        }

        @Override
        public int getDuration() {
            return getAccessor().get_cookTime();
        }

        @Override
        public void setDuration(int duration) {
            getAccessor().set_cookTime(duration);
        }
    }

    public static class RC_BlastFurnace extends ModifyRecipeEvent<IBlastFurnaceRecipe> implements IHasDuration {
        public RC_BlastFurnace(IBlastFurnaceRecipe recipe) {
            super(recipe);
        }

        public BlastFurnaceRecipeAccessor getAccessor() {
            return (BlastFurnaceRecipeAccessor) recipe;
        }

        @Override
        public int getDuration() {
            return getAccessor().get_cookTime();
        }

        @Override
        public void setDuration(int duration) {
            getAccessor().set_cookTime(duration);
        }
    }

    public static class GT_ResearchStation extends GregTech {
        public GT_ResearchStation(GTRecipe recipe) {
            super(recipe);
        }

        private static final int MASK = 0xFFFF;

        public int getAmp() {
            return recipe.mSpecialValue & 0xFFFF;
        }

        public void setAmp(int amp) {
            recipe.mSpecialValue &= ~MASK;
            recipe.mSpecialValue |= amp & MASK;
        }

        public int getMinComputation() {
            return recipe.mSpecialValue >> 16 & MASK;
        }

        public void setMinComputation(int minComputation) {
            recipe.mSpecialValue &= MASK;
            recipe.mSpecialValue &= minComputation & MASK << 16;
        }
    }

}
