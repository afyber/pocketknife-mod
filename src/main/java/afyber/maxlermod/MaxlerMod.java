package afyber.maxlermod;

import afyber.maxlermod.entity.HomingArrow;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
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
        try {
            // I am going insane
            HomingArrow.xTile = EntityArrow.class.getDeclaredField("field_145791_d");
            HomingArrow.xTile.setAccessible(true);
            HomingArrow.yTile = EntityArrow.class.getDeclaredField("field_145792_e");
            HomingArrow.yTile.setAccessible(true);
            HomingArrow.zTile = EntityArrow.class.getDeclaredField("field_145789_f");
            HomingArrow.zTile.setAccessible(true);
            HomingArrow.inTile = EntityArrow.class.getDeclaredField("field_145790_g");
            HomingArrow.inTile.setAccessible(true);
            HomingArrow.inData = EntityArrow.class.getDeclaredField("field_70253_h");
            HomingArrow.inData.setAccessible(true);
            HomingArrow.ticksInGround = EntityArrow.class.getDeclaredField("field_70252_j");
            HomingArrow.ticksInGround.setAccessible(true);
            HomingArrow.ticksInAir = EntityArrow.class.getDeclaredField("field_70257_an");
            HomingArrow.ticksInAir.setAccessible(true);
            HomingArrow.customPotionEffects = EntityTippedArrow.class.getDeclaredField("field_184561_h");
            HomingArrow.customPotionEffects.setAccessible(true);
            HomingArrow.potion = EntityTippedArrow.class.getDeclaredField("field_184560_g");
            HomingArrow.potion.setAccessible(true);
            HomingArrow.COLOR = EntityTippedArrow.class.getDeclaredField("field_184559_f");
            HomingArrow.COLOR.setAccessible(true);

            HomingArrow.spawnPotionParticles = EntityTippedArrow.class.getDeclaredMethod("func_184556_b", int.class);
            HomingArrow.spawnPotionParticles.setAccessible(true);
        }
        catch (NoSuchFieldException | NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
    }
}
