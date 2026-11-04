package cn.elytra.gtnh.cutcorners.config;

import cn.elytra.gtnh.cutcorners.util.math.MathOp;
import com.google.common.collect.Sets;
import com.gtnewhorizon.gtnhlib.config.Config;
import com.gtnewhorizon.gtnhlib.config.ConfigurationManager;
import it.unimi.dsi.fastutil.objects.ReferenceArraySet;
import org.jetbrains.annotations.ApiStatus;

import java.util.Set;
import java.util.function.Supplier;

import static cn.elytra.gtnh.cutcorners.api.CutCornersAPI.CONFIGURATION_RELOAD_RUNTIME;
import static cn.elytra.gtnh.cutcorners.util.fp.LazyMapping.lazyMapping;

@Config(modid = "CutCorners-1")
@Config.Sync
public class NewConfig {

    static {
        // make sure that GTNHLib loads this configuration first.
        ConfigurationManager.registerConfig(NewConfig.class);
    }

    @ApiStatus.Internal
    public static void init() {
    }

    private static Supplier<MathOp> vm(Supplier<String> s) {
        return lazyMapping(s, input -> MathOpParser.parseOrThrow(input).memoize());
    }

    @Config.Comment("whether disable the \"MOTD\" from CC")
    @Config.DefaultBoolean(false)
    public static boolean noAboardMessage;

    // special blacklist keys
    @Config.Ignore
    public static final String
        // @formatter:off
        BL_FURNACE     = "@mcFurnace",
        BL_TC          = "@tc",
        BL_BOT         = "@bot",
        BL_RC          = "@rc",
        BL_GT_AL       = "@gtAL",
        BL_GT_EOH      = "@gtEOH",
        BL_GT_RS       = "@gtRS",
        BL_GT_WPP      = "@gtWPP",
        BL_GT_WILDCARD = "@gt*";
    // @formatter:on

    @SuppressWarnings("SpellCheckingInspection")
    @Config.Comment({
        "list of unlocalized names of GT recipe maps whose recipes should not be modified",
        "",
        "besides the unlocalized names, there are other special keys used to disable certain modifications (starts with '@'):",
        "\"@mcFurnace\" - Minecraft Furnace & EFR Furnaces",
        "\"@tc\"        - Thaumcraft",
        "\"@bot\"       - Botania",
        "\"@rc\"        - Railcraft Coke Oven & Blast Furnace",
        "\"@gtAL\"      - Assembly Line recipes in GT",
        "\"@gtEOH\"     - Eye of Harmony recipes in GT",
        "\"@gtRS\"      - Research Station recipes in GT",
        "\"@gtWPP\"     - Water Purification P. recipes in GT",
        "\"@gt*\"       - Wildcard recipes in GT",
        "",
        "NOTE: @gt* works for all modifications under getMaxProgressTime"})
    @Config.DefaultStringList({
        "gg.recipe.naquadah_reactor",
        "gt.recipe.largeboilerfakefuels"})
    public static String[] blacklist;
    @Config.Ignore
    public static final Supplier<Set<String>> BLACKLIST = lazyMapping(() -> blacklist, Sets::newHashSet);

    @Config.Comment("whether to invert the blacklist behavior")
    public static boolean whitelistMode;

    @Config.Comment({
        "the value modification expression that applied to general GT machines",
        "Expression:",
        "~        - no modification, return the value as-is",
        "=<value> - return a fixed value",
        "+<value> - return a increased value (or decreased using '-' instead of '+')",
        "*<value> - return a multiplied value (or 'x' as an alias)",
        "Attributes:",
        "Attributes can be used after the expression, to make some minor modification.",
        "It starts with '{' and ends with '}'. Entries are comma (,) separated. Key-value paired by equals (=).",
        "Like \"{min=114, max=514}\".",
        "min=<value> - the minimum value",
        "max=<value> - the maximum value"})
    @Config.DefaultString("=1{min=1}")
    public static String generalModification;
    @Config.Ignore
    public static final Supplier<MathOp> GENERAL_MODIFICATION = vm(() -> generalModification);

