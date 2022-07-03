package afyber.maxlermod;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = MaxlerMod.MOD_ID, name = MaxlerMod.MOD_NAME, version = MaxlerMod.MOD_VERSION, dependencies = "required-after:galacticraftcore;required-after:appliedenergistics2")
public class MaxlerMod
{
    public static final String MOD_ID = "maxlermod";
    public static final String MOD_NAME = "Maxler Mod";
    public static final String MOD_VERSION = "0.0.1.0";

    private static Logger logger;

    public static Logger logger() {
        return logger;
    }

    public static Configuration config;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        File directory = event.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "maxlermod.cfg"));
        MaxlerConfig.readConfig();
        AE2Api.setupAPI();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // empty, for now
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if (config.hasChanged()) {
            config.save();
        }
    }
}
