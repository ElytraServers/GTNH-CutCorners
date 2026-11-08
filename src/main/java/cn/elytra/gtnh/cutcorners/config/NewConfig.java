package cn.elytra.gtnh.cutcorners.config;

import cn.elytra.gtnh.cutcorners.util.math.MathOp;
import com.google.common.collect.Sets;
import com.gtnewhorizon.gtnhlib.config.Config;
import com.gtnewhorizon.gtnhlib.config.ConfigurationManager;
import it.unimi.dsi.fastutil.objects.ReferenceArraySet;
import org.jetbrains.annotations.ApiStatus;

import java.util.Set;
import java.util.function.Supplier;

import static cn.elytra.gtnh.cutcorners.util.fp.LazyMapping.dynamicMapping;

@Config(modid = "CutCorners-1")
public class NewConfig {

    static {
        // make sure that GTNHLib loads this configuration first.
        ConfigurationManager.registerConfig(NewConfig.class);
    }

    @ApiStatus.Internal
    public static void init() {
    }

    private static Supplier<MathOp> op(Supplier<String> s) {
        return dynamicMapping(s, input -> MathOp.parse(input).memoize());
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
    public static final Supplier<Set<String>> BLACKLIST = dynamicMapping(() -> blacklist, Sets::newHashSet);

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
    public static final Supplier<MathOp> GENERAL_MODIFICATION = op(() -> generalModification);

    @Config.Comment("whether to set all GT recipes to LV EU-tier")
    @Config.DefaultBoolean(false)
    public static boolean recipesToLVTier;

    @Config.Comment("")
    @Config.DefaultBoolean(true)
    public static boolean immediateMode;

    @Config.Comment({
        "the value modification expression that applied to the block strength when harvestable (tool matches or no tool required)",
        "if the value is >= 1.0F, block is broken instantly."})
    @Config.DefaultString("+0{min=0.99}") // instead of =0.99, use +0{min=0.99} to allow unmodified instant breaks
    public static String blockStrengthModification;
    @Config.Ignore
    public static final Supplier<MathOp> BLOCK_STRENGTH = op(() -> blockStrengthModification);

    @Config.Comment({
        "the value modification expression that applied to the block strength, when non-harvestable (tool mismatch or harvest level higher than the tool)",
        "by default, it's about 3.33x (or 100 / 30, to be precise) slower comparing to the harvestable situation, ignoring the benefits from tools.",
        "if the value is >= 1.0F, block is broken instantly."})
    @Config.DefaultString("*4")
    public static String blockStrengthNonHarvestModification;
    @Config.Ignore
    public static final Supplier<MathOp> BLOCK_STRENGTH_NON_HARVEST = op(() -> blockStrengthNonHarvestModification);

    @Config.Comment("whether all blocks are harvestable (destroy with drops) with any tools, even barehand")
    @Config.DefaultBoolean(true)
    public static boolean allHarvestable;

    @Config.Comment({
        "whether all blocks are breakable, even unbreakable blocks like Bedrock",
        "this is different from \"harvestable\", where harvestable controls if the block will drop an item (or if you're using the right tool), while breakable controls if you have any chance to break it."})
    @Config.DefaultBoolean(false)
    public static boolean allBreakable;

    @Config.Comment("whether to cancel the digging speed penalty when not on ground. (which is 5x slower than on ground)")
    @Config.DefaultBoolean(true)
    public static boolean noFlyingDigPenalty;

    @Config.Comment("the value modification expression that applied to the \"Start EU Cost\" of Eye of Harmony-s")
    @Config.DefaultString("*0.5")
    public static String eyeOfHarmonyStartEuCostModification;
    @Config.Ignore
    public static final Supplier<MathOp> EYE_OF_HARMONY_START_EU_COST = op(() -> eyeOfHarmonyStartEuCostModification);

    @Config.Comment("the value modification expression that applied to \"Fuel Burn Time\" of Large Boilers")
    @Config.DefaultString("*2")
    public static String largeBoilerFuelBurnTimeModification;
    @Config.Ignore
    public static final Supplier<MathOp> LARGE_BOILER_FUEL_BURN_TIME = op(() -> largeBoilerFuelBurnTimeModification);

    @Config.Comment("the value modification expression that applied to \"Amperage\" of Research Stations")
    @Config.DefaultString("~")
    public static String researchStationAmperageModification;
    @Config.Ignore
    public static final Supplier<MathOp> RESEARCH_STATION_AMP = op(() -> researchStationAmperageModification);

    @Config.Comment("the value modification expression that applied to \"Min Computation\" of Research Stations")
    @Config.DefaultString("~")
    public static String researchStationMinComputationModification;
    @Config.Ignore
    public static final Supplier<MathOp> RESEARCH_STATION_MIN_COMPUTATION = op(() -> researchStationMinComputationModification);

    @Config.Comment("the value modification expression that applied to \"Success Chance\" of Water Purification Plants")
    @Config.DefaultString("+50")
    public static String waterPurificationPlantSuccessChance;
    @Config.Ignore
    public static final Supplier<MathOp> WATER_PURIFICATION_SUCCESS_CHANCE = op(() -> waterPurificationPlantSuccessChance);

    @Config.Comment("the value modification expression that applied to duration of Water Purification Plant")
    @Config.DefaultString("~")
    public static String waterPurificationPlantModification;
    @Config.Ignore
    public static final Supplier<MathOp> WATER_PURIFICATION_CYCLE = op(() -> waterPurificationPlantModification);

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
        "the multiplier of the growth progress",
        "the value is replacing \"Growth rate multiplier\" in CropsNH configuration. the range isn't capped.",
        "the value should be positive."})
    @Config.DefaultFloat(10.0F)
    public static float growthMultiplierCropsNH;

    @Config.Comment({
        "the tick rate of growth for Crop Sticks in CropsNH",
        "the value should be positive"})
    @Config.DefaultInt(1)
    public static int tickRateCropsNH;

    @Config.Comment({
        "the interval ticks of queen working",
        "the value should be positive"})
    @Config.DefaultInt(1)
    public static int queenIntervalForestry;

    @Config.Comment({
        "the output interval of Super & Quantum Tanks",
        "20 by default. -1 to disable modification."})
    @Config.DefaultInt(-1)
    public static int quantumTankOutputInterval;

    @Config.Comment("the value modification applied to the output amount of Super & Quantum Tanks")
    @Config.DefaultString("+0{min=INT_MAX}")
    public static String quantumTankOutputAmountModification;
    @Config.Ignore
    public static final Supplier<MathOp> QUANTUM_TANK_OUTPUT_AMOUNT = op(() -> quantumTankOutputAmountModification);

    @Config.Comment("the value modification applied to the aspect orb from Ashroom of Thaumic Bases")
    @Config.DefaultString("*100")
    public static String ashroomAspectOrbValueModification;
    @Config.Ignore
    public static final Supplier<MathOp> ASHROOM_ASPECT_ORB = op(() -> ashroomAspectOrbValueModification);

    @Config.Comment("the value modification applied to the drop count of Aspect Orbs from Ashroom of Thaumic Bases")
    @Config.DefaultString("*6")
    public static String ashroomDropCountModification;
    @Config.Ignore
    public static final Supplier<MathOp> ASHROOM_DROP_COUNT = op(() -> ashroomDropCountModification);

    @Config.Comment("the value modification applied to the max extract amount of Item Conduit from Ender IO")
    @Config.DefaultString("+0{min=INT_MAX}")
    public static String enderIoItemConduitMaxExtractAmountModification;
    @Config.Ignore
    public static final Supplier<MathOp> ITEM_CONDUIT_MAX_EXTRACT = op(() -> enderIoItemConduitMaxExtractAmountModification);

    @Config.Comment("the value modification applied to the max extract amount of Liquid (Fluid) Conduit from Ender IO")
    @Config.DefaultString("+0{min=INT_MAX}")
    public static String enderIoLiquidConduitMaxExtractAmountModification;
    @Config.Ignore
    public static final Supplier<MathOp> LIQUID_CONDUIT_MAX_EXTRACT = op(() -> enderIoLiquidConduitMaxExtractAmountModification);

    @Config.Comment("the value modification applied to the max insert amount of Liquid (Fluid) Conduit from Ender IO")
    @Config.DefaultString("+0{min=INT_MAX}")
    public static String enderIoLiquidConduitMaxInsertAmountModification;
    @Config.Ignore
    public static final Supplier<MathOp> LIQUID_CONDUIT_MAX_INSERT = op(() -> enderIoLiquidConduitMaxInsertAmountModification);

    @Config.Comment({
        "the list of MTE-internal accelerated classes (FQCN)",
        "GENERATED, DON'T EDIT!"})
    @Config.DefaultStringList({})
    public static String[] mteAccelerationClass;
    @Config.Ignore
    public static final Supplier<Set<Class<?>>> MTE_ACCELERATION_CLASS = dynamicMapping(
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
