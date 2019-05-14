package gtsharp.gtsharp.api.metatileentity;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.multiblock.PatternMatchContext;
import gregtech.api.render.Textures;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public abstract class MultiblockWithAbilities extends MultiblockWithDisplayBase {

    protected IItemHandlerModifiable inputInventory;
    protected IItemHandlerModifiable outputInventory;
    protected IMultipleTankHandler inputFluidInventory;
    protected IMultipleTankHandler outputFluidInventory;


    public MultiblockWithAbilities(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    private void initializeAbilities() {
        this.inputInventory = new ItemHandlerList(getAbilities(MultiblockAbility.IMPORT_ITEMS));
        this.inputFluidInventory = new FluidTankList(allowSameFluidFillForOutputs(), getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        this.outputInventory = new ItemHandlerList(getAbilities(MultiblockAbility.EXPORT_ITEMS));
        this.outputFluidInventory = new FluidTankList(allowSameFluidFillForOutputs(), getAbilities(MultiblockAbility.EXPORT_FLUIDS));
    }

    private void resetTileAbilities() {
        this.inputInventory = new ItemStackHandler(0);
        this.inputFluidInventory = new FluidTankList(true);
        this.outputInventory = new ItemStackHandler(0);
        this.outputFluidInventory = new FluidTankList(true);
    }

    public boolean allowSameFluidFillForOutputs() {
        return true;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        this.initializeAbilities();
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        resetTileAbilities();
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        Textures.MULTIBLOCK_WORKABLE_OVERLAY.render(renderState, translation, pipeline, this.getFrontFacing(), false);
    }

}
