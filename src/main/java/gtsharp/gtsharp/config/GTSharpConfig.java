package gtsharp.gtsharp.config;

import gtsharp.gtsharp.GTSharpMod;
import net.minecraftforge.common.config.Config;

@Config(modid = GTSharpMod.MODID)
public class GTSharpConfig {

    @Config.Comment("do reactor explosions [Default : true]")
    public static boolean doExplosions = true;
}
