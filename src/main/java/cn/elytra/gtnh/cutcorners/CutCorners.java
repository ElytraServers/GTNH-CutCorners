package cn.elytra.gtnh.cutcorners;

import cn.elytra.gtnh.cutcorners.init.GTRecipeInit;
import cn.elytra.gtnh.cutcorners.init.RailcraftRecipeInit;
import cn.elytra.gtnh.cutcorners.init.VanillaRecipeInit;
import cn.elytra.gtnh.cutcorners.strate.ICutCornerStrategy;
import cn.elytra.gtnh.cutcorners.strate.impl.event.CutCornersEventDispatchHelper;
import gregtech.api.enums.*;
import gregtech.api.util.GTRecipeConstants;
import gregtech.api.util.recipe.Scanning;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CutCorners {

    @Nullable
    private static ICutCornerStrategy strategy;

    public static final Logger LOG = LogManager.getLogger("GTNH-CutCorners");

    /**
     * The methods that directly modify the recipes without using mixins.
     */
    private static final Runnable[] INITIALIZERS = new Runnable[]{
        GTRecipeInit::init,
        VanillaRecipeInit::init,
        RailcraftRecipeInit::init,
    };

    public static void setStrategy(@NotNull ICutCornerStrategy strategies) {
        CutCorners.strategy = strategies;
    }

    private static boolean initialized = false;

    @NotNull
    public static ICutCornerStrategy getStrategy() {
        if (strategy == null) {
            throw new IllegalStateException("strategy has not been set yet!");
        }
        return strategy;
    }

    // called in LoadComplete event
    public static void loadComplete() {
        if (initialized) {
            CutCornersEventDispatchHelper.checkReinitializeCompatibility();
        }

        initialized = true;
        for (Runnable initializer : INITIALIZERS) {
            try {
                initializer.run();
            } catch (Exception e) {
                LOG.error("Failed to initialize: {}", initializer, e);
            }
        }
    }

    @Deprecated
    public static void registerListener(Object listener) {
        CutCornersEventDispatchHelper.registerListener(listener);
    }

    @Deprecated
    public static void unregisterListener(Object listener) {
        CutCornersEventDispatchHelper.unregisterListener(listener);
    }

    private static boolean isDevEnvironment() {
        return (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
    }

    public static void postInit() {
        if(isDevEnvironment()) {
            LOG.info("Development Environment detected, adding Testing Recipes");

            GTValues.RA.stdBuilder()
                .metadata(GTRecipeConstants.RESEARCH_ITEM, new ItemStack(Items.apple))
                .metadata(GTRecipeConstants.SCANNING, new Scanning(1, TierEU.RECIPE_LV))
                .itemInputs(
                    ItemList.AdvDebugStructureWriter.get(1),
                    new Object[] {OrePrefixes.circuit.get(Materials.UXV), 16},
                    new Object[] {OrePrefixes.circuit.get(Materials.UXV), 16},
                    new Object[] {OrePrefixes.circuit.get(Materials.UXV), 16}
                )
                .fluidInputs(
                    Materials.Lubricant.getFluid(1000)
                )
                .itemOutputs(new ItemStack(Items.stick))
                .eut(1)
                .duration(1)
                .addTo(GTRecipeConstants.AssemblyLine);
        }
    }
}
