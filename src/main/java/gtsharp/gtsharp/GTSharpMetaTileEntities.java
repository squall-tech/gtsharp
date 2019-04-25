package gtsharp.gtsharp;

import gregtech.api.GregTechAPI;
import gtsharp.gtsharp.metatileentities.MetaTileEntityFissionReactor;
import net.minecraft.util.ResourceLocation;

public class GTSharpMetaTileEntities {

    public static MetaTileEntityFissionReactor META_TILE_ENTITY_FISSION_REACTOR;

    public static void init() {
        META_TILE_ENTITY_FISSION_REACTOR = GregTechAPI.registerMetaTileEntity(3512, new MetaTileEntityFissionReactor(getGTSharpId("fission_reactor")));
    }


    private static ResourceLocation getGTSharpId(String name) {
        return new ResourceLocation(GTSharpMod.MODID, name);
    }
}
