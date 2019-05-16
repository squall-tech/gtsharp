package gtsharp.gtsharp;

import gregtech.api.GregTechAPI;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.render.Textures;
import gtsharp.gtsharp.metatileentities.MetaTileEntityFissionReactor;
import gtsharp.gtsharp.metatileentities.MetaTileEntityHeatExchanger;
import gtsharp.gtsharp.metatileentities.MetaTileEntityLargeTurbine;
import gtsharp.gtsharp.recipe.GTSharpRecipeMap;
import net.minecraft.util.ResourceLocation;

public class GTSharpMetaTileEntities {

    public static MetaTileEntityFissionReactor META_TILE_ENTITY_FISSION_REACTOR;
    public static SimpleMachineMetaTileEntity META_TILE_ENTITY_PRESSURIZER;
    public static MetaTileEntityHeatExchanger META_TILE_ENTITY_HEAT_EXCHANGER;
    public static MetaTileEntityLargeTurbine META_TILE_ENTITY_LARGE_TURBINE;

    public static void init() {
        META_TILE_ENTITY_FISSION_REACTOR = GregTechAPI.registerMetaTileEntity(3512, new MetaTileEntityFissionReactor(getGTSharpId("fission_reactor")));

        META_TILE_ENTITY_PRESSURIZER = GregTechAPI.registerMetaTileEntity(3513, new SimpleMachineMetaTileEntity(getGTSharpId("pressurizer.lv"),
                GTSharpRecipeMap.PRESSURIZER_RECIPES,
                Textures.POLARIZER_OVERLAY,
                1));

        META_TILE_ENTITY_HEAT_EXCHANGER = GregTechAPI.registerMetaTileEntity(3514, new MetaTileEntityHeatExchanger(getGTSharpId("heat_exchanger")));

        META_TILE_ENTITY_LARGE_TURBINE = GregTechAPI.registerMetaTileEntity(3515, new MetaTileEntityLargeTurbine(getGTSharpId("steam_turbine")));

    }


    private static ResourceLocation getGTSharpId(String name) {
        return new ResourceLocation(GTSharpMod.MODID, name);
    }
}
