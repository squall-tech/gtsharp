package gtsharp.gtsharp.capabilities;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityFuelRod implements ICapabilityProvider {

    @CapabilityInject(IFuelRod.class)
    public static Capability<IFuelRod> FUEL_ROD = null;


    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == FUEL_ROD;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return  capability == FUEL_ROD ? (T) FUEL_ROD : null;
    }
}
