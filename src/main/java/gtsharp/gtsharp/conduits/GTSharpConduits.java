package gtsharp.gtsharp.conduits;

import com.enderio.core.common.util.NullHelper;
import crazypants.enderio.api.IModObject;
import crazypants.enderio.api.IModTileEntity;
import crazypants.enderio.base.init.IModObjectBase;
import crazypants.enderio.base.init.ModObjectRegistry;
import crazypants.enderio.base.init.RegisterModObject;
import gtsharp.gtsharp.GTSharpMod;
import gtsharp.gtsharp.conduits.eu.ItemEUPowerConduit;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.BiFunction;
import java.util.function.Function;


@Mod.EventBusSubscriber(modid = GTSharpMod.MODID)
public enum GTSharpConduits implements IModObjectBase {

    item_eu_power_conduit(ItemEUPowerConduit::create);

    protected @Nullable Block block;
    protected @Nullable Item item;

    final @Nonnull String unlocalisedName;
    protected final @Nullable Function<IModObject, Block> blockMaker;
    protected final @Nullable BiFunction<IModObject, Block, Item> itemMaker;
    protected final @Nullable IModTileEntity modTileEntity;


    GTSharpConduits(@Nonnull BiFunction<IModObject, Block, Item> itemMaker) {
        this(null, itemMaker, null);
    }


    GTSharpConduits(@Nullable Function<IModObject, Block> blockMaker, @Nullable BiFunction<IModObject, Block, Item> itemMaker,
                    @Nullable IModTileEntity modTileEntity) {
        this.unlocalisedName = ModObjectRegistry.sanitizeName(NullHelper.notnullJ(name(), "Enum.name()"));
        this.blockMaker = blockMaker;
        this.itemMaker = itemMaker;
        if (blockMaker == null && itemMaker == null) {
            throw new RuntimeException(this + " unexpectedly is neither a Block nor an Item.");
        }
        this.modTileEntity = modTileEntity;
    }

    @SubscribeEvent
    public static void registerBlocksEarly(@Nonnull RegisterModObject event) {
        event.register(GTSharpConduits.class);
    }


    @Override
    public final @Nullable Block getBlock() {
        return block;
    }

    @Override
    public final @Nullable Item getItem() {
        return item;
    }

    @Nonnull
    @Override
    public String getUnlocalisedName() {
        return this.unlocalisedName;
    }

    @Nonnull
    @Override
    public Class<?> getClazz() {
        return null;
    }

    @Nullable
    @Override
    public String getBlockMethodName() {
        return null;
    }

    @Nullable
    @Override
    public String getItemMethodName() {
        return null;
    }

    @Nullable
    @Override
    public IModTileEntity getTileEntity() {
        return modTileEntity;
    }

    @Override
    public void setItem(@Nullable Item obj) {
        item = obj;
    }

    @Override
    public void setBlock(@Nullable Block obj) {
        block = obj;
    }

    @Override
    public @Nonnull Function<IModObject, Block> getBlockCreator() {
        return blockMaker != null ? blockMaker : mo -> null;
    }

    @Override
    public @Nonnull BiFunction<IModObject, Block, Item> getItemCreator() {
        return NullHelper.first(itemMaker, IModObject.WithBlockItem.itemCreator);
    }
}
