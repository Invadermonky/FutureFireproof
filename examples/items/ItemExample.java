import com.invadermonky.futurefireproof.api.IFireproofItem;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * An example of how to implement the IFireproofItem interface, allowing your items to survive being submerged in lava.
 * <p>
 * None of the {@link IFireproofItem} methods need to be overridden, but it can be done if you desire to implement your
 * own logic.
 */
public class ItemExample extends Item implements IFireproofItem {


    /**
     * This is the default item decay rate in lava. A return value of 1 will result in the item existing in lava for
     * the full 6000 ticks (5 minutes). Returning 2 will cause it to decay twice as fast, only lasting 3000 ticks
     * (2.5 minutes).
     */
    @Override
    public int getPerTickLavaDecay() {
        return 1;
    }

    /**
     * An ItemStack sensitive version of {@link IFireproofItem#getPerTickLavaDecay()}
     */
    @Override
    public int getPerTickLavaDecay(ItemStack stack) {
        return this.getPerTickLavaDecay();
    }

    /**
     * The actual item being queried when submerged in lava or inside fire. You can use this method to directly manipulate
     * the {@link EntityItem}.
     */
    @Override
    public int getPerTickLavaDecay(EntityItem entityItem) {
        return IFireproofItem.super.getPerTickLavaDecay(entityItem);
    }
}
