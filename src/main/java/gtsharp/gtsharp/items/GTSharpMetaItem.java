package gtsharp.gtsharp.items;

import gregtech.api.items.materialitem.MaterialMetaItem;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gtsharp.gtsharp.api.items.metaitem.FluidCellBehavior;
import gtsharp.gtsharp.api.items.metaitem.FuelRodBehavior;
import net.minecraft.item.ItemStack;

import static gtsharp.gtsharp.items.GTSharpMetaItems.FLUID_CELL;
import static gtsharp.gtsharp.items.GTSharpMetaItems.FUEL_ROD;

public class GTSharpMetaItem extends MaterialMetaItem {

    @Override
    public void registerSubItems() {

        FLUID_CELL = addItem(3405, "fluid_cell")
                .addStats(new FluidCellBehavior())
                .setMaxStackSize(16);

        FUEL_ROD = addItem(3406, "fuel_rod")
                .addStats(new FuelRodBehavior())
                .setMaxStackSize(16);

    }

    public void registerRecipes() {
        for (Material material : Material.MATERIAL_REGISTRY) {
            if (material instanceof IngotMaterial) {
                IngotMaterial metalMaterial = (IngotMaterial) material;
                if (metalMaterial.cellProperties != null) {
                    ItemStack stackForm = FLUID_CELL.getStackForm();
                    FluidCellBehavior.getInstanceFor(stackForm).setPartMaterial(stackForm, metalMaterial);
                    RecipeMaps.BENDER_RECIPES.recipeBuilder()
                            .circuitMeta(16)
                            .input(OrePrefix.plate, material, 2)
                            .outputs(stackForm)
                            .duration(200).EUt(30)
                            .buildAndRegister();
                }

                if (metalMaterial.fuelRodProperties != null) {
//                    ItemStack stackForm = FUEL_ROD.getStackForm();
//                    FuelRodBehavior.getInstanceFor(stackForm).setPartMaterial(stackForm, metalMaterial);
//                    RecipeMaps.CANNER_RECIPES.recipeBuilder()
//                            .input(OrePrefix.dust, material, 2)
//                            .outputs(stackForm)
//                            .duration(200)
//                            .EUt(5)
//                            .buildAndRegister();
                //        FuelRodMaterial.getMaterials().forEach(fuelRodMaterial -> {
                //            IngotMaterial material = fuelRodMaterial.getIngotMaterial();
                //            ItemStack stackRod = FUEL_ROD.getStackForm();
                //            // IngotMaterial material = Materials.Uranium;
                //            FuelRodBehavior.getInstanceFor(stackRod).setPartMaterial(stackRod, material);
                //            ModHandler.addShapedRecipe(String.format("fuel_rod_%s", material.toString()),
                //                    stackRod,
                //                    "XX ", "   ", "   ",
                //                    'X', new UnificationEntry(OrePrefix.ingot, material));
                //        });
                }
            }
        }
    }

    static {

        Materials.Uranium.setFuelRodProperties( 512, 36000, 1, true);
        Materials.Uranium235.setFuelRodProperties( 2048, 36000, 2, true);
        Materials.Plutonium.setFuelRodProperties( 4096, 36000, 10, true);
        Materials.NaquadahEnriched.setFuelRodProperties( 8192, 36000, 10, true);
        Materials.Naquadria.setFuelRodProperties( 32768, 36000, 20, true);

        Materials.Silver.setFuelRodProperties( -1024, 1, -2, false);
        Materials.Cadmium.setFuelRodProperties( -2048, 1, -4, false);
        Materials.Iridium.setFuelRodProperties( -10240, 1, -20, false);

        Materials.Invar.setFluidCellProperties(1,Integer.MIN_VALUE, Integer.MAX_VALUE);
        Materials.Copper.setFluidCellProperties(16,Integer.MIN_VALUE, Integer.MAX_VALUE);
        Materials.Tin.setFluidCellProperties(50,Integer.MIN_VALUE, Integer.MAX_VALUE);
        Materials.Zinc.setFluidCellProperties(100,Integer.MIN_VALUE, Integer.MAX_VALUE);
        Materials.Bronze.setFluidCellProperties(144,Integer.MIN_VALUE, Integer.MAX_VALUE);
        Materials.Brass.setFluidCellProperties(500,Integer.MIN_VALUE, Integer.MAX_VALUE);
        Materials.WroughtIron.setFluidCellProperties(1000,Integer.MIN_VALUE, Integer.MAX_VALUE);
        Materials.Rubber.setFluidCellProperties(1296,Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
}
