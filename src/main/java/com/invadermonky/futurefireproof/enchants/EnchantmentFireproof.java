package com.invadermonky.futurefireproof.enchants;

import com.invadermonky.futurefireproof.FutureFireproof;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentFireproof extends Enchantment {
    private static final String NAME = "fireproof";

    public EnchantmentFireproof() {
        super(Rarity.COMMON, EnumEnchantmentType.ALL, EntityEquipmentSlot.values());
        this.setRegistryName(FutureFireproof.MOD_ID, NAME);
        this.setName(NAME);
    }
}
