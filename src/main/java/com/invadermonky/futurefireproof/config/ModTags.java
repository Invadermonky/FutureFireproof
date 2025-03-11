package com.invadermonky.futurefireproof.config;

import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.THashSet;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;

import java.util.Arrays;

public class ModTags {
    public static THashSet<String> DAMAGE_TYPES = new THashSet<>();
    public static THashMap<String, Integer> FIREPROOF_ITEMS = new THashMap<>();

    public static boolean isIgnoredDamageType(DamageSource source) {
        return DAMAGE_TYPES.contains(source.getDamageType()) && source != DamageSource.OUT_OF_WORLD;
    }

    public static boolean isFireproofItem(ItemStack stack) {
        String itemId = stack.getItem().getRegistryName().toString();
        return FIREPROOF_ITEMS.containsKey(itemId) || FIREPROOF_ITEMS.containsKey(itemId + ":" + stack.getMetadata());
    }

    public static int getFireproofDecayRate(ItemStack stack) {
        String itemId = stack.getItem().getRegistryName().toString();
        return Math.max(FIREPROOF_ITEMS.getOrDefault(itemId, 1), FIREPROOF_ITEMS.getOrDefault(itemId + ":" + stack.getMetadata(), 1));
    }

    public static void syncConfig() {
        DAMAGE_TYPES.clear();
        FIREPROOF_ITEMS.clear();

        DAMAGE_TYPES.addAll(Arrays.asList(ConfigHandlerFF.damageTypes));

        for(String str : ConfigHandlerFF.fireproofItems) {
            if(str.matches(".+=[0-9]+")) {
                String[] split = str.split("=");
                FIREPROOF_ITEMS.put(split[0], MathHelper.clamp(Integer.parseInt(split[1]), 1, 6000));
            } else {
                FIREPROOF_ITEMS.put(str, 1);
            }
        }
    }
}
