package gtsharp.gtsharp.items;

import gregtech.api.items.metaitem.MetaItem;

import java.util.List;

public class GTSharpMetaItems {

    private static List<MetaItem<?>> ITEMS = MetaItem.getMetaItems();

    public static MetaItem<?>.MetaValueItem FLUID_CELL;
    public static MetaItem<?>.MetaValueItem FUEL_ROD;

    public static void init() {
        GTSharpMetaItem gtSharpMetaItem = new GTSharpMetaItem();
        gtSharpMetaItem.setRegistryName("gtsharp_metaitem");
    }

    public static void registerRecipes() {
        for (MetaItem<?> item : ITEMS) {
            if (item instanceof GTSharpMetaItem)
                ((GTSharpMetaItem) item).registerRecipes();
        }
    }
}
