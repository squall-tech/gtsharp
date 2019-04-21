package gtsharp.gtsharp.conduits.eu;

import com.enderio.core.api.client.gui.ITabPanel;
import com.enderio.core.common.util.DyeColor;
import com.enderio.core.common.vecmath.Vector4f;
import crazypants.enderio.base.EnderIO;
import crazypants.enderio.base.conduit.*;
import crazypants.enderio.base.conduit.geom.CollidableComponent;
import crazypants.enderio.base.machine.modes.RedstoneControlMode;
import crazypants.enderio.base.render.registry.TextureRegistry;
import crazypants.enderio.conduits.conduit.AbstractConduit;
import crazypants.enderio.conduits.gui.PowerSettings;
import crazypants.enderio.conduits.render.ConduitTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class EUPowerConduit  extends AbstractConduit implements IEUPowerConduit {


    public static final
    String ICON_KEY = "blocks/eu_power_conduit";
    public static final
    String ICON_CORE_KEY = "blocks/eu_power_conduit_core";

    static final Map<String, IConduitTexture> ICONS = new HashMap<>();

    static final String[] POSTFIX = new String[]{"_tungstensteel", "_hssg", "_naquadah", "_naquadah_alloy"};

    public static void registerTextures() {
        int i = 0;
        for (String pf : POSTFIX) {
            ICONS.put(ICON_KEY + pf, new ConduitTexture(TextureRegistry.registerTexture(EnderIO.DOMAIN+ ":" + ICON_KEY,false), ConduitTexture.arm(i)));
            ICONS.put(ICON_CORE_KEY + pf, new ConduitTexture(TextureRegistry.registerTexture(EnderIO.DOMAIN + ":" +"blocks/eu_conduit_core_0",false), ConduitTexture.core(i++)));
        }
    }


    private IEUPowerConduitData subtype = new EUPowerConduitData(0);

    private EUPowerConduitNetwork network;
    protected final EnumMap<EnumFacing, RedstoneControlMode> rsModes = new EnumMap<EnumFacing, RedstoneControlMode>(EnumFacing.class);
    protected final EnumMap<EnumFacing, DyeColor> rsColors = new EnumMap<EnumFacing, DyeColor>(EnumFacing.class);

    public EUPowerConduit() {
    }

    public EUPowerConduit(IEUPowerConduitData subtype) {
        this.subtype = subtype;
    }


    @Override
    public void writeToNBT(@Nonnull NBTTagCompound nbtRoot) {
        super.writeToNBT(nbtRoot);
        nbtRoot.setShort("subtype", (short) this.subtype.getID());
    }


    @Override
    public void readFromNBT(@Nonnull NBTTagCompound nbtRoot) {
        super.readFromNBT(nbtRoot);
        this.subtype = IEUPowerConduitData.Registry.fromID(nbtRoot.getShort("subtype"));
    }

    @Nonnull
    @Override
    public IConduitTexture getTextureForState(@Nonnull CollidableComponent component) {
        return subtype.getTextureForState(component);
    }

    @Nullable
    @Override
    public IConduitTexture getTransmitionTextureForState(@Nonnull CollidableComponent component) {
        return null;
    }

    @Nullable
    @Override
    public Vector4f getTransmitionTextureColorForState(@Nonnull CollidableComponent component) {
        return null;
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    public ITabPanel createGuiPanel(@Nonnull IGuiExternalConnection gui, @Nonnull IClientConduit con) {
        return new PowerSettings(gui, con);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean updateGuiPanel(@Nonnull ITabPanel panel) {
        if (panel instanceof PowerSettings) {
            return ((PowerSettings) panel).updateConduit(this);
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getGuiPanelTabOrder() {
        return 3;
    }

    @Override
    public void setExtractionRedstoneMode(@Nonnull RedstoneControlMode mode, @Nonnull EnumFacing dir) {
        rsModes.put(dir, mode);
        setClientStateDirty();
    }

    @Override
    @Nonnull
    public RedstoneControlMode getExtractionRedstoneMode(@Nonnull EnumFacing dir) {
        RedstoneControlMode res = rsModes.get(dir);
        if (res == null) {
            res = RedstoneControlMode.IGNORE;
        }
        return res;
    }

    @Override
    public void setExtractionSignalColor(@Nonnull EnumFacing dir, @Nonnull DyeColor col) {
        rsColors.put(dir, col);
        setClientStateDirty();
    }

    @Override
    @Nonnull
    public DyeColor getExtractionSignalColor(@Nonnull EnumFacing dir) {
        DyeColor res = rsColors.get(dir);
        if (res == null) {
            res = DyeColor.RED;
        }
        return res;
    }

    @Nonnull
    @Override
    public IConduitNetwork<?, ?> createNetworkForType() {
        return new EUPowerConduitNetwork();
    }

    @Override
    public boolean setNetwork(@Nonnull IConduitNetwork<?, ?> network) {
        this.network = (EUPowerConduitNetwork) network;
        return super.setNetwork(network);
    }

    @Nullable
    @Override
    public IConduitNetwork<?, ?> getNetwork() throws NullPointerException {
        return network;
    }

    @Override
    public void clearNetwork() {
        this.network = null;
    }

    @Nonnull
    @Override
    public Class<? extends IConduit> getBaseConduitType() {
        return IEUPowerConduit.class;
    }

    @Nonnull
    @Override
    public ItemStack createItem() {
        return subtype.createItemStackForSubtype();
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public int getEnergyStored() {
        return 0;
    }

    @Override
    public int getMaxEnergyStored() {
        return 0;
    }

    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public boolean canReceive() {
        return true;
    }
}
