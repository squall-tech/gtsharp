package gtsharp.gtsharp.conduits.eu;

import com.enderio.core.api.client.gui.ITabPanel;
import com.enderio.core.client.render.BoundingBox;
import com.enderio.core.common.util.DyeColor;
import com.enderio.core.common.util.NullHelper;
import com.enderio.core.common.vecmath.Vector4f;
import crazypants.enderio.base.EnderIO;
import crazypants.enderio.base.conduit.*;
import crazypants.enderio.base.conduit.geom.CollidableCache;
import crazypants.enderio.base.conduit.geom.CollidableComponent;
import crazypants.enderio.base.conduit.geom.ConduitGeometryUtil;
import crazypants.enderio.base.machine.modes.RedstoneControlMode;
import crazypants.enderio.base.power.IPowerInterface;
import crazypants.enderio.base.render.registry.TextureRegistry;
import crazypants.enderio.conduits.conduit.AbstractConduit;
import crazypants.enderio.conduits.conduit.TileConduitBundle;
import crazypants.enderio.conduits.conduit.power.IPowerConduit;
import crazypants.enderio.conduits.conduit.power.IPowerConduitData;
import crazypants.enderio.conduits.config.ConduitConfig;
import crazypants.enderio.conduits.gui.PowerSettings;
import crazypants.enderio.conduits.render.BlockStateWrapperConduitBundle;
import crazypants.enderio.conduits.render.ConduitTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

import static crazypants.enderio.base.conduit.ConnectionMode.INPUT;

public class EUPowerConduit  extends AbstractConduit implements IEUPowerConduit {


    public static final
    String ICON_KEY = "blocks/eu_power_conduit";
    public static final
    String ICON_CORE_KEY = "blocks/eu_power_conduit_core";

    static final Map<String, IConduitTexture> ICONS = new HashMap<>();

    static final String[] POSTFIX = new String[]{"_tungstensteel", "_hssg", "_naquadah", "_naquadah_alloy"};

    static {
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

        for (Map.Entry<EnumFacing, RedstoneControlMode> entry : rsModes.entrySet()) {
            if (entry.getValue() != null) {
                short ord = (short) entry.getValue().ordinal();
                nbtRoot.setShort("pRsMode." + entry.getKey().name(), ord);
            }
        }

        for (Map.Entry<EnumFacing, DyeColor> entry : rsColors.entrySet()) {
            if (entry.getValue() != null) {
                short ord = (short) entry.getValue().ordinal();
                nbtRoot.setShort("pRsCol." + entry.getKey().name(), ord);
            }
        }
    }


    @Override
    public void readFromNBT(@Nonnull NBTTagCompound nbtRoot) {
        super.readFromNBT(nbtRoot);
        this.subtype = IEUPowerConduitData.Registry.fromID(nbtRoot.getShort("subtype"));

        for (EnumFacing dir : EnumFacing.VALUES) {
            String key = "pRsMode." + dir.name();
            if (nbtRoot.hasKey(key)) {
                short ord = nbtRoot.getShort(key);
                if (ord >= 0 && ord < RedstoneControlMode.values().length) {
                    rsModes.put(dir, RedstoneControlMode.values()[ord]);
                }
            }
            key = "pRsCol." + dir.name();
            if (nbtRoot.hasKey(key)) {
                short ord = nbtRoot.getShort(key);
                if (ord >= 0 && ord < DyeColor.values().length) {
                    rsColors.put(dir, DyeColor.values()[ord]);
                }
            }
        }
    }

    @Override
    protected void readTypeSettings(@Nonnull EnumFacing dir, @Nonnull NBTTagCompound dataRoot) {
        setConnectionMode(dir, NullHelper.first(ConnectionMode.values()[dataRoot.getShort("connectionMode")], ConnectionMode.NOT_SET));
        setExtractionSignalColor(dir, NullHelper.first(DyeColor.values()[dataRoot.getShort("extractionSignalColor")], DyeColor.RED));
        setExtractionRedstoneMode(RedstoneControlMode.fromOrdinal(dataRoot.getShort("extractionRedstoneMode")), dir);
    }


    @Override
    protected void writeTypeSettingsToNbt(@Nonnull EnumFacing dir, @Nonnull NBTTagCompound dataRoot) {
        dataRoot.setShort("connectionMode", (short) getConnectionMode(dir).ordinal());
        dataRoot.setShort("extractionSignalColor", (short) getExtractionSignalColor(dir).ordinal());
        dataRoot.setShort("extractionRedstoneMode", (short) getExtractionRedstoneMode(dir).ordinal());
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

    private int storedRf = 0;

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int freeSpace = (40000 - storedRf);
        int result = Math.min(maxReceive, freeSpace);
        if (!simulate && result > 0) {
            storedRf += result;
        }
        return result;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (storedRf > 0) {
            storedRf -= Math.min(maxExtract, storedRf);
        }
        return 0;
    }