    @Config.Comment("whether to set all GT recipes to LV EU-tier")
    public static boolean recipesToLVTier;

    @Config.Comment("")
    @Config.DefaultBoolean(true)
    public static boolean immediateMode;

    @Config.Comment({
        "the value modification expression that applied to the block strength",
        "if the value is >= 1.0F, block is broken instantly."})
    @Config.DefaultString("+0{min=0.99}") // instead of =0.99, use +0{min=0.99} to allow unmodified instant breaks
    public static String blockStrengthModification;
    @Config.Ignore
    public static final Supplier<MathOp> BLOCK_STRENGTH = vm(() -> blockStrengthModification);

    @Config.Comment("whether all blocks are harvestable (destroy with drops) with any tools, even barehand")
    @Config.DefaultBoolean(true)
    public static boolean allHarvestable;

    @Config.Comment({
        "whether all blocks are breakable, even unbreakable blocks like Bedrock",
        "this is different from \"harvestable\", where harvestable controls if the block will drop an item (or if you're using the right tool), while breakable controls if you have any chance to break it."})
    @Config.DefaultBoolean(false)
    public static boolean allBreakable;

    @Config.Comment("the value modification expression that applied to the \"Start EU Cost\" of Eye of Harmony-s")
    @Config.DefaultString("*0.5")
    @Config.Reloadable(CONFIGURATION_RELOAD_RUNTIME)
    public static String eyeOfHarmonyStartEuCostModification;
    @Config.Ignore
    public static final Supplier<MathOp> EYE_OF_HARMONY_START_EU_COST = vm(() -> eyeOfHarmonyStartEuCostModification);

    @Config.Comment("the value modification expression that applied to \"Fuel Burn Time\" of Large Boilers")
    @Config.DefaultString("*2")
    @Config.Reloadable(CONFIGURATION_RELOAD_RUNTIME)
    public static String largeBoilerFuelBurnTimeModification;
    @Config.Ignore
    public static final Supplier<MathOp> LARGE_BOILER_FUEL_BURN_TIME = vm(() -> largeBoilerFuelBurnTimeModification);

    @Config.Comment("the value modification expression that applied to \"Amperage\" of Research Stations")
    @Config.DefaultString("~")
    @Config.Reloadable(CONFIGURATION_RELOAD_RUNTIME)
    public static String researchStationAmperageModification;
    @Config.Ignore
    public static final Supplier<MathOp> RESEARCH_STATION_AMP = vm(() -> researchStationAmperageModification);

    @Config.Comment("the value modification expression that applied to \"Min Computation\" of Research Stations")
    @Config.DefaultString("~")
    @Config.Reloadable(CONFIGURATION_RELOAD_RUNTIME)
    public static String researchStationMinComputationModification;
    @Config.Ignore
    public static final Supplier<MathOp> RESEARCH_STATION_MIN_COMPUTATION = vm(() -> researchStationMinComputationModification);

    @Config.Comment("the value modification expression that applied to \"Success Chance\" of Water Purification Plants")
    @Config.DefaultString("+50")
    @Config.Reloadable(CONFIGURATION_RELOAD_RUNTIME)
    public static String waterPurificationPlantSuccessChance;
    @Config.Ignore
    public static final Supplier<MathOp> WATER_PURIFICATION_SUCCESS_CHANCE = vm(() -> waterPurificationPlantSuccessChance);

    @Config.Comment("the value modification expression that applied to duration of Water Purification Plant")
    @Config.DefaultString("~")
    @Config.Reloadable(CONFIGURATION_RELOAD_RUNTIME)
    public static String waterPurificationPlantModification;
    @Config.Ignore
    public static final Supplier<MathOp> WATER_PURIFICATION_CYCLE = vm(() -> waterPurificationPlantModification);

