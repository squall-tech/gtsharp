package gtsharp.gtsharp.conduits.eu;

import crazypants.enderio.conduits.conduit.AbstractConduitNetwork;

public class EUPowerConduitNetwork extends AbstractConduitNetwork<IEUPowerConduit, IEUPowerConduit> {

    public EUPowerConduitNetwork() {
        super(IEUPowerConduit.class,IEUPowerConduit.class);
    }

}
