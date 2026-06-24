package cn.elytra.gtnh.cutcorners.config;

import cn.elytra.gtnh.cutcorners.CutCorners;
import com.gtnewhorizon.gtnhlib.util.data.Lazy;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@SuppressWarnings("UnusedReturnValue")
public class CutCornersConfig {

    public static CutCornersConfig instance;

    private final Configuration config;

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String[] EXCLUDED_WARMUP_METHODS = {"save"};
    private static final List<Runnable> UPDATE_LISTENERS = new ArrayList<>();

    public CutCornersConfig(@NotNull Configuration config) {
        instance = this;

        this.config = config;

        // read all properties once here, so we can save the file
        for (Method m : getClass().getDeclaredMethods()) {
            if (!ArrayUtils.contains(EXCLUDED_WARMUP_METHODS, m.getName()) && m.getParameterCount() == 0) {
                try {
                    if (Boolean.getBoolean("cut_corners.debug.config")) {
                        CutCorners.LOG.info("INVOKING {} for CutCorners Config WarmUp", m.getName());
                    }
                    m.invoke(this);
                } catch (Exception e) {
                    CutCorners.LOG.error("Exception occurred while 'warm-up' configuration!", e);
                }
            }
        }

        this.save();
    }

    public Configuration getConfig() {
        return config;
    }

    public void save() {
        this.config.save();
    }

    public static void update(Consumer<Configuration> block) {
        CutCornersConfig config = Objects.requireNonNull(instance);
        block.accept(config.config);
        config.save();
        // the user-given logics are wrapped in a try-catch block, so it's safe to ignore the exceptions.
        UPDATE_LISTENERS.forEach(Runnable::run);
    }

    public static void onUpdate(Runnable block) {
        Exception listenerSource = new Exception("Listener Registrator");
        UPDATE_LISTENERS.add(() -> {
            try {
                block.run();
            } catch (Throwable e) {
                // add the source to suppressed for better debugging.
                e.addSuppressed(listenerSource);
                LOGGER.warn("Failed to invoke configuration update listener.", e);
            }
        });
    }

    /// Run the block immediately and run again when the config is updated.
    public static void onUpdateAndNow(Runnable block) {
        block.run();
        onUpdate(block);
    }

    // region Utils

    private static ValueModification mergeValueModification(int mode, int fixedValue, float rationalValue, @Nullable ValueModification defaultValue) {
        return switch (mode) {
            case 0 -> new ValueModification.NoMod();
            case 1 -> new ValueModification.Fixed(fixedValue);
            case 2 -> new ValueModification.Rational(rationalValue);
            default -> {
                if (defaultValue != null) {
                    yield defaultValue;
                } else {
                    throw new AssertionError("Invalid value modification mode " + mode);
                }
            }
        };
    }

    // endregion

    // region Blacklist

    public static final String CATEGORY_BLACKLIST = "blacklist";

    public boolean whitelistMode() {
        return this.config.getBoolean("whitelistMode", CATEGORY_BLACKLIST, false, "Consider blacklists as whitelists");
    }

    public String[] getGregTechBlacklistedRecipeMaps() {
        return this.config.getStringList("gtBlacklistedRecipeMaps", CATEGORY_BLACKLIST, new String[]{"gg.recipe.naquadah_reactor"}, "The unlocalized names of blacklisted GregTech recipe maps. (e.g.: gt.recipe.packager)");
    }

    public boolean doesBlacklistFurnace() {
        return this.config.getBoolean("blacklistFurnace", CATEGORY_BLACKLIST, false, "Blacklist the vanilla furnaces.");
    }

    public boolean doesBlacklistRailcraft() {
        return this.config.getBoolean("blacklistRailcraft", CATEGORY_BLACKLIST, false, "Blacklist the Railcraft furnaces (Coke Oven and Blast Furnace).");
    }

    public boolean doesBlacklistAssemblyLine() {
        return this.config.getBoolean("blacklistAssemblyLine", CATEGORY_BLACKLIST, false, "Blacklist the GregTech Assembly Line.");
    }

    public boolean doesBlacklistEyeOfHarmony() {
        return this.config.getBoolean("blacklistEyeOfHarmony", CATEGORY_BLACKLIST, false, "Blacklist the Eye of Harmony.");
    }

