package cn.elytra.gtnh.cutcorners.config;

import cn.elytra.gtnh.cutcorners.CutCorners;
import net.minecraftforge.common.config.Configuration;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;

@SuppressWarnings("UnusedReturnValue")
public class CutCornersConfig {

    public static CutCornersConfig instance;

    private final Configuration config;

    private static final String[] EXCLUDED_WARMUP_METHODS = {"save"};

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
        return this.config.getStringList("gtBlacklistedRecipeMaps", CATEGORY_BLACKLIST, new String[0], "The unlocalized names of blacklisted GregTech recipe maps. (e.g.: gt.recipe.packager)");
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

}
