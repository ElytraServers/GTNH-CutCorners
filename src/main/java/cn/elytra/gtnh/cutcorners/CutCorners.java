package cn.elytra.gtnh.cutcorners;

import cn.elytra.gtnh.cutcorners.init.GTRecipeInit;
import cn.elytra.gtnh.cutcorners.init.RailcraftRecipeInit;
import cn.elytra.gtnh.cutcorners.init.VanillaRecipeInit;
import cn.elytra.gtnh.cutcorners.strate.ICutCornerStrategy;
import com.github.wohaopa.GTNHModify.GTNHModifyMod;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import gregtech.api.enums.*;
import gregtech.api.util.GTRecipeConstants;
import gregtech.api.util.recipe.Scanning;
import net.minecraft.command.ICommand;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CutCorners {

    @Nullable
    private static ICutCornerStrategy strategy;

    public static final Logger LOG = LogManager.getLogger("GTNH-CutCorners");

    /**
     * The methods that directly modify the recipes without using mixins.
     */
    private static final Runnable[] INITIALIZERS = new Runnable[] { GTRecipeInit::init, VanillaRecipeInit::init,
        RailcraftRecipeInit::init, };

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
        initialized = true;
        warnModsLoadedAfterCutCorners();
        for (Runnable initializer : INITIALIZERS) {
            try {
                initializer.run();
            } catch (Exception e) {
                LOG.error("Failed to initialize: {}", initializer, e);
            }
        }
    }

    private static boolean isDevEnvironment() {
        return (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
    }

    public static void postInit() {
        if (isDevEnvironment()) {
            LOG.info("Development Environment detected, adding Testing Recipes");

            GTValues.RA.stdBuilder()
                .metadata(GTRecipeConstants.RESEARCH_ITEM, new ItemStack(Items.apple))
                .metadata(GTRecipeConstants.SCANNING, new Scanning(1, TierEU.RECIPE_LV))
                .itemInputs(
                    ItemList.AdvDebugStructureWriter.get(1),
                    new Object[] { OrePrefixes.circuit.get(Materials.UXV), 16 },
                    new Object[] { OrePrefixes.circuit.get(Materials.UXV), 16 },
                    new Object[] { OrePrefixes.circuit.get(Materials.UXV), 16 })
                .fluidInputs(Materials.Lubricant.getFluid(1000))
                .itemOutputs(new ItemStack(Items.stick))
                .eut(1)
                .duration(1)
                .addTo(GTRecipeConstants.AssemblyLine);
        }
    }

    private static void warnModsLoadedAfterCutCorners() {
        // the mod list is sorted in loading order, so we can easily tell which mods are loaded after us.
        List<ModContainer> modList = Loader.instance().getModList();
        boolean foundCutCorners = false;
        ArrayList<ModContainer> modLoadedAfterCutCorners = new ArrayList<>();
        for (ModContainer modContainer : modList) {
            if (foundCutCorners) {
                modLoadedAfterCutCorners.add(modContainer);
            } else if (Objects.equals(modContainer.getModId(), GTNHModifyMod.MOD_ID)) {
                foundCutCorners = true;
            }
        }
        if (!modLoadedAfterCutCorners.isEmpty()) {
            LOG.warn("Following mods are loaded after CutCorners, thus some modifications may not take effect.");
            for (ModContainer modContainer : modLoadedAfterCutCorners) {
                LOG.warn("- {}", modContainer.getModId());
            }
        } else {
            LOG.info("CutCorners is the last to be loaded, everything should be fine.");
        }
    }

    @ApiStatus.Internal
    public static ICommand getCutCornersCommand() {
        return CutCornersCommand.INSTANCE;
    }
}
