package gtsharp.gtsharp.conduits.eu;

import crazypants.enderio.api.IModObject;
import crazypants.enderio.base.EnderIO;
import crazypants.enderio.base.conduit.ConduitDisplayMode;
import crazypants.enderio.base.conduit.IConduit;
import crazypants.enderio.base.conduit.IServerConduit;
import crazypants.enderio.base.conduit.geom.Offset;
import crazypants.enderio.base.conduit.registry.ConduitBuilder;
import crazypants.enderio.base.conduit.registry.ConduitRegistry;
import crazypants.enderio.base.gui.IconEIO;
import crazypants.enderio.conduits.conduit.AbstractItemConduit;
import crazypants.enderio.conduits.conduit.ItemConduitSubtype;
import crazypants.enderio.conduits.render.ConduitBundleRenderManager;
import gtsharp.gtsharp.GTSharpMod;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemEUPowerConduit extends AbstractItemConduit {

    public static ItemEUPowerConduit create(@Nonnull IModObject modObject, @Nullable Block block) {
        if (GTSharpMod.euConduits)
            return new ItemEUPowerConduit(modObject);
        else
            return null;
    }

    protected ItemEUPowerConduit(@Nonnull IModObject modObject) {
        super(modObject,
                new ItemConduitSubtype(modObject.getUnlocalisedName() + "_tungstensteel", modObject.getRegistryName().toString() + "_tungstensteel"),
                new ItemConduitSubtype(modObject.getUnlocalisedName() + "_hssg", modObject.getRegistryName().toString() + "_hssg"),
                new ItemConduitSubtype(modObject.getUnlocalisedName() + "_naquadah", modObject.getRegistryName().toString() + "_naquadah"),
                new ItemConduitSubtype(modObject.getUnlocalisedName() + "_naquadah_alloy", modObject.getRegistryName().toString() + "_naquadah_alloy")
        );

        ConduitRegistry.register(ConduitBuilder.start().setUUID(new ResourceLocation(EnderIO.DOMAIN, "eu_power")).setClass(getBaseConduitType())
                .setOffsets(Offset.EAST_DOWN, Offset.SOUTH_UP, Offset.NORTH_EAST, Offset.EAST_UP).build().setUUID(new ResourceLocation(EnderIO.DOMAIN, "eu_power_conduit"))
                .setClass(EUPowerConduit.class).build().finish());

        ConduitDisplayMode.registerDisplayMode(new ConduitDisplayMode(getBaseConduitType(), IconEIO.WRENCH_OVERLAY_ME, IconEIO.WRENCH_OVERLAY_ME_OFF));
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void registerRenderers(@Nonnull IModObject modObject) {
        super.registerRenderers(modObject);
        ConduitBundleRenderManager.instance.getConduitBundleRenderer().registerRenderer(new EUPowerConduitRenderer());
    }

    @Nonnull
    @Override
    public Class<? extends IConduit> getBaseConduitType() {
        return IEUPowerConduit.class;
    }

    @Override
    public IServerConduit createConduit(@Nonnull ItemStack stack, @Nonnull EntityPlayer player) {
        return new EUPowerConduit(IEUPowerConduitData.Registry.fromID(stack.getItemDamage()));
    }

    @Override
    public boolean shouldHideFacades(@Nonnull ItemStack stack, @Nonnull EntityPlayer player) {
        return true;
    }
}
