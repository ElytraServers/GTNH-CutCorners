package cn.elytra.gtnh.cutcorners.config;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.strate.CutCornerStrategies;
import cn.elytra.gtnh.cutcorners.strate.ICutCornerStrategy;
import cn.elytra.gtnh.cutcorners.strate.impl.event.CutCornerBuiltinEventHandlers;
import cn.elytra.gtnh.cutcorners.strate.impl.event.CutCornersEventDispatchHelper;
import com.github.wohaopa.GTNHModify.GTNHModifyMod;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;
import java.util.Optional;

public class Config {

    public static Configuration config;

    public static final String CATE_TICK_REDUCE = "reduce-tick";

    private static boolean doSave;

    public static void init(File configFile) {
        if (config == null) {
            config = new Configuration(configFile);
        }
        doSave = false;
        loadConfig();
        FMLCommonHandler.instance()
            .bus()
            .register(new Config());
    }

    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equalsIgnoreCase(GTNHModifyMod.MOD_ID)) {
            loadConfig();
        }
    }

    private static void loadConfig() {

        Property propStrategy = config.get(
            Configuration.CATEGORY_GENERAL,
            "Strategy",
            "EVENT",
            """
                The strategy of cut corners.
                NOOP: No cut corners.
                EVENT: Cut corners triggered by events.
                Others are deprecated and works like EVENT.

                (DEPRECATED OPTION)""",
            new String[]{"NOOP", "EVENT", "None", "OneTick", "Tenths", "Output64", "Energyless"}
        );
        String strategyName = propStrategy.getString();

        // handle deprecated
        if (strategyName.equals("None") ||
            strategyName.equals("OneTick") ||
            strategyName.equals("Tenths") ||
            strategyName.equals("Output64") ||
            strategyName.equals("Energyless")) {
            propStrategy.set("EVENT");
            strategyName = propStrategy.getString();
        }

        try {
            Optional<ICutCornerStrategy> strategy = CutCornerStrategies.getByName(strategyName);
            CutCorners.setStrategy(strategy.orElse(CutCornerStrategies.EVENT));
        } catch (IllegalStateException e) {
            CutCorners.LOG.error("The strategy {} is not implemented in CutCorners!", strategyName, e);
        }

        Property propEventHandlers = config.get(
            Configuration.CATEGORY_GENERAL,
            "EventHandlers",
            new String[] {"ONE_TICK"},
            """
                The event handlers used by EVENT strategy.
                ONE_TICK: All recipes and processing time are set to 1 tick.
                RATIO_TICK: All recipes and processing time are reduced to ratio * original.
                FIXED_TICK: All recipes and processing time are reduced by fixed tick.
                GT_TIER_LV: All voltage tier of GT recipes are set to LV."""
        );
        String[] eventHandlerNames = propEventHandlers.getStringList();
        if(eventHandlerNames.length == 0) {
            CutCorners.LOG.info("No handler is configured");
        }
        for (String name : eventHandlerNames) {
            Optional<Object> handlerOptional = CutCornerBuiltinEventHandlers.getHandler(name);
            if (handlerOptional.isPresent()) {
                var handler = handlerOptional.get();
                CutCorners.LOG.info("Using handler {}", handler.getClass().getSimpleName());
                CutCornersEventDispatchHelper.registerListener(handler);
            } else {
                CutCorners.LOG.error("The event handler {} does not exist!", name);
            }
        }

        if (config.hasChanged() || doSave) {
            config.save();
            doSave = false;
        }
    }

    public static float getRatioTickValue() {
        return config.getFloat("ratio", CATE_TICK_REDUCE, 0.5F, 0.0F, 1.0F, "The ratio of duration.");
    }

    public static int getFixedReduceTickValue() {
        return config.getInt("fixed", CATE_TICK_REDUCE, 60 * 20 /* 1 min */, 0, Integer.MAX_VALUE, "The fixed ticks to reduce.");
    }

}
