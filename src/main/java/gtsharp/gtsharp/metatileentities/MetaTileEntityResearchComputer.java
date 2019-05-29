package gtsharp.gtsharp.metatileentities;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.multiblock.BlockPattern;
import gregtech.api.multiblock.FactoryBlockPattern;
import gregtech.api.render.ICubeRenderer;
import gtsharp.gtsharp.GTSharpTextures;
import gtsharp.gtsharp.block.GTSharpBlockMultiblockCasing;
import gtsharp.gtsharp.block.GTSharpMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

public class MetaTileEntityResearchComputer extends MultiblockWithDisplayBase {

    public MetaTileEntityResearchComputer(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Override
    protected void updateFormedValid() {

    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXX","XCC")
                .aisle("XXX","SCC")
                .where('S', selfPredicate())
                .where('X', statePredicate(getCasingState()))
                .where('C', statePredicate(getConnectorCasingState()))
                .build();
    }

    private IBlockState getCasingState() {
        return GTSharpMetaBlocks.GT_SHARP_BLOCK_MULTIBLOCK_CASING.getState(GTSharpBlockMultiblockCasing.MultiblockCasingType.HV_COMPUTER_CASING);
    }

    private IBlockState getConnectorCasingState() {
        return GTSharpMetaBlocks.GT_SHARP_BLOCK_MULTIBLOCK_CASING.getState(GTSharpBlockMultiblockCasing.MultiblockCasingType.HV_CONNECTOR_CASING);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTSharpTextures.HV_COMPUTER_CASING;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder metaTileEntityHolder) {
        return new MetaTileEntityResearchComputer(metaTileEntityId);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        GTSharpTextures.RESEARCH_COMPUTER.render(renderState, translation, pipeline, this.getFrontFacing(), false);
    }
}