    @Config.Comment("whether add effective materials of Excavators to Hammers (so that Hammers can dig gravels and sands.")
    @Config.DefaultBoolean(true)
    public static boolean ticExtraHammerMaterial;

    @Config.Comment("whether to make all Pumps transfer \"max int\"L of fluids per execution")
    @Config.DefaultBoolean(true)
    public static boolean maxBatchSizePump;

    @Config.Comment("whether to make all Conveyors transfer \"max int\" amount of items per execution")
    @Config.DefaultBoolean(true)
    public static boolean maxBatchSizeConveyor;

    @Config.Comment("whether to allow all Conveyors work with interval of 1t at minimum")
    @Config.DefaultBoolean(true)
    public static boolean maxTickRateConveyorCover;

    @Config.Comment("whether to allow all Robot Arms work with interval of 1t at minimum")
    @Config.DefaultBoolean(true)
    public static boolean maxTickRateRobotArmCover;

    @Config.Comment({
        "the tick rate of growth for Crop Sticks in CropsNH.",
        "256 by default from CropsNH; -1 for disable modification",
        "also scales the growth progress of the GT/CropsNH Industrial Farm"})
    @Config.DefaultInt(8) // 32x faster
    public static int tickRateCropsNH;

    @Config.Comment({
        "the per-tick increment of the Forestry queen work cycle throttle, and the divisor of the",
        "GT Industrial Apiary cycle length (both use the same value).",
        "1 matches vanilla (550 ticks per cycle), 55 is about 10 ticks per cycle.",
        "0 (or less) disables all bee-related speedups"})
    @Config.DefaultInt(55)
    @Config.Reloadable(CONFIGURATION_RELOAD_RUNTIME)
    public static int queenWorkCycleThrottleIncrement;

    @Config.Ignore
    public static final int VANILLA_CROP_TICK_RATE = 256;
    @Config.Ignore
    public static final int VANILLA_QUEEN_WORK_THROTTLE_INCREMENT = 1;
    @Config.Ignore
    public static final int MAX_QUEEN_WORK_THROTTLE_INCREMENT = 550;

    /** Effective queen throttle increment, clamped to 1-550. 1 (vanilla) when the speedups are disabled. */
    public static int getQueenWorkCycleThrottleIncrement() {
        int value = queenWorkCycleThrottleIncrement;
        if (value <= 0) return VANILLA_QUEEN_WORK_THROTTLE_INCREMENT;
        return Math.min(value, MAX_QUEEN_WORK_THROTTLE_INCREMENT);
    }

    public static boolean isBeeSpeedupEnabled() {
        return getQueenWorkCycleThrottleIncrement() > VANILLA_QUEEN_WORK_THROTTLE_INCREMENT;
    }

    /**
     * Effective CropsNH tick rate shared by the crop sticks and the industrial farm, so both always use the same
     * multiplier. Returns the vanilla 256 when the modification is disabled or the configured value is out of range.
     */
    public static int getCropCycleTickRate() {
        int value = tickRateCropsNH;
        if (value <= 0 || value > VANILLA_CROP_TICK_RATE) return VANILLA_CROP_TICK_RATE;
        return value;
    }

    @Config.Comment({
        "the list of MTE-internal accelerated classes (FQCN)",
        "GENERATED, DON'T EDIT!"})
    @Config.DefaultStringList({})
    @Config.Reloadable(CONFIGURATION_RELOAD_RUNTIME)
    public static String[] mteAccelerationClass;
    @Config.Ignore
    public static final Supplier<Set<Class<?>>> MTE_ACCELERATION_CLASS = lazyMapping(
        () -> mteAccelerationClass,
        NewConfig::nameSet2ClassSet);

    private static Set<Class<?>> nameSet2ClassSet(String[] names) {
        Set<Class<?>> set = new ReferenceArraySet<>();
        for (String name : names) {
            try {
                set.add(Class.forName(name));
            } catch (ClassNotFoundException ignore) {
            }
        }
        return set;
    }
}
