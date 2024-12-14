package cn.elytra.gtnh.cutcorners;

import cn.elytra.gtnh.cutcorners.init.GTRecipeInit;
import cn.elytra.gtnh.cutcorners.init.RailcraftRecipeInit;
import cn.elytra.gtnh.cutcorners.init.VanillaRecipeInit;
import cn.elytra.gtnh.cutcorners.strate.CutCornerStrategies;
import cn.elytra.gtnh.cutcorners.strate.ICutCornerStrategy;
import cn.elytra.gtnh.cutcorners.strate.impl.event.CutCornersEventDispatchHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CutCorners {

    private static ICutCornerStrategy strategy = CutCornerStrategies.EVENT;

    public static final Logger LOG = LogManager.getLogger("GTNH-CutCorners");

    /**
     * The methods that directly modify the recipes without using mixins.
     */
    private static final Runnable[] INITIALIZERS = new Runnable[]{
        GTRecipeInit::init,
        VanillaRecipeInit::init,
        RailcraftRecipeInit::init,
    };

    public static void setStrategy(ICutCornerStrategy strategies) {
        CutCorners.strategy = strategies;
    }

    private static boolean initialized = false;

    public static ICutCornerStrategy getStrategy() {
        if (strategy == null) {
            throw new IllegalStateException("strategy has not been set yet!");
        }
        return strategy;
    }

    // called in LoadComplete event
    public static void init() {
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
}
