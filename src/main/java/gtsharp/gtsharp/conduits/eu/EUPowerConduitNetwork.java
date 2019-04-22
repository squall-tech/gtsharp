package gtsharp.gtsharp.conduits.eu;

import crazypants.enderio.conduits.conduit.AbstractConduitNetwork;
import crazypants.enderio.conduits.conduit.power.IPowerConduit;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;

public class EUPowerConduitNetwork extends AbstractConduitNetwork<IEUPowerConduit, IEUPowerConduit> {

    public EUPowerConduitNetwork() {
        super(IEUPowerConduit.class,IEUPowerConduit.class);
    }

    public void powerReceptorAdded(EUPowerConduit euPowerConduit, EnumFacing direction, BlockPos p) {
    }

    public void powerReceptorRemoved(int x, int y, int z) {
    }

    @Override
    public void addConduit(@Nonnull IEUPowerConduit con) {
        super.addConduit(con);
        con.setActive(true);
    }
}
