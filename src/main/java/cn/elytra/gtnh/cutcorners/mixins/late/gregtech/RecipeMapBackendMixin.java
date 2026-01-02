package cn.elytra.gtnh.cutcorners.mixins.late.gregtech;

import cn.elytra.gtnh.cutcorners.internal.AddRecipeHook;
import gregtech.api.recipe.RecipeMapBackend;
import gregtech.api.util.GTRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = RecipeMapBackend.class, remap = false)
public class RecipeMapBackendMixin {

    @Inject(method = "compileRecipe", at = @At("HEAD"))
    private void hook$doAdd(GTRecipe recipe, CallbackInfoReturnable<GTRecipe> cir) {
        AddRecipeHook.onRecipeAddedToRecipeMapBackend((RecipeMapBackend) (Object) this, recipe);
    }

}
