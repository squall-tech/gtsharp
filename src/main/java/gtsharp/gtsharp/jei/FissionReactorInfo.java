package gtsharp.gtsharp.jei;

import gregicadditions.jei.GAMultiblockShapeInfo;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.integration.jei.multiblock.MultiblockInfoPage;
import gregtech.integration.jei.multiblock.MultiblockShapeInfo;
import gtsharp.gtsharp.GTSharpMetaTileEntities;
import gtsharp.gtsharp.block.GTSharpBlockMultiblockCasing;
import gtsharp.gtsharp.block.GTSharpMetaBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.List;

public class FissionReactorInfo extends MultiblockInfoPage {

    @Override
    public MultiblockControllerBase getController() {
        return GTSharpMetaTileEntities.META_TILE_ENTITY_FISSION_REACTOR;
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        List<MultiblockShapeInfo> shapes = new ArrayList<>();
        GAMultiblockShapeInfo.Builder builder = GAMultiblockShapeInfo.builder();
        builder.aisle("XXXXXXX", "XXXXXXX", "XXXXXXX")
                .aisle("XXXXXXX", "X#####X", "XXXXXXX")
                .aisle("XXXXXXX", "X#####X", "XXXXXXX")
                .aisle("XXXXXXX", "X#####X", "XXXXXXX")
                .aisle("XXXXXXX", "X#####X", "XXXXXXX")
                .aisle("XXXXXXX", "XXXSXXX", "XXXXXXX")
                .where('S', getController(), EnumFacing.SOUTH)
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
