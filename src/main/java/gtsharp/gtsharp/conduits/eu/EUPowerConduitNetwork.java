package gtsharp.gtsharp.conduits.eu;

import crazypants.enderio.base.conduit.ConduitUtil;
import crazypants.enderio.base.conduit.IConduitBundle;
import crazypants.enderio.base.power.IPowerInterface;
import crazypants.enderio.base.power.PowerHandlerUtil;
import crazypants.enderio.conduits.conduit.AbstractConduitNetwork;
import crazypants.enderio.conduits.conduit.power.IPowerConduit;
import crazypants.enderio.conduits.conduit.power.PowerConduitNetwork;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class EUPowerConduitNetwork extends AbstractConduitNetwork<IEUPowerConduit, IEUPowerConduit> {

    private NetworkEUPowerManager powerManager;

    private final Map<ReceptorKey, ReceptorEntry> powerReceptors = new HashMap<>();

    public EUPowerConduitNetwork() {
        super(IEUPowerConduit.class,IEUPowerConduit.class);
    }

    public void powerReceptorAdded(EUPowerConduit euPowerConduit, EnumFacing direction, BlockPos pos) {
        EUPowerConduitNetwork.ReceptorKey key = new EUPowerConduitNetwork.ReceptorKey(pos, direction);
        EUPowerConduitNetwork.ReceptorEntry re = powerReceptors.get(key);
        if (re == null) {
            re = new EUPowerConduitNetwork.ReceptorEntry(pos, euPowerConduit, direction);
            powerReceptors.put(key, re);
        }
        if (powerManager != null) {
            powerManager.receptorsChanged();
        }
    }

    public void powerReceptorRemoved(int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        List<ReceptorKey> remove = new ArrayList<>();
        for (ReceptorKey key : powerReceptors.keySet()) {
            if (key != null && key.pos.equals(pos)) {
                remove.add(key);
            }
        }
        for (ReceptorKey key : remove) {
            powerReceptors.remove(key);
        }
        powerManager.receptorsChanged();
    }

    @Override
    public void init(@Nonnull IConduitBundle tile, Collection<IEUPowerConduit> connections, @Nonnull World world) throws ConduitUtil.UnloadedBlockException {
        super.init(tile, connections, world);
        powerManager = new NetworkEUPowerManager(this);
    }

    @Override
    public void addConduit(@Nonnull IEUPowerConduit con) {
        super.addConduit(con);
        con.setActive(true);
    }

    @Override
    public void tickEnd(TickEvent.ServerTickEvent event, @Nullable Profiler profiler) {
        powerManager.applyRecievedPower(profiler);
    }

    public Collection<ReceptorEntry>  getPowerReceptors() {
        return powerReceptors.values();
    }


    public static class ReceptorEntry {

        final @Nonnull IEUPowerConduit emmiter;
        final @Nonnull BlockPos pos;
        final @Nonnull EnumFacing direction;

        public ReceptorEntry(@Nonnull BlockPos pos, @Nonnull IEUPowerConduit emmiter, @Nonnull EnumFacing direction) {
            this.pos = pos;
            this.emmiter = emmiter;
            this.direction = direction;
        }

        @Nullable
        IPowerInterface getPowerInterface() {
            return PowerHandlerUtil.getPowerInterface(emmiter.getBundle().getBundleworld().getTileEntity(pos), direction.getOpposite());
        }

    }


    public static class ReceptorKey {
        BlockPos pos;
        EnumFacing direction;

        ReceptorKey(@Nonnull BlockPos pos, @Nonnull EnumFacing direction) {
            this.pos = pos;
            this.direction = direction;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((pos == null) ? 0 : pos.hashCode());
            result = prime * result + ((direction == null) ? 0 : direction.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            EUPowerConduitNetwork.ReceptorKey other = (EUPowerConduitNetwork.ReceptorKey) obj;
            if (pos == null) {
                if (other.pos != null) {
                    return false;
                }
            } else if (!pos.equals(other.pos)) {
                return false;
            }
            if (direction != other.direction) {
                return false;
            }
            return true;
        }

    }
}
