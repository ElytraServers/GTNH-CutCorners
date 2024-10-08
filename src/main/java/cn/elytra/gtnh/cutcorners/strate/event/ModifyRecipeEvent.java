package cn.elytra.gtnh.cutcorners.strate.event;

import com.github.technus.tectech.recipe.EyeOfHarmonyRecipe;
import com.github.wohaopa.GTNHModify.mixins.late.gregtech.EyeOfHarmonyRecipeAccessor;
import com.github.wohaopa.GTNHModify.mixins.late.railcraft.BlastFurnaceRecipeAccessor;
import com.github.wohaopa.GTNHModify.mixins.late.railcraft.CokeOvenRecipeAccessor;
import cpw.mods.fml.common.eventhandler.Event;
import gregtech.api.util.GT_Recipe;
import mods.railcraft.api.crafting.IBlastFurnaceRecipe;
import mods.railcraft.api.crafting.ICokeOvenRecipe;
import net.minecraft.item.ItemStack;

public abstract class ModifyRecipeEvent<T> extends Event {

    public final T recipe;

    public ModifyRecipeEvent(T recipe) {
        this.recipe = recipe;
    }

    public static class GregTech extends ModifyRecipeEvent<GT_Recipe> {
        public GregTech(GT_Recipe recipe) {
            super(recipe);
        }
    }

    public static class GT_AssemblyLine extends ModifyRecipeEvent<GT_Recipe.GT_Recipe_AssemblyLine> {
        public GT_AssemblyLine(GT_Recipe.GT_Recipe_AssemblyLine recipe) {
            super(recipe);
        }
    }

    public static class GT_EyeOfHarmony extends ModifyRecipeEvent<EyeOfHarmonyRecipe> {
        public GT_EyeOfHarmony(EyeOfHarmonyRecipe recipe) {
            super(recipe);
        }

        public EyeOfHarmonyRecipeAccessor getAccessor() {
            return (EyeOfHarmonyRecipeAccessor) recipe;
        }
    }

    public static class RC_CokeOven extends ModifyRecipeEvent<ICokeOvenRecipe> {
        public RC_CokeOven(ICokeOvenRecipe recipe) {
            super(recipe);
        }

        public CokeOvenRecipeAccessor getAccessor() {
            return (CokeOvenRecipeAccessor) recipe;
        }
    }

    public static class RC_BlastFurnace extends ModifyRecipeEvent<IBlastFurnaceRecipe> {
        public RC_BlastFurnace(IBlastFurnaceRecipe recipe) {
            super(recipe);
        }

        public BlastFurnaceRecipeAccessor getAccessor() {
            return (BlastFurnaceRecipeAccessor) recipe;
        }
    }

    public static class GT_ResearchStation extends ModifyRecipeEvent<GT_Recipe> {
        public GT_ResearchStation(GT_Recipe recipe) {
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
