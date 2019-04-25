package gtsharp.gtsharp.metatileentities;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.multiblock.BlockPattern;
import gregtech.api.multiblock.FactoryBlockPattern;
import gregtech.api.render.ICubeRenderer;
import gregtech.api.render.Textures;
import gtsharp.gtsharp.GTSharpRecipeMap;
import gtsharp.gtsharp.GTSharpTextures;
import gtsharp.gtsharp.block.GTSharpBlockMultiblockCasing;
import gtsharp.gtsharp.block.GTSharpMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

public class MetaTileEntityFissionReactor extends MultiblockWithDisplayBase {




    public MetaTileEntityFissionReactor(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Override
    protected void updateFormedValid() {

    }


    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        Textures.MULTIBLOCK_WORKABLE_OVERLAY.render(renderState, translation, pipeline, this.getFrontFacing(), false);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXX", "XXX").setRepeatable(1,4)
                .aisle("XXX", "XSX")
                .where('S', selfPredicate())
                .where('X', statePredicate(getCasingState()))
                .build();
    }

    private IBlockState getCasingState() {
        return GTSharpMetaBlocks.GT_SHARP_BLOCK_MULTIBLOCK_CASING.getState(GTSharpBlockMultiblockCasing.MultiblockCasingType.REACTOR_PRESSURE_VESSEL);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTSharpTextures.REACTOR_PRESSURE_VESSEL;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder metaTileEntityHolder) {
        return new MetaTileEntityFissionReactor(metaTileEntityId);
    }
}
