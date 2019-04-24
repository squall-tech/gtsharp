package gtsharp.gtsharp;


import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = GTSharpMod.MODID, name = GTSharpMod.NAME, version = GTSharpMod.VERSION)
public class GTSharpMod {

    public static final String MODID = "gtsharp";
    public static final String NAME = "Gregtech Sharp";
    public static final String VERSION = "1.0";


    public static boolean euConduits = false;

    private static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }


    public static Logger getLogger() {
        return logger;
    }
}
