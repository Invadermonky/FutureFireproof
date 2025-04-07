package com.invadermonky.futurefireproof.util;

import com.invadermonky.futurefireproof.api.IFireproofItem;
import com.invadermonky.futurefireproof.config.ConfigHandlerFF;
import com.invadermonky.futurefireproof.config.ModTags;
import com.invadermonky.futurefireproof.registry.RegistrarFF;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

public class FireproofHelper {
    public static boolean isFireproofItem(ItemStack stack) {
        if (stack.isEmpty())
            return false;
        else if (stack.getItem() instanceof IFireproofItem)
            return true;
        else if (Block.getBlockFromItem(stack.getItem()) instanceof IFireproofItem)
            return true;
        else if (ConfigHandlerFF.enableFireproofEnchant && EnchantmentHelper.getEnchantmentLevel(RegistrarFF.ENCHANTMENT_FIREPROOF, stack) > 0)
            return true;
        else
            return ModTags.isFireproofItem(stack);
    }

    public static int getLavaDecayRate(EntityItem entityItem) {
        ItemStack stack = entityItem.getItem();
        if ((ConfigHandlerFF.enableFireproofEnchant && EnchantmentHelper.getEnchantmentLevel(RegistrarFF.ENCHANTMENT_FIREPROOF, stack) > 0)) {
            return 1;
        } else if (stack.getItem() instanceof IFireproofItem) {
            return ((IFireproofItem) stack.getItem()).getPerTickLavaDecay(entityItem);
        } else if (Block.getBlockFromItem(stack.getItem()) instanceof IFireproofItem) {
            return ((IFireproofItem) Block.getBlockFromItem(stack.getItem())).getPerTickLavaDecay(entityItem);
        } else {
            return ModTags.getFireproofDecayRate(stack);
        }
    }
}
