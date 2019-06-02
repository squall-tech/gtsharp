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
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static gtsharp.gtsharp.items.GTSharpMetaItems.FUEL_ROD;
import static gtsharp.gtsharp.items.GTSharpMetaItems.INVAR_FLUID_CELL;
import static gtsharp.gtsharp.items.GTSharpMetaItems.MASS_ENERGY_CONVERSION_INTERFACE;

public class GTSharpMetaItem extends MaterialMetaItem {

    static List<IngotMaterial> rodFuels = new ArrayList<>();

    static {
        rodFuels.add(Materials.Uranium);
        rodFuels.add(Materials.Plutonium);
        rodFuels.add(Materials.NaquadahEnriched);
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

        MASS_ENERGY_CONVERSION_INTERFACE = addItem(3407, "mass_energy_conversion_interface");
    }

    public void registerRecipes() {

        rodFuels.forEach(material -> {
            ItemStack stackRod = FUEL_ROD.getStackForm();
           // IngotMaterial material = Materials.Uranium;
            FuelRodBehavior.getInstanceFor(stackRod).setPartMaterial(stackRod, material);
            ModHandler.addShapedRecipe(String.format("fuel_rod_%s", material.toString()),
                    stackRod,
                    "   ", "   ", "X X",
                    'X', new UnificationEntry(OrePrefix.ingot, material));
        });
    }
}
