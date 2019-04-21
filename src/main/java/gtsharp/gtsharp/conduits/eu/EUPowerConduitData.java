package gtsharp.gtsharp.conduits.eu;

import com.enderio.core.client.render.IconUtil;
import crazypants.enderio.base.conduit.IConduitTexture;
import crazypants.enderio.base.conduit.geom.CollidableComponent;
import crazypants.enderio.conduits.conduit.power.PowerConduit;
import crazypants.enderio.conduits.render.ConduitTextureWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

import static gtsharp.gtsharp.conduits.GTSharpConduits.item_eu_power_conduit;

public class EUPowerConduitData implements IEUPowerConduitData {

    private int id;

    public EUPowerConduitData(int id) {
        this.id = id;
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public int getMaxEnergyIO() {
        return 0;
    }

    @Nonnull
    @Override
    public ItemStack createItemStackForSubtype() {
        return new ItemStack(item_eu_power_conduit.getItemNN(), 1, getID());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public @Nonnull IConduitTexture getTextureForState(@Nonnull CollidableComponent component) {
        if (component.isCore()) {
            return EUPowerConduit.ICONS.get(EUPowerConduit.ICON_CORE_KEY + EUPowerConduit.POSTFIX[getID()]);
        }
        if (PowerConduit.COLOR_CONTROLLER_ID.equals(component.data)) {
            return new ConduitTextureWrapper(IconUtil.instance.whiteTexture);
        }
        return EUPowerConduit.ICONS.get(EUPowerConduit.ICON_KEY + EUPowerConduit.POSTFIX[getID()]);
    }
}
