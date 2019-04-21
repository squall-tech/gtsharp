package gtsharp.gtsharp.conduits.eu;

import com.enderio.core.common.util.NullHelper;
import crazypants.enderio.base.conduit.IConduitTexture;
import crazypants.enderio.base.conduit.geom.CollidableComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public interface IEUPowerConduitData {

    class Registry {

        private static final @Nonnull
        List<IEUPowerConduitData> data = new ArrayList<>();
        private static final @Nonnull IEUPowerConduitData fallback;

        static {
            register(fallback = new EUPowerConduitData(0));
            register(new EUPowerConduitData(1));
        }

        public static @Nonnull IEUPowerConduitData fromID(int id) {
            return NullHelper.first(data.get(MathHelper.clamp(id, 0, data.size() - 1)), fallback);
        }

        public static void register(@Nonnull IEUPowerConduitData pcd) {
            while (pcd.getID() >= data.size()) {
                data.add(null);
            }
            if (data.get(pcd.getID()) != null) {
                throw new RuntimeException("Cannot register power conduit with ID " + pcd.getID() + ".");
            }
            data.set(pcd.getID(), pcd);
        }

    }

    int getID();

    int getMaxEnergyIO();

    @Nonnull
    ItemStack createItemStackForSubtype();

    @SideOnly(Side.CLIENT)
    @Nonnull
    IConduitTexture getTextureForState(@Nonnull CollidableComponent component);

}
