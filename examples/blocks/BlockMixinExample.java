import com.invadermonky.futurefireproof.api.IFireproofBlock;
import net.minecraftforge.fml.common.Optional;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Using mixins, fireproof logic can easily be injected into items belonging to other mods.
 * <p>
 * However, if the item the mixin is injecting into has a custom EntityItem, the fireproof logic likely will not fire
 * correctly. This is intentional to avoid compatibility issues resulting from EntityItems that have custom logic or
 * rendering.
 * <p>
 * The {@link IFireproofBlock} interface being injected can be marked as optional as well, removing the required
 * dependency on FutureFireproof.
 */
@Mixin(value = BlockExample.class)
@Optional.Interface(modid = "futurefireproof", iface = "com.invadermonky.futurefireproof.api.IFireproofItem", striprefs = true)
public class BlockMixinExample implements IFireproofBlock {


    /**
     * Override the classes as needed and mark them as optional if there is no require dependency.
     */
    @Optional.Method(modid = "futurefireproof")
    @Override
    public int getPerTickLavaDecay() {
        return 1;
    }
}
