package gtsharp.gtsharp.conduits.eu;

import crazypants.enderio.base.conduit.IClientConduit;
import crazypants.enderio.base.conduit.IExtractor;
import crazypants.enderio.base.conduit.IServerConduit;
import net.minecraftforge.energy.IEnergyStorage;

public interface IEUPowerConduit extends IEnergyStorage, IExtractor, IServerConduit, IClientConduit {
}
