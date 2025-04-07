import com.invadermonky.futurefireproof.api.IFireproofBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.Optional;

/**
 * An example of how to implement an optional dependency for FutureFireproof.
 * <p>
 * This removes the required dependency, but still allows the item to be fireproof if FutureFireproof is installed.
 */
@Optional.Interface(modid = "futurefireproof", iface = "com.invadermonky.futurefireproof.api.IFireproofBlock", striprefs = true)
public class BlockExampleOptional extends Block implements IFireproofBlock {


    public BlockExampleOptional(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
    }

    /**
     * If you override any methods from {@link IFireproofBlock}, be sure to mark them as optional using Forge's
     * {@link Optional.Method} annotation.
     */
    @Optional.Method(modid = "futurefireproof")
    @Override
    public int getPerTickLavaDecay() {
        return 1;
    }
}
