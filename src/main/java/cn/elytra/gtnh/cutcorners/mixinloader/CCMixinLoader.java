package cn.elytra.gtnh.cutcorners.mixinloader;

import com.google.common.collect.Lists;
import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
@LateMixin
public class CCMixinLoader implements ILateMixinLoader {

    @Override
    public String getMixinConfig() {
        return "mixins.GTNHModify_CutCorners.late.json";
    }

    @Override
    public @NotNull List<String> getMixins(Set<String> loadedMods) {
        // read the mod init states
        Mods.init(loadedMods);

        var mixins = Lists.<String>newArrayList();

        if (Mods.GregTech.isLoaded()) {
            // GregTech
            mixins.add("gregtech.GT_MetaTileEntity_ScannerMixin");
            mixins.add("gregtech.GT_MetaTileEntity_MinerMixin");
            mixins.add("gregtech.GT_MetaTileEntity_MultiFurnaceMixin");
            mixins.add("gregtech.GT_MetaTileEntity_DrillerBaseMixin");
            mixins.add("gregtech.GT_DrillingLogicDelegateMixin");
            mixins.add("gregtech.EyeOfHarmonyRecipeAccessor");
            mixins.add("gregtech.EyeOfHarmonyFrontendFixMixin");

            mixins.add("gregtech.MTESteamFurnaceMixin");
            mixins.add("gregtech.MTEBoilerMixin");
            mixins.add("gregtech.MTECleanroomMixin");
            mixins.add("gregtech.MTEBasicMachineMixin");
            mixins.add("gregtech.MTEExtremeEntityCrusherMixin");
            mixins.add("gregtech.MTERockBreakerMixin");
            mixins.add("gregtech.BaseItemTickableMixin");
            mixins.add("gregtech.TileEntityDecayablesChestMixin");
            mixins.add("gregtech.MTEPurificationPlantMixin");
            mixins.add("gregtech.MTEAssemblyLineMixin");

            mixins.add("gregtech.RecipeMapMixin");
            mixins.add("gregtech.RecipeMapBackendMixin");
        }
        if(Mods.NHCore.isLoaded()) {
            // fix divided by zero error
            mixins.add("nhcore.DTPFCalculatorMixin");
        }
        if (Mods.Thaumcraft.isLoaded()) {
            mixins.add("thaumcraft.TileAlchemyFurnaceMixin");
            mixins.add("thaumcraft.TileNodeMixin");
        }
        if (Mods.GtnhIntergalactic.isLoaded()) {
            mixins.add("gtnhintergalactic.TileEntityModuleMinerMixin");
        }
        if (Mods.Botania.isLoaded()) {
            mixins.add("botania.TileSpreaderMixin");
        }
        if (Mods.Railcraft.isLoaded()) {
            mixins.add("railcraft.CokeOvenRecipeAccessor");
            mixins.add("railcraft.BlastFurnaceRecipeAccessor");

            mixins.add("railcraft.TileCokeOvenMixin");
        }
        if (Mods.EtFuturumRequiem.isLoaded()) {
            mixins.add("et_futurum_requiem.BlastFurnaceAndSmoker_Mixin");
        }
        if (Mods.TConstruct.isLoaded()) {
            mixins.add("tconstruct.SmeltryLogicMixin");
        }

        return mixins;
    }
}
