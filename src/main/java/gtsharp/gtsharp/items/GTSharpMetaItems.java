package gtsharp.gtsharp.items;

import gregtech.api.items.metaitem.MetaItem;

public class GTSharpMetaItems {

    public static MetaItem<?>.MetaValueItem INVAR_FLUID_CELL;
    public static MetaItem<?>.MetaValueItem FUEL_ROD;

    public static void init() {
        GTSharpMetaItem gtSharpMetaItem = new GTSharpMetaItem();
        gtSharpMetaItem.setRegistryName("gtsharp_metaitem");

    }
}
