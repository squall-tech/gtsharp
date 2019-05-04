package gtsharp.gtsharp.items;

import gregtech.api.items.materialitem.MaterialMetaItem;
import gregtech.api.items.metaitem.FluidStats;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.unification.stack.MaterialStack;
import gtsharp.gtsharp.api.items.metaitem.FuelRodStats;

import static gtsharp.gtsharp.items.GTSharpMetaItems.FUEL_ROD;
import static gtsharp.gtsharp.items.GTSharpMetaItems.INVAR_FLUID_CELL;

public class GTSharpMetaItem extends MaterialMetaItem {

    @Override
    public void registerSubItems() {

        INVAR_FLUID_CELL = addItem(3405, "invar_fluid_cell")
                .addStats(new FluidStats(144, Integer.MIN_VALUE, Integer.MAX_VALUE, true))
                .setMaxStackSize(16)
                .setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Invar, OrePrefix.plate.materialAmount * 2L + 2L * OrePrefix.ring.materialAmount)));

        FUEL_ROD = addItem(3406, "fuel_rod")
                .addStats(new FuelRodStats())
                .setMaxStackSize(16)
                .setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Invar, OrePrefix.plate.materialAmount * 2L + 2L * OrePrefix.ring.materialAmount)));

    }
}
