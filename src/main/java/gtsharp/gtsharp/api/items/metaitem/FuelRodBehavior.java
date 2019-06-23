package gtsharp.gtsharp.api.items.metaitem;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.metaitem.stats.IItemDurabilityManager;
import gregtech.api.items.metaitem.stats.IItemMaxStackSizeProvider;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.common.items.behaviors.AbstractMaterialPartBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class FuelRodBehavior extends AbstractMaterialPartBehavior implements IItemMaxStackSizeProvider {


    public static FuelRodBehavior getInstanceFor(ItemStack itemStack) {
        if(!(itemStack.getItem() instanceof MetaItem)) {
            return null;
        }
        MetaItem<?> metaItem = (MetaItem<?>) itemStack.getItem();
        MetaItem.MetaValueItem valueItem = metaItem.getItem(itemStack);
        if(valueItem == null) {
            return null;
        }
        IItemDurabilityManager durabilityManager = valueItem.getDurabilityManager();
        if(!(durabilityManager instanceof FuelRodBehavior)) {
            return null;
        }
        return (FuelRodBehavior) durabilityManager;
    }

    public IngotMaterial getPartMaterial(ItemStack itemStack) {
        NBTTagCompound compound = this.getPartStatsTag(itemStack);
        IngotMaterial defaultMaterial = Materials.Uranium;
        if (compound != null && compound.hasKey("Material", 8)) {
            String materialName = compound.getString("Material");
            Material material = Material.MATERIAL_REGISTRY.getObject(materialName);
            return !(material instanceof IngotMaterial) ? defaultMaterial : (IngotMaterial)material;
        } else {
            return defaultMaterial;
        }
    }

    public int getFuelEfficiency(ItemStack itemStack) {
        FuelRodMaterial rodMaterial = FuelRodMaterial.findMaterial(getPartMaterial(itemStack));
        if (rodMaterial != null) {
            return rodMaterial.getFuelEfficiency();
        }
        return 512;
    }

    public float getHeatPerTick(ItemStack itemStack){
        FuelRodMaterial rodMaterial = FuelRodMaterial.findMaterial(getPartMaterial(itemStack));
        if (rodMaterial != null) {
            return rodMaterial.getHeatPerTick();
        }
        return 1.0f;
    }

    public boolean isConsumable(ItemStack itemStack) {
        FuelRodMaterial rodMaterial = FuelRodMaterial.findMaterial(getPartMaterial(itemStack));
        if (rodMaterial != null) {
            return rodMaterial.isConsumable();
        }
        return true;
    }

    @Override
    public int getPartMaxDurability(ItemStack itemStack) {
        FuelRodMaterial rodMaterial = FuelRodMaterial.findMaterial(getPartMaterial(itemStack));
        if (rodMaterial != null) {
            return rodMaterial.getMaxDurability();
        }
        return 36000;
    }

    @Override
    public int getMaxStackSize(ItemStack itemStack, int i) {
        return 1;
    }


    @Override
    public int getItemStackColor(ItemStack itemStack, int tintIndex) {
        IngotMaterial material = this.getPartMaterial(itemStack);
        return tintIndex % 2 == 1 ? material.materialRGB : 0xFFFFFF;
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity player, int timer, boolean isInHand) {
        //TODO change behavior
//        FuelRodBehavior behavior = getInstanceFor(itemStack);
//        if (behavior != null) {
//            if (isInHand) {
//                player.attackEntityFrom(DamageSource.GENERIC, 1);
//            }
//        }
    }
}
