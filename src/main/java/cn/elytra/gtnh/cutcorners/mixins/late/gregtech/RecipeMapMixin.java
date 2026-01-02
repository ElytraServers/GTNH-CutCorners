package cn.elytra.gtnh.cutcorners.mixins.late.gregtech;

import cn.elytra.gtnh.cutcorners.internal.AddRecipeHook;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTRecipeBuilder;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Collection;

@Mixin(value = RecipeMap.class, remap = false)
public class RecipeMapMixin {

    @WrapMethod(method = "doAdd")
    private Collection<GTRecipe> cc$provideContext(GTRecipeBuilder builder, Operation<Collection<GTRecipe>> original) {
        try {
            AddRecipeHook.setRecipeMapContext(this);
            return original.call(builder);
        } finally {
            AddRecipeHook.setRecipeMapContext(null);
        }
    }

}
