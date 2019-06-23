package gtsharp.gtsharp.recipe;

import gregtech.api.unification.material.Materials;
import gtsharp.gtsharp.GTSharpMaterials;

public class MachineRecipeLoader {

    public static void init() {
        registerPressurizerRecipes();
    }

    private static void registerPressurizerRecipes() {

        GTSharpRecipeMap.PRESSURIZER_RECIPES.recipeBuilder()
                .fluidInputs(Materials.DistilledWater.getFluid(10))
                .fluidOutputs(GTSharpMaterials.highPressureWater.getFluid(3))
                .duration(60)
                .EUt(30)
                .buildAndRegister();
    }
}
