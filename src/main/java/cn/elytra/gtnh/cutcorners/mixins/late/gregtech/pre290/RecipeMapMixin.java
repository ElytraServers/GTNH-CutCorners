package cn.elytra.gtnh.cutcorners.mixins.late.gregtech.pre290;

import cn.elytra.gtnh.cutcorners.internal.AddRecipeHook;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTRecipeBuilder;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Collection;

@Mixin(value = RecipeMap.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.GregTech))
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
