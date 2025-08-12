package com.invadermonky.futurefireproof.enchants;

import com.invadermonky.futurefireproof.FutureFireproof;
import com.invadermonky.futurefireproof.api.IFireproofItem;
import com.invadermonky.futurefireproof.config.ConfigHandlerFF;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentFireproof extends Enchantment {
    private static final String NAME = "fireproof";

    public EnchantmentFireproof() {
        super(Rarity.COMMON, EnumEnchantmentType.ALL, EntityEquipmentSlot.values());
        this.setRegistryName(FutureFireproof.MOD_ID, NAME);
        this.setName(NAME);
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return ConfigHandlerFF.fireproofEnchantMinEnchantability;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return ConfigHandlerFF.fireproofEnchantMaxEnchantability;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return !(stack.getItem() instanceof IFireproofItem && ((IFireproofItem) stack.getItem()).getPerTickLavaDecay() <= 1) && super.canApply(stack);
    }
}
