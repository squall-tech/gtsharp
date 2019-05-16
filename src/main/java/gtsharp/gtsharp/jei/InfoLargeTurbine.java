package gtsharp.gtsharp.jei;

import com.google.common.collect.Lists;
import gregtech.api.GTValues;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.BlockInfo;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import gregtech.common.items.behaviors.TurbineRotorBehavior;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.common.metatileentities.electric.multiblockpart.MetaTileEntityRotorHolder;
import gregtech.integration.jei.multiblock.MultiblockInfoPage;
import gregtech.integration.jei.multiblock.MultiblockShapeInfo;
import gtsharp.gtsharp.metatileentities.MetaTileEntityLargeTurbine;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import java.util.List;

public class InfoLargeTurbine extends MultiblockInfoPage {

    public final MetaTileEntityLargeTurbine turbine;

    public InfoLargeTurbine(MetaTileEntityLargeTurbine turbine) {
        this.turbine = turbine;
    }

    @Override
    public MultiblockControllerBase getController() {
        return turbine;
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {

        MetaTileEntityHolder holder = new MetaTileEntityHolder();
        holder.setMetaTileEntity(MetaTileEntities.ROTOR_HOLDER[2]);
        holder.getMetaTileEntity().setFrontFacing(EnumFacing.WEST);
        ItemStack rotorStack = MetaItems.TURBINE_ROTOR.getStackForm();
        //noinspection ConstantConditions
        TurbineRotorBehavior.getInstanceFor(rotorStack).setPartMaterial(rotorStack, Materials.Darmstadtium);
        ((MetaTileEntityRotorHolder) holder.getMetaTileEntity()).getRotorInventory().setStackInSlot(0, rotorStack);

        MultiblockShapeInfo.Builder shapeInfo = MultiblockShapeInfo.builder()
                .aisle("CCCC", "CCIC", "CCCC")
                .aisle("COCC", "R##D", "CCCC")
                .aisle("CCCC", "CSCC", "CCCC")
                .where('S', turbine, EnumFacing.SOUTH)
                .where('#', Blocks.AIR.getDefaultState())
                .where('C', turbine.getCasingState())
                .where('I', MetaTileEntities.FLUID_IMPORT_HATCH[GTValues.LV],EnumFacing.NORTH)
                .where('O', MetaTileEntities.FLUID_IMPORT_HATCH[GTValues.LV],EnumFacing.DOWN)
                .where('R',  new BlockInfo(MetaBlocks.MACHINE.getDefaultState(), holder))
                .where('D', MetaTileEntities.ENERGY_OUTPUT_HATCH[GTValues.LV], EnumFacing.EAST);

        return Lists.newArrayList(shapeInfo.build());
    }

    @Override
    public String[] getDescription() {
        return new String[] {I18n.format("gregtech.multiblock.large_turbine.description")};
    }
}
