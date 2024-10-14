package com.invadermonky.futurefireproof.registry;

import com.invadermonky.futurefireproof.FutureFireproof;
import com.invadermonky.futurefireproof.config.ConfigHandlerFF;
import com.invadermonky.futurefireproof.enchants.EnchantmentFireproof;
import com.invadermonky.futurefireproof.entity.EntityFireproofItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod.EventBusSubscriber(modid = FutureFireproof.MOD_ID)
public class RegistrarFF {
    public final static Enchantment ENCHANTMENT_FIREPROOF = new EnchantmentFireproof();

    @SubscribeEvent
    public static void registerEnchants(RegistryEvent.Register<Enchantment> event) {
        if(ConfigHandlerFF.enableFireproofEnchant) {
            event.getRegistry().register(ENCHANTMENT_FIREPROOF);
        }
    }

    public static void registerEntities() {
        EntityRegistry.registerModEntity(
                new ResourceLocation(FutureFireproof.MOD_ID, "fireproof_item"),
                EntityFireproofItem.class,
                "fireproof_item",
                100,
                FutureFireproof.instance,
                50,
                1,
                true
        );
    }
}
