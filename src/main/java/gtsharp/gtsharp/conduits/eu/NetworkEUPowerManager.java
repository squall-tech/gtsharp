package gtsharp.gtsharp.conduits.eu;

import crazypants.enderio.base.power.IPowerInterface;
import crazypants.enderio.conduits.conduit.power.IPowerConduit;
import net.minecraft.profiler.Profiler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class NetworkEUPowerManager {

    long maxEnergyStored;
    long energyStored;

    private EUPowerConduitNetwork network;
    private boolean receptorsDirty;
    private final List<EUPowerConduitNetwork.ReceptorEntry> receptors = new ArrayList<>();
    private ListIterator<EUPowerConduitNetwork.ReceptorEntry> receptorIterator = receptors.listIterator();


    public NetworkEUPowerManager(EUPowerConduitNetwork network) {
        this.network = network;
    }


    public void doApplyRecievedPower(@Nullable Profiler profiler) {
        checkReceptors();
        updateNetworkStorage();
        long available = energyStored;
        long wasAvailable = available;

        for (EUPowerConduitNetwork.ReceptorEntry r : receptors) {
            IPowerInterface pp = r.getPowerInterface();
            int canOffer = (int) Math.min(r.emmiter.getMaxEnergyExtracted(r.direction), available);
            int used = pp.receiveEnergy(canOffer, false);
            available -= used;
            if (available <= 0) {
                break;
            }
        }
        long used = wasAvailable - available;
        energyStored -= used;

        distributeStorageToConduits();
    }


    private void checkReceptors() {
        if (!receptorsDirty) {
            return;
        }
        receptors.clear();
//        storageReceptors.clear();
        for (EUPowerConduitNetwork.ReceptorEntry rec : network.getPowerReceptors()) {
            final IPowerInterface powerInterface = rec.getPowerInterface();
            if (powerInterface != null) {
//                if (powerInterface.getProvider() instanceof IPowerStorage) {
//                    storageReceptors.add(rec);
//                } else {
                receptors.add(rec);
//                }
//            } else {
//                // we can ignore that connection here, but the conduit should also update and remove its external connection
//                rec.emmiter.setConnectionsDirty();
//            }
            }
            receptorIterator = receptors.listIterator();

            receptorsDirty = false;
        }
    }

    private void distributeStorageToConduits() {
        if (maxEnergyStored <= 0 || energyStored <= 0) {
            for (IPowerConduit con : network.getConduits()) {
                con.setEnergyStored(0);
            }
            return;
        }
        energyStored = energyStored < 0 ? 0 : energyStored > maxEnergyStored ? maxEnergyStored : energyStored;

        float filledRatio = (float) energyStored / maxEnergyStored;
        long energyLeft = energyStored;

        for (IPowerConduit con : network.getConduits()) {
            if (energyLeft > 0) {
                // NB: use ceil to ensure we dont through away any energy due to
                // rounding
                // errors
                int give = (int) Math.ceil(con.getMaxEnergyStored() * filledRatio);
                give = Math.min(give, con.getMaxEnergyStored());
                give = Math.min(give, (int) Math.min(Integer.MAX_VALUE, energyLeft));
                con.setEnergyStored(give);
                energyLeft -= give;
            } else {
                con.setEnergyStored(0);
            }
        }
    }

    public void applyRecievedPower(Profiler profiler) {
        doApplyRecievedPower(profiler);
    }

    public void receptorsChanged() {
        this.receptorsDirty = true;
    }

    private void updateNetworkStorage() {
        maxEnergyStored = 0;
        energyStored = 0;
        for (IEUPowerConduit con : network.getConduits()) {
            maxEnergyStored += con.getMaxEnergyStored();
            con.onTick();
            energyStored += con.getEnergyStored();
        }
        energyStored = energyStored < 0 ? 0 : energyStored > maxEnergyStored ? maxEnergyStored : energyStored;
    }
}
