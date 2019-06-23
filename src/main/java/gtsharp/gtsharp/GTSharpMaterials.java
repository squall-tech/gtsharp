package gtsharp.gtsharp;


import com.google.common.collect.ImmutableList;
import gregtech.api.unification.material.IMaterialHandler;
import gregtech.api.unification.material.MaterialIconSet;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.unification.material.type.Material;
import gtsharp.gtsharp.config.GTSharpConfig;

@IMaterialHandler.RegisterMaterialHandler
public class GTSharpMaterials {

    public static FluidMaterial highPressureWater = new FluidMaterial(500, "high_pressure_water", 5053, MaterialIconSet.FLUID, ImmutableList.of(), Material.MatFlags.DISABLE_DECOMPOSITION);
    public static FluidMaterial highPressureBoilingWater = new FluidMaterial(501, "high_pressure_boiling_water", 13844253, MaterialIconSet.FLUID, ImmutableList.of(), Material.MatFlags.DISABLE_DECOMPOSITION);

    static {
        highPressureBoilingWater.setFluidTemperature(548);
    }
}
