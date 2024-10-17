package com.invadermonky.futurefireproof.util;

import com.invadermonky.futurefireproof.api.IFireproofItem;
import com.invadermonky.futurefireproof.config.ConfigHandlerFF;
import com.invadermonky.futurefireproof.config.ModTags;
import com.invadermonky.futurefireproof.registry.RegistrarFF;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

public class FireproofHelper {
    public static boolean isFireproofItem(ItemStack stack) {
        return stack.getItem() instanceof IFireproofItem || (ConfigHandlerFF.enableFireproofEnchant && EnchantmentHelper.getEnchantmentLevel(RegistrarFF.ENCHANTMENT_FIREPROOF, stack) > 0) || ModTags.isFireproofItem(stack);
    }

    public static int getLavaDecayRate(ItemStack stack) {
        if((ConfigHandlerFF.enableFireproofEnchant && EnchantmentHelper.getEnchantmentLevel(RegistrarFF.ENCHANTMENT_FIREPROOF, stack) > 0)) {
            return 1;
        } else if(stack.getItem() instanceof IFireproofItem) {
            return ((IFireproofItem) stack.getItem()).getPerTickLavaDecay();
        } else {
            return ModTags.getFireproofDecayRate(stack);
        }
    }
}