    public boolean doesBlacklistResearchStation() {
        return this.config.getBoolean("blacklistResearchStation", CATEGORY_BLACKLIST, false, "Blacklist the Research Station.");
    }

    public boolean doesBlacklistWaterPurification() {
        return this.config.getBoolean("blacklistWaterPurification", CATEGORY_BLACKLIST, false, "Blacklist the Water Purification Plant.");
    }

    public boolean doesBlacklistWildcardDurationModification() {
        return this.config.getBoolean("blacklistWildcardDurationModification", CATEGORY_BLACKLIST, false, "Blacklist the Wildcard Duration Modification.");
    }

    // endregion

    // region Duration Modification

    private static final String CATEGORY_DURATION_MOD = "durationMod";

    public ValueModification getDurationModification() {
        int mode = this.config.getInt("mode", CATEGORY_DURATION_MOD, 0, 0, 2, """
            Mode of Duration Modification

            0 = None : Not modifying.
            1 = Fixed : Durations of recipes are fixed to 'fixedDuration' ticks.
            2 = Rational : Durations of recipes are multiplied by 'rationalDuration'. (e.g.: rationalDuration is 0.4, the durations are reduced by 60%.)""");

        int fixedDuration = this.config.getInt("fixedDuration", CATEGORY_DURATION_MOD, 1, 1, Integer.MAX_VALUE, "Ticks of the recipe durations.");
        float rationalDuration = this.config.getFloat("rationalDuration", CATEGORY_DURATION_MOD, 0.5F, 0.0001F, Float.MAX_VALUE, "Multiplier of the recipe durations.");

        return mergeValueModification(mode, fixedDuration, rationalDuration, null);
    }

    // endregion

    // region GregTech Recipe Specific

    public static final String CATEGORY_GREGTECH_SPEC = "gregtech-spec";

    public boolean useAllLVRecipes() {
        return this.config.getBoolean("allLVRecipes", CATEGORY_GREGTECH_SPEC, false, "Make all GregTech recipes to LV-level EU/T requirement");
    }

    public boolean useUpdateResearchTime() {
        return !this.config.getBoolean("notUpdateResearchTime", CATEGORY_GREGTECH_SPEC, false, "Don't update the Research Time of Research Station.");
    }

    // endregion

    // region EOH Specific

    public static final String CATEGORY_EOH_SPEC = "eoh-spec";

    public ValueModification getEOHStartEuCostModification() {
        int mode = this.config.getInt("startEuCostMode", CATEGORY_EOH_SPEC, 0, 0, 2, """
            Mode of EU Cost Modification

            0 = None
            1 = Fixed
            2 = Rational""");

        int fixedStartEuCost = this.config.getInt("fixedStartEuCost", CATEGORY_EOH_SPEC, 1, 1, Integer.MAX_VALUE, "EU Cost for Starting EOH.");
        float rationalStartEuCost = this.config.getFloat("rationalStartEuCost", CATEGORY_EOH_SPEC, 0.2F, 0.001F, Float.MAX_VALUE, "Multiplier of EU Cost for Starting EOH.");

        return mergeValueModification(mode, fixedStartEuCost, rationalStartEuCost, new ValueModification.NoMod());
    }

    // endregion

    // region Research Station Specific

    public static final String CATEGORY_RESEARCH_STATION_SPEC = "research-station-spec";

    public ValueModification getResearchStationAmpModification() {
        int mode = this.config.getInt("ampMode", CATEGORY_RESEARCH_STATION_SPEC, 0, 0, 2, """
            Mode of Research Station Amp Modification

            0 = None
            1 = Fixed
            2 = Rational""");

        int fixedAmp = this.config.getInt("fixedAmp", CATEGORY_RESEARCH_STATION_SPEC, 1, 1, Integer.MAX_VALUE, "The fixed Amp of Research Station recipes.");
        float rationalAmp = this.config.getFloat("rationalAmp", CATEGORY_RESEARCH_STATION_SPEC, 0.2F, 0.001F, Float.MAX_VALUE, "Multiplier of Amp of Research Station recipes.");

        return mergeValueModification(mode, fixedAmp, rationalAmp, new ValueModification.NoMod());
    }

