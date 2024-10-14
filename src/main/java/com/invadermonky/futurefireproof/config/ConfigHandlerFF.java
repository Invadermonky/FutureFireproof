package com.invadermonky.futurefireproof.config;

import com.invadermonky.futurefireproof.FutureFireproof;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = FutureFireproof.MOD_ID)
public class ConfigHandlerFF {
    @Config.RequiresMcRestart
    @Config.Comment("Enables the Fireproof item enchant.")
    public static boolean enableFireproofEnchant = true;

    @Config.Comment("Items that will be considered fireproof.\n" +
            "Format: <modid>:<itemid>:[meta]=[decayrate]\n" +
            "  modid:itemid (required) - the item id\n" +
            "  meta (optional) - numerical meta value\n" +
            "  decayrate (optional) - How fast the item decays per tick in lava\n" +
            "Examples:\n" +
            "  minecraft:stick\n" +
            "  minecraft:stick:0\n" +
            "  minecraft:stick=5\n" +
            "  minecraft:stick:0=5")
    public static String[] fireproofItems = new String[] {};

    @Mod.EventBusSubscriber(modid = FutureFireproof.MOD_ID)
    public static class ConfigChangeListener {
        @SubscribeEvent
        public static void onConfigChange(ConfigChangedEvent.OnConfigChangedEvent event) {
            if(event.getModID().equals(FutureFireproof.MOD_ID)) {
                ConfigManager.sync(FutureFireproof.MOD_ID, Config.Type.INSTANCE);
                ModTags.syncConfig();
            }
        }
    }
}
