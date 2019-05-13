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

    @Override
    public int getPartMaxDurability(ItemStack itemStack) {
        return 10000;
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

    }
}
