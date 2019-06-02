package gtsharp.gtsharp.metatileentities.multiblockpart;

import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.common.metatileentities.electric.multiblockpart.MetaTileEntityMultiblockPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.List;

public class MetaTileEntityInputDisk extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<IItemHandlerModifiable> {

    public MetaTileEntityInputDisk(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder metaTileEntityHolder) {
        return new MetaTileEntityInputDisk(metaTileEntityId, getTier());
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return null;
    }

    @Override
    public MultiblockAbility<IItemHandlerModifiable> getAbility() {
        return null;
    }

    @Override
    public void registerAbilities(List<IItemHandlerModifiable> list) {

    }
}
