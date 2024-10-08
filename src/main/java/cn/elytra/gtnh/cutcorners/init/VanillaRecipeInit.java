package cn.elytra.gtnh.cutcorners.init;

import cn.elytra.gtnh.cutcorners.CutCorners;
import net.minecraft.item.crafting.FurnaceRecipes;

public class VanillaRecipeInit {

    public static void init() {
        updateFurnaceRecipes();
    }

    private static void updateFurnaceRecipes() {
        CutCorners.LOG.info("Updating Vanilla Furnace Recipes");
        FurnaceRecipes.smelting().getSmeltingList()
            .forEach((in, out) -> CutCorners.getStrategy().updateFurnaceRecipe_size(in, out));
    }

}
