package gtsharp.gtsharp.items;

import gregtech.api.items.materialitem.MaterialMetaItem;
import gregtech.api.items.metaitem.FluidStats;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.UnificationEntry;
import gtsharp.gtsharp.api.items.metaitem.FuelRodBehavior;
import gtsharp.gtsharp.api.items.metaitem.FuelRodMaterial;
import net.minecraft.item.ItemStack;

import static gtsharp.gtsharp.items.GTSharpMetaItems.FUEL_ROD;
import static gtsharp.gtsharp.items.GTSharpMetaItems.INVAR_FLUID_CELL;

public class GTSharpMetaItem extends MaterialMetaItem {

    static {
        FuelRodMaterial.registerFuelRod(new FuelRodMaterial(Materials.Uranium, 512, 36000, 1, true));
        FuelRodMaterial.registerFuelRod(new FuelRodMaterial(Materials.Uranium235, 2048, 36000, 2, true));
        FuelRodMaterial.registerFuelRod(new FuelRodMaterial(Materials.Plutonium, 4096, 36000, 10, true));
        FuelRodMaterial.registerFuelRod(new FuelRodMaterial(Materials.NaquadahEnriched, 8192, 36000, 10, true));
        FuelRodMaterial.registerFuelRod(new FuelRodMaterial(Materials.Naquadria, 32768, 36000, 20, true));

        FuelRodMaterial.registerFuelRod(new FuelRodMaterial(Materials.Silver, -1024, 1, -2, false));
        FuelRodMaterial.registerFuelRod(new FuelRodMaterial(Materials.Cadmium, -2048, 1, -4, false));
        FuelRodMaterial.registerFuelRod(new FuelRodMaterial(Materials.Iridium, -10240, 1, -20, false));
    }

    @Override
    public void registerSubItems() {

        INVAR_FLUID_CELL = addItem(3405, "invar_fluid_cell")
                .addStats(new FluidStats(144, Integer.MIN_VALUE, Integer.MAX_VALUE, true))
                .setMaxStackSize(16)
                .setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Invar, OrePrefix.plate.materialAmount * 2L + 2L * OrePrefix.ring.materialAmount)));

        FUEL_ROD = addItem(3406, "fuel_rod")
                .addStats(new FuelRodBehavior())
                .setMaxStackSize(16)
                .setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Invar, OrePrefix.plate.materialAmount * 2L + 2L * OrePrefix.ring.materialAmount)));

    }

    public void registerRecipes() {
        FuelRodMaterial.getMaterials().forEach(fuelRodMaterial -> {
            IngotMaterial material = fuelRodMaterial.getIngotMaterial();
            ItemStack stackRod = FUEL_ROD.getStackForm();
            // IngotMaterial material = Materials.Uranium;
            FuelRodBehavior.getInstanceFor(stackRod).setPartMaterial(stackRod, material);
            ModHandler.addShapedRecipe(String.format("fuel_rod_%s", material.toString()),
                    stackRod,
                    "XX ", "   ", "   ",
                    'X', new UnificationEntry(OrePrefix.ingot, material));
        });
    }
}
