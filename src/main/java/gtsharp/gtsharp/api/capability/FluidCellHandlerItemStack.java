package gtsharp.gtsharp.api.capability;

import gregtech.api.unification.material.type.IngotMaterial;
import gtsharp.gtsharp.api.items.metaitem.FluidCellBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FluidCellHandlerItemStack implements IFluidHandlerItem, ICapabilityProvider {

    public static final String FLUID_NBT_KEY = "Fluid";

    @Nonnull
    protected ItemStack container;
    protected FluidCellBehavior fluidCellBehavior;

    /**
     * @param container  The container itemStack, data is stored on it directly as NBT.
     */
    public FluidCellHandlerItemStack(@Nonnull ItemStack container, FluidCellBehavior fluidCellBehavior)
    {
        this.container = container;
        this.fluidCellBehavior = fluidCellBehavior;
    }

    @Nonnull
    @Override
    public ItemStack getContainer()
    {
        return container;
    }

    @Nullable
    public FluidStack getFluid()
    {
        NBTTagCompound tagCompound = container.getTagCompound();
        if (tagCompound == null || !tagCompound.hasKey(FLUID_NBT_KEY))
        {
            return null;
        }
        return FluidStack.loadFluidStackFromNBT(tagCompound.getCompoundTag(FLUID_NBT_KEY));
    }

    protected void setFluid(FluidStack fluid)
    {
        if (!container.hasTagCompound())
        {
            container.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound fluidTag = new NBTTagCompound();
        fluid.writeToNBT(fluidTag);
        container.getTagCompound().setTag(FLUID_NBT_KEY, fluidTag);
    }

    @Override
    public IFluidTankProperties[] getTankProperties()
    {
        return new FluidTankProperties[] { new FluidTankProperties(getFluid(), getCapacity()) };
    }

    @Override
    public int fill(FluidStack resource, boolean doFill)
    {
        if (container.getCount() != 1 || resource == null || resource.amount <= 0 || !canFillFluidType(resource))
        {
            return 0;
        }

        FluidStack contained = getFluid();
        if (contained == null)
        {
            int fillAmount = Math.min(getCapacity(), resource.amount);

            if (doFill)
            {
                FluidStack filled = resource.copy();
                filled.amount = fillAmount;
                setFluid(filled);
            }

            return fillAmount;
        }
        else
        {
            if (contained.isFluidEqual(resource))
            {
                int fillAmount = Math.min(getCapacity() - contained.amount, resource.amount);

                if (doFill && fillAmount > 0) {
                    contained.amount += fillAmount;
                    setFluid(contained);
                }

                return fillAmount;
            }

            return 0;
        }
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain)
    {
        if (container.getCount() != 1 || resource == null || resource.amount <= 0 || !resource.isFluidEqual(getFluid()))
        {
            return null;
        }
        return drain(resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain)
    {
        if (container.getCount() != 1 || maxDrain <= 0)
        {
            return null;
        }

        FluidStack contained = getFluid();
        if (contained == null || contained.amount <= 0 || !canDrainFluidType(contained))
        {
            return null;
        }

        final int drainAmount = Math.min(contained.amount, maxDrain);

        FluidStack drained = contained.copy();
        drained.amount = drainAmount;

        if (doDrain)
        {
            contained.amount -= drainAmount;
            if (contained.amount == 0)
            {
                setContainerToEmpty();
            }
            else
            {
                setFluid(contained);
            }
        }

        return drained;
    }

    public boolean canFillFluidType(FluidStack fluid) {
        int liquidTemperature = fluid.getFluid().getTemperature();
        return liquidTemperature >= this.getMinFluidTemperaturey() && liquidTemperature <= this.getMaxFluidTemperature();
    }

    public boolean canDrainFluidType(FluidStack fluid)
    {
        return true;
    }

    /**
     * Override this method for special handling.
     * Can be used to swap out or destroy the container.
     */
    protected void setContainerToEmpty()
    {
        container.getTagCompound().removeTag(FLUID_NBT_KEY);
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY ? (T) this : null;
    }


    public int getCapacity() {
        IngotMaterial partMaterial = fluidCellBehavior.getPartMaterial(container);
        return partMaterial.cellProperties.maxCapacity;
    }

    public int getMinFluidTemperaturey() {
        IngotMaterial partMaterial = fluidCellBehavior.getPartMaterial(container);
        return partMaterial.cellProperties.minFluidTemperature;
    }

    public int getMaxFluidTemperature() {
        IngotMaterial partMaterial = fluidCellBehavior.getPartMaterial(container);
        return partMaterial.cellProperties.maxFluidTemperature;
    }

}
