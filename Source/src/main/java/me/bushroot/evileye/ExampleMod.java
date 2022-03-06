package me.bushroot.evileye;

import me.bushroot.evileye.Evil.EvilEye;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.Mod;

@Mod(modid = "evileye", name = "EvilEye Mod", version = "1.0")
public class ExampleMod {
    public static final String MODID = "evileye";
    public static final String NAME = "EvilEye Mod";
    public static final String VERSION = "1.0";
    private static Logger logger;

    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        ExampleMod.logger = event.getModLog();
    }

    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        new EvilEye().start();
    }
}
