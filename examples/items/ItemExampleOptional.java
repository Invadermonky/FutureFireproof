import com.invadermonky.futurefireproof.api.IFireproofItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Optional;

/**
 * An example of how to implement an optional dependency for FutureFireproof.
 * <p>
 * This removes the required dependency, but still allows the item to be fireproof if FutureFireproof is installed.
 */
@Optional.Interface(modid = "futurefireproof", iface = "com.invadermonky.futurefireproof.api.IFireproofItem", striprefs = true)
public class ItemExampleOptional extends Item implements IFireproofItem {

    /**
     * If you override any methods from {@link IFireproofItem}, be sure to mark them as optional using Forge's
     * {@link Optional.Method} annotation.
     */
    @Optional.Method(modid = "futurefireproof")
    @Override
    public int getPerTickLavaDecay() {
        return IFireproofItem.super.getPerTickLavaDecay();
    }
}
