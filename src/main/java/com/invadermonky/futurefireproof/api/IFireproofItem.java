package com.invadermonky.futurefireproof.api;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

/**
 *
 */
public interface IFireproofItem {
    /**
     * How fast the item will decay when in lava or fire. A value of 1 will have the vanilla item decay rate. A value of 2
     * will cause the item to decay twice as fast.
     */
    default int getPerTickLavaDecay() {
        return 1;
    }

    /**
     * <p>
     * An ItemStack sensitive version of {@link IFireproofItem#getPerTickLavaDecay()}.
     * </p>
     *
     * <p>
     * Determines how fast the item will decay when in lava or fire. A value of 1 will use the vanilla item decay rate.
     * A value of 2 will cause the item to decay twice as fast.
     * </p>
     *
     * <p>
     * This method also directly links to the {@link EntityItem#getItem()} object, allowing direct manipulation of the
     * ItemStack (such as durability damage). This will be called every tick while the item is in lava or fire.
     * </p>
     */
    default int getPerTickLavaDecay(ItemStack stack) {
        return this.getPerTickLavaDecay();
    }

    /**
     * <p>
     * An {@link EntityItem} sensitive version of {@link IFireproofItem#getPerTickLavaDecay()}.
     * </p>
     *
     * <p>
     * Using this method allows direct access to the EntityItem being queried. This will be fired every tick the item
     * is in lava or fire.
     * </p>
     */
    default int getPerTickLavaDecay(EntityItem entityItem) {
        return this.getPerTickLavaDecay(entityItem.getItem());
    }
}
