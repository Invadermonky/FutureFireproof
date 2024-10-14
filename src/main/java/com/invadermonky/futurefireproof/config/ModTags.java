package com.invadermonky.futurefireproof.config;

import gnu.trove.map.hash.THashMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class ModTags {
    public static THashMap<String, Integer> FIREPROOF_ITEMS;

    public static boolean isFireproofItem(ItemStack stack) {
        if(FIREPROOF_ITEMS == null) {
            syncConfig();
        }
        String itemId = stack.getItem().getRegistryName().toString();
        return FIREPROOF_ITEMS.containsKey(itemId) || FIREPROOF_ITEMS.containsKey(itemId + ":" + stack.getMetadata());
    }

    public static int getFireproofDecayRate(ItemStack stack) {
        if(FIREPROOF_ITEMS == null) {
            syncConfig();
        }
        String itemId = stack.getItem().getRegistryName().toString();
        return Math.max(FIREPROOF_ITEMS.getOrDefault(itemId, 1), FIREPROOF_ITEMS.getOrDefault(itemId + ":" + stack.getMetadata(), 1));
    }

    public static void syncConfig() {
        if(FIREPROOF_ITEMS == null) {
            FIREPROOF_ITEMS = new THashMap<>(ConfigHandlerFF.fireproofItems.length);
        }
        FIREPROOF_ITEMS.clear();

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
