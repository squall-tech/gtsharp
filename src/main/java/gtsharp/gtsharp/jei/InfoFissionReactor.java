package gtsharp.gtsharp.jei;

import gregicadditions.jei.GAMultiblockShapeInfo;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.integration.jei.multiblock.MultiblockInfoPage;
import gregtech.integration.jei.multiblock.MultiblockShapeInfo;
import gtsharp.gtsharp.GTSharpMetaTileEntities;
import gtsharp.gtsharp.block.GTSharpBlockMultiblockCasing;
import gtsharp.gtsharp.block.GTSharpMetaBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.List;

public class InfoFissionReactor extends MultiblockInfoPage {

    @Override
    public MultiblockControllerBase getController() {
        return GTSharpMetaTileEntities.META_TILE_ENTITY_FISSION_REACTOR;
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        List<MultiblockShapeInfo> shapes = new ArrayList<>();
        GAMultiblockShapeInfo.Builder builder = GAMultiblockShapeInfo.builder();
        builder.aisle("XXXXX", "XXRXX", "XXXXX","XXXXX","XXXXX")
                .aisle("XXXXX", "X###X", "X###X","X###X","XOOOX")
                .aisle("XXXXX", "I###L", "X###X","X###X","XOOOX")
                .aisle("XXXXX", "X###X", "X###X","X###X","XOOOX")
                .aisle("XXXXX", "XXSXX", "XXXXX","XXXXX","XXXXX")
                .where('S', getController(), EnumFacing.SOUTH)
                .where('O', MetaTileEntities.ITEM_IMPORT_BUS[1], EnumFacing.UP)
                .where('I', MetaTileEntities.FLUID_IMPORT_HATCH[1], EnumFacing.WEST)
                .where('L', MetaTileEntities.FLUID_EXPORT_HATCH[1], EnumFacing.EAST)
                .where('R', MetaTileEntities.ITEM_EXPORT_BUS[1], EnumFacing.NORTH)
                .where('X', GTSharpMetaBlocks.GT_SHARP_BLOCK_MULTIBLOCK_CASING.getState(GTSharpBlockMultiblockCasing.MultiblockCasingType.REACTOR_PRESSURE_VESSEL))
                .where('#', Blocks.AIR.getDefaultState());

        shapes.add(builder.build());
        return shapes;
    }

    @Override
    public String[] getDescription() {
        return new String[0];
    }
}
