package gtsharp.gtsharp;


import gtsharp.gtsharp.block.GTSharpMetaBlocks;
import gtsharp.gtsharp.capabilities.CapabilityFuelRod;
import gtsharp.gtsharp.items.GTSharpMetaItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = GTSharpMod.MODID, name = GTSharpMod.NAME, version = GTSharpMod.VERSION, dependencies = "required-after:gregtech")
public class GTSharpMod {

    public static final String MODID = "gtsharp";
    public static final String NAME = "Gregtech Sharp";
    public static final String VERSION = "1.0";


    public static boolean euConduits = false;

    public ResourceLocation resourceLocation;

    private static Logger logger;


    @SidedProxy(
            modId = MODID,
            clientSide = "gtsharp.gtsharp.ClientProxy",
            serverSide = "gtsharp.gtsharp.CommonProxy"
    )
    private static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        this.resourceLocation = new ResourceLocation("gtsharp", "gtsharpcapability");
        logger = event.getModLog();
        GTSharpMetaItems.init();
        GTSharpTextures.init();
        GTSharpMetaBlocks.init();
        GTSharpMetaTileEntities.init();
    }


    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {

    }

    @SubscribeEvent
    public void attachItemCapability(final AttachCapabilitiesEvent<ItemStack> event) {
        event.addCapability(this.resourceLocation, new CapabilityFuelRod());
    }


    public static Logger getLogger() {
        return logger;
    }
}
