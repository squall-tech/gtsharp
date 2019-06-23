package gtsharp.gtsharp.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gtsharp.gtsharp.GTSharpMod;
import net.minecraftforge.fml.common.Loader;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.file.Path;

public abstract class ConfigHandler<T> {

    private static Path modPath = Loader.instance().getConfigDir().toPath().resolve(GTSharpMod.MODID);
    public final Gson gson;
    public final File configFile;

    private Class<T> type;

    public ConfigHandler(String fileName, Class<T> type) {
        this.configFile = new File(modPath.toFile(), fileName + ".json");
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        this.gson = gsonBuilder.create();
        this.type = type;
    }

    public void init(){
        if (!configFile.exists()){
            if (!modPath.toFile().exists()) {
                modPath.toFile().mkdir();
            }
            try{
                PrintWriter writer = new PrintWriter(configFile);
                writer.print(gson.toJson(createDefaultFile()));
                writer.close();
            } catch (Exception ex) {
                GTSharpMod.getLogger().error("Error on create default config file {}", ex);
            }
        }
        try{
            this.readConfig(gson.fromJson(new FileReader(configFile), type));
        } catch (Exception ex) {
            GTSharpMod.getLogger().error("Error on read config file: {} error: {}", configFile.getName(), ex);
        }
    }

    public abstract void readConfig(T file);

    public abstract T createDefaultFile();
}
