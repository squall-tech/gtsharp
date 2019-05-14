package gtsharp.gtsharp.jei;

import gregicadditions.jei.GAMultiblockShapeInfo;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.integration.jei.multiblock.MultiblockInfoPage;
import gregtech.integration.jei.multiblock.MultiblockShapeInfo;
import gtsharp.gtsharp.GTSharpMetaTileEntities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.List;

public class HeatExchangerInfo extends MultiblockInfoPage {
    
    @Override
    public MultiblockControllerBase getController() {
        return GTSharpMetaTileEntities.META_TILE_ENTITY_HEAT_EXCHANGER;
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        List<MultiblockShapeInfo> shapes = new ArrayList<>();
        GAMultiblockShapeInfo.Builder builder = GAMultiblockShapeInfo.builder();
        builder.aisle("XXX","XZX","XXX","XAX","XXX")
                .aisle("XIX","XPX","XPX","XPX","XOX")
                .aisle("XXX","XSX","XXX","XXX","XXX")
                .where('S', getController(), EnumFacing.SOUTH)
                .where('P', MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE))
                .where('I',  MetaTileEntities.FLUID_IMPORT_HATCH[1], EnumFacing.DOWN)
                .where('Z',  MetaTileEntities.FLUID_IMPORT_HATCH[1], EnumFacing.NORTH)
                .where('A',  MetaTileEntities.FLUID_EXPORT_HATCH[1], EnumFacing.NORTH)
                .where('O',  MetaTileEntities.FLUID_EXPORT_HATCH[1], EnumFacing.UP)
                .where('X', getCasingState());

        shapes.add(builder.build());
        return shapes;
    }

    private IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID);
    }

    @Override
    public String[] getDescription() {
        return new String[0];
    }
}
