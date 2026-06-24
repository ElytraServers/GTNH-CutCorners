package cn.elytra.gtnh.cutcorners.mixins.late.thaumcraft;

import cn.elytra.gtnh.cutcorners.mixinloader.DevelopmentEnvironmentTester;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.ShapedArcaneRecipe;

/// TC throws exception when the recipe has something missing in dev.
/// This mixin replaces the null slots to sticks, so that the game won't be fucked.
@Mixin(value = ShapedArcaneRecipe.class, remap = false)
@Restriction(
    require = @Condition(type = Condition.Type.TESTER, tester = DevelopmentEnvironmentTester.class)
)
public class ShapedArcaneRecipeNullFix {

    @Inject(method = "<init>(Ljava/lang/String;Lnet/minecraft/item/ItemStack;Lthaumcraft/api/aspects/AspectList;[Ljava/lang/Object;)V", at = @At("HEAD"))
    private static void gtnhcc$handleNullItems(String research, ItemStack result, AspectList aspects, Object[] recipe, CallbackInfo ci) {
        for (int i = 0; i < recipe.length; i++) {
            if (recipe[i] == null) {
                recipe[i] = new ItemStack(Items.stick);
            }
        }
    }

}