    public ValueModification getResearchStationMinComputationModification() {
        int mode = this.config.getInt("minComputationMode", CATEGORY_RESEARCH_STATION_SPEC, 0, 0, 2, """
            Mode of Research Station Min Computation Modification

            0 = None
            1 = Fixed
            2 = Rational""");

        int fixedMinComputation = this.config.getInt("fixedMinComputation", CATEGORY_RESEARCH_STATION_SPEC, 1, 1, Integer.MAX_VALUE, "The fixed Min Computation of Research Station recipes.");
        float rationalMinComputation = this.config.getFloat("rationalMinComputation", CATEGORY_RESEARCH_STATION_SPEC, 0.2F, 0.001F, Float.MAX_VALUE, "Multiplier of Min Computation of Research Station recipes.");

        return mergeValueModification(mode, fixedMinComputation, rationalMinComputation, new ValueModification.NoMod());
    }

    // endregion

    // region Water Purification

    private static final String CATEGORY_WATER_PURIFICATION_SPEC = "water-purification-spec";

    public WaterPurificationConfigData getWaterPurificationSuccessChanceModification() {
        int mode = this.config.getInt("successChance", CATEGORY_WATER_PURIFICATION_SPEC, 0, 0, 2, """
            Mode of Water Purification Success Chance Modification

            NOTE: the value is valid in range from 0F to 100F.

            0 = None
            1 = Fixed
            2 = Additional""");

        float fixedSuccessChance = this.config.getFloat("fixedSuccessChance", CATEGORY_WATER_PURIFICATION_SPEC, 100, 0, 100, "The fixed success chance of water purification.");
        float additionalSuccessChance = this.config.getFloat("additionalSuccessChance", CATEGORY_WATER_PURIFICATION_SPEC, 100, 0, 100, "The additional success chance of water purification.");
        return switch (mode) {
            case 0 -> WaterPurificationConfigData.none();
            case 1 -> WaterPurificationConfigData.fixed(fixedSuccessChance);
            case 2 -> WaterPurificationConfigData.additional(additionalSuccessChance);
            default -> throw new IllegalStateException("Unexpected value: " + mode);
        };
    }

    // endregion

    // region General

    private static final String CATEGORY_RM_ACCELERATION_SPEC = "run-machine-acceleration-spec";
    private static final String PROPERTY_RM_ACCELERATION_TARGET_CLASSES = "target-classes";

    public static final Lazy<List<Class<?>>> MAX_PROGRESS_TIME_RUN_MACHINE_CLASSES = new Lazy<>(RunMachineAcceleration::getClasses);

    public List<Class<?>> getMaxProgressTimeRunMachineClasses() {
        // define the prop.
        this.config.getStringList(
            PROPERTY_RM_ACCELERATION_TARGET_CLASSES,
            CATEGORY_RM_ACCELERATION_SPEC,
            new String[0],
            "The full-qualified names of classes to patch time in runMachine() function. experimental.");
        return RunMachineAcceleration.getClasses();
    }

    // endregion

    public static class RunMachineAcceleration {
        private static final Logger LOGGER = LogManager.getLogger();

        private static Property prop() {
            // the property will be initialized during the configuration loading, so we don't care about other settings.
            return instance.config.get(
                CATEGORY_RM_ACCELERATION_SPEC,
                PROPERTY_RM_ACCELERATION_TARGET_CLASSES,
                new String[0]);
        }

        public static boolean addClass(Class<?> clazz) {
            Property prop = prop();
            HashSet<String> cls = new HashSet<>(Arrays.asList(prop.getStringList()));
            boolean added = cls.add(clazz.getCanonicalName());
            if (added) {
                prop.set(cls.toArray(new String[0]));
            }
            return added;
        }

        public static boolean containsClass(Class<?> clazz) {
            Property prop = prop();
            List<String> cls = Arrays.asList(prop.getStringList());
            return cls.contains(clazz.getCanonicalName());
        }

        @Unmodifiable
        public static List<Class<?>> getClasses() {
            Property prop = prop();
            String[] array = prop.getStringList();
            ArrayList<Class<?>> list = new ArrayList<>(array.length);
            for (String fqn : array) {
                try {
                    Class<?> clazz = Class.forName(fqn, false, Thread.currentThread().getContextClassLoader());
                    list.add(clazz);
                } catch (ClassNotFoundException e) {
                    LOGGER.warn("Couldn't load the target class of R.M. Acceleration: {}", fqn, e);
                }
            }
            return Collections.unmodifiableList(list);
        }
    }

}
