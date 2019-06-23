package gtsharp.gtsharp.api.items.metaitem;

import gregtech.api.unification.material.type.IngotMaterial;

import java.util.ArrayList;
import java.util.List;

public class FuelRodMaterial {

    private static List<FuelRodMaterial> materialList = new ArrayList<>();
    private final IngotMaterial ingotMaterial;
    private final int fuelEfficiency;
    private final int maxDurability;
    private final float heatPerTick;
    private final boolean consumable;

    public FuelRodMaterial(IngotMaterial ingotMaterial, int fuelEfficiency, int maxDurability, float heatPerTick, boolean consumable) {
        this.ingotMaterial = ingotMaterial;
        this.fuelEfficiency = fuelEfficiency;
        this.maxDurability = maxDurability;
        this.heatPerTick = heatPerTick;
        this.consumable = consumable;
    }


    public IngotMaterial getIngotMaterial() {
        return ingotMaterial;
    }

    public int getFuelEfficiency() {
        return fuelEfficiency;
    }

    public float getHeatPerTick() {
        return heatPerTick;
    }

    public int getMaxDurability() {
        return maxDurability;
    }

    public boolean isConsumable() {
        return consumable;
    }

    public static void registerFuelRod(FuelRodMaterial fuelRodMaterial) {
        materialList.add(fuelRodMaterial);
    }

    public static List<FuelRodMaterial> getMaterials() {
        return materialList;
    }

    public static FuelRodMaterial findMaterial(IngotMaterial ingotMaterial) {
        return materialList.stream().filter(fuelRodMaterial -> fuelRodMaterial.getIngotMaterial().equals(ingotMaterial)).findFirst().get();
    }
}
