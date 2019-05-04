package gtsharp.gtsharp.api.items.metaitem;

import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.items.metaitem.stats.IItemDurabilityManager;
import gregtech.api.items.metaitem.stats.IMetaItemStats;
import net.minecraft.item.ItemStack;

import java.util.List;

public class FuelRodStats implements IMetaItemStats, IItemDurabilityManager, IItemBehaviour {


    @Override
    public boolean showsDurabilityBar(ItemStack itemStack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack itemStack) {
        return 10000;
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack itemStack) {
        return 10000;
    }

    @Override
    public void addInformation(ItemStack itemStack, List<String> lines) {
        lines.add("Durability 10000");
    }
}
