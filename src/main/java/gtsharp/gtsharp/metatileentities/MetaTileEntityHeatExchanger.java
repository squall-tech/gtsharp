package gtsharp.gtsharp.metatileentities;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.multiblock.BlockPattern;
import gregtech.api.multiblock.FactoryBlockPattern;
import gregtech.api.render.ICubeRenderer;
import gregtech.api.render.Textures;
import gregtech.api.unification.material.Materials;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import gtsharp.gtsharp.GTSharpMaterials;
import gtsharp.gtsharp.api.metatileentity.MultiblockWithAbilities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import java.util.List;

public class MetaTileEntityHeatExchanger extends MultiblockWithAbilities {

    public MetaTileEntityHeatExchanger(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Override
    protected void updateFormedValid() {
        if (!getWorld().isRemote) {
            FluidStack fs1 = inputFluidInventory.drain(GTSharpMaterials.highPressureBoilingWater.getFluid(150), false);
            if (fs1 != null) {
                FluidStack fs2 = inputFluidInventory.drain(Materials.DistilledWater.getFluid(fs1.amount * 10), false);
                if (fs2 != null) {
                    int utilizedHotWater = 0;
                    int generatedSteam = 0;

                    List<IFluidTank> fluidTanks = outputFluidInventory.getFluidTanks();

                    if (fluidTanks.size() > 0) {
                        utilizedHotWater = fluidTanks.get(0).fill(GTSharpMaterials.highPressureWater.getFluid(fs2.amount / 10), false);
                    }
                    if (fluidTanks.size() > 1) {
                        generatedSteam = fluidTanks.get(1).fill(Materials.Steam.getFluid(fs2.amount * 10), false);
                    }

                    if (utilizedHotWater > 0 && generatedSteam > 0){
                        fluidTanks.get(0).fill(GTSharpMaterials.highPressureWater.getFluid(utilizedHotWater -1), true);
                        fluidTanks.get(1).fill(Materials.Steam.getFluid(fs2.amount * 10), true);
                        inputFluidInventory.drain(GTSharpMaterials.highPressureBoilingWater.getFluid(utilizedHotWater), true);
                        inputFluidInventory.drain(Materials.DistilledWater.getFluid(generatedSteam /10), true);
                    }
                }
            }
        }
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXX", "XZX", "XXX", "XAX", "XXX")
                .aisle("XIX", "XPX", "XPX", "XPX", "XOX")
                .aisle("XXX", "XSX", "XXX", "XXX", "XXX")
                .where('S', selfPredicate())
                .where('P', statePredicate(MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE)))
                .where('I', statePredicate(getCasingState()).or(abilityPartPredicate(MultiblockAbility.IMPORT_FLUIDS)))
                .where('Z', statePredicate(getCasingState()).or(abilityPartPredicate(MultiblockAbility.IMPORT_FLUIDS)))
                .where('A', statePredicate(getCasingState()).or(abilityPartPredicate(MultiblockAbility.EXPORT_FLUIDS)))
                .where('O', statePredicate(getCasingState()).or(abilityPartPredicate(MultiblockAbility.EXPORT_FLUIDS)))
                .where('X', statePredicate(getCasingState()))
                .build();
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.SOLID_STEEL_CASING;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder metaTileEntityHolder) {
        return new MetaTileEntityHeatExchanger(metaTileEntityId);
    }

    private IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID);
    }
}
