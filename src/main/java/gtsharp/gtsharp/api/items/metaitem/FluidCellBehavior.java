package gtsharp.gtsharp.api.items.metaitem;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.metaitem.stats.IItemCapabilityProvider;
import gregtech.api.items.metaitem.stats.IItemColorProvider;
import gregtech.api.items.metaitem.stats.IItemNameProvider;
import gregtech.api.items.metaitem.stats.IMetaItemStats;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.material.type.Material;
import gtsharp.gtsharp.api.capability.FluidCellHandlerItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class FluidCellBehavior implements IMetaItemStats, IItemCapabilityProvider, IItemNameProvider, IItemColorProvider {

    public IngotMaterial getPartMaterial(ItemStack itemStack) {
        NBTTagCompound compound = this.getPartStatsTag(itemStack);
        IngotMaterial defaultMaterial = Materials.WroughtIron;
        if (compound != null && compound.hasKey("Material", 8)) {
            String materialName = compound.getString("Material");
            Material material = Material.MATERIAL_REGISTRY.getObject(materialName);
            return !(material instanceof IngotMaterial) ? defaultMaterial : (IngotMaterial)material;
        } else {
            return defaultMaterial;
        }
    }

    @Override
    public int getItemStackColor(ItemStack itemStack, int i) {
        IngotMaterial partMaterial = getPartMaterial(itemStack);
        if (i == 1) {
            return 0xFFFFFF;
        }
        return partMaterial.materialRGB;
    }

    @Override
    public ICapabilityProvider createProvider(ItemStack itemStack) {
        return new FluidCellHandlerItemStack(itemStack, this);
    }

    protected NBTTagCompound getPartStatsTag(ItemStack itemStack) {
        return itemStack.getSubCompound("GT.PartStats");
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemStack, String s) {
        IngotMaterial partMaterial = getPartMaterial(itemStack);
        return partMaterial.getLocalizedName() + " Fluid Cell " + partMaterial.cellProperties.getMaxCapacity() + "mb";
    }

    public static FluidCellBehavior getInstanceFor(ItemStack itemStack) {
        if(!(itemStack.getItem() instanceof MetaItem)) {
            return null;
        }
        MetaItem<?> metaItem = (MetaItem<?>) itemStack.getItem();
        MetaItem.MetaValueItem valueItem = metaItem.getItem(itemStack);
        if(valueItem == null) {
            return null;
        }
        IItemColorProvider durabilityManager = valueItem.getColorProvider();
        if(!(durabilityManager instanceof FluidCellBehavior)) {
            return null;
        }
        return (FluidCellBehavior)durabilityManager;
    }

    public void setPartMaterial(ItemStack itemStack, IngotMaterial material) {
        NBTTagCompound compound = getOrCreatePartStatsTag(itemStack);
        compound.setString("Material", material.toString());
    }

    protected NBTTagCompound getOrCreatePartStatsTag(ItemStack itemStack) {
        return itemStack.getOrCreateSubCompound("GT.PartStats");
    }
}