    @Override
    public int getEnergyStored() {
        return storedRf;
    }

    @Override
    public int getMaxEnergyStored() {
        return 40000;
    }

    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public boolean canReceive() {
        return true;
    }


    @Override
    public boolean canConnectToExternal(@Nonnull EnumFacing direction, boolean ignoreDisabled) {
        TileEntity te = getBundle().getEntity();
        World world = te.getWorld();
        TileEntity tileEntity = world.getTileEntity(te.getPos().offset(direction));
        if (tileEntity == null) {
            return false;
        }
        if (tileEntity instanceof IConduitBundle) {
            return false;
        }

        return tileEntity.hasCapability(CapabilityEnergy.ENERGY, direction.getOpposite());
    }

    @Override
    public boolean canConnectToConduit(@Nonnull EnumFacing direction, @Nonnull IConduit conduit) {
        boolean res = super.canConnectToConduit(direction, conduit);
        if (!res) {
            return false;
        }
        if (ConduitConfig.canDifferentTiersConnect.get()) {
            return res;
        }
        if (!(conduit instanceof IEUPowerConduit)) {
            return false;
        }
        IEUPowerConduit pc = (IEUPowerConduit) conduit;
        //return pc.getMaxEnergyStored() == getMaxEnergyStored();
        return true;
    }

    @Override
    public void externalConnectionAdded(@Nonnull EnumFacing direction) {
        super.externalConnectionAdded(direction);
        if (network != null) {
            TileEntity te = getBundle().getEntity();
            BlockPos p = te.getPos().offset(direction);
            network.powerReceptorAdded(this, direction, p);
        }
    }

    @Override
    public void externalConnectionRemoved(@Nonnull EnumFacing direction) {
        super.externalConnectionRemoved(direction);
        if (network != null) {
            TileEntity te = getBundle().getEntity();
            BlockPos p = te.getPos().offset(direction);
            network.powerReceptorRemoved(p.getX(), p.getY(), p.getZ());
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void hashCodeForModelCaching(BlockStateWrapperConduitBundle.ConduitCacheKey hashCodes) {
        super.hashCodeForModelCaching(hashCodes);
        if (subtype.getID() != 1) {

        }
        hashCodes.add(subtype.getID());
        hashCodes.addEnum(rsModes);
        hashCodes.addEnum(rsColors);
    }

    @Override
    public void onAddedToBundle() {
        for (EnumFacing dir : EnumFacing.VALUES) {
            TileEntity te = getBundle().getEntity();
            if (te instanceof TileConduitBundle) {
                IEUPowerConduit cond = ((TileConduitBundle) te).getConduit(IEUPowerConduit.class);
                if (cond != null) {
                    cond.setConnectionMode(dir.getOpposite(), ConnectionMode.IN_OUT);
                    ConduitUtil.connectConduits(cond, dir.getOpposite());
                }
            }
        }
    }


    @Override
    @Nonnull
    public Collection<CollidableComponent> createCollidables(@Nonnull CollidableCache.CacheKey key) {
        Collection<CollidableComponent> baseCollidables = super.createCollidables(key);
        final EnumFacing key_dir = key.dir;
        if (key_dir == null) {
            return baseCollidables;
        }

        BoundingBox bb = ConduitGeometryUtil.getInstance().createBoundsForConnectionController(key_dir, key.offset);
        CollidableComponent cc = new CollidableComponent(IPowerConduit.class, bb, key_dir, COLOR_CONTROLLER_ID);

        List<CollidableComponent> result = new ArrayList<>();
        result.addAll(baseCollidables);
        result.add(cc);

        return result;
    }

    @Override
    public boolean hasCapability(final Capability<?> capability, final EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return true;
        }
        return false;
    }


    @Override
    public <T> T getCapability(final Capability<T> capability, final EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return (T) this;
        }
        return null;
    }


    @Override
    public IPowerInterface getExternalPowerReceptor(@Nonnull EnumFacing direction) {
        return (IPowerInterface) this;
    }

    @Override
    public void onTick() {

    }

    @Override
    public boolean getConnectionsDirty() {
        return false;
    }

    @Override
    public void setEnergyStored(int energy) {
        storedRf = MathHelper.clamp(energy, 0, getMaxEnergyStored());
    }

    @Override
    public int getMaxEnergyRecieved(@Nonnull EnumFacing dir) {
        return getMaxEnergyIO(subtype);
    }

    @Override
    public int getMaxEnergyExtracted(EnumFacing direction) {
//        ConnectionMode mode = getConnectionMode(dir);
//        if (mode == INPUT || mode == ConnectionMode.DISABLED || !isRedstoneEnabled(dir)) {
//            return 0;
//        }
        return getMaxEnergyIO(subtype);
    }

    @Override
    public void setConnectionsDirty() {

    }

    public static int getMaxEnergyIO(IEUPowerConduitData subtype) {
        return subtype.getMaxEnergyIO();
    }
}
