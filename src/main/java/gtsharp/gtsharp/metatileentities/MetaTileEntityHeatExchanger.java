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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import java.util.List;

//TODO - create hability for fluid1 and fluid2

public class MetaTileEntityHeatExchanger extends MultiblockWithAbilities {

    int mbt = 0;

    public MetaTileEntityHeatExchanger(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Override
    protected void updateFormedValid() {
        if (!getWorld().isRemote) {
            mbt = 0;
            FluidStack fs1 = inputFluidInventory.drain(GTSharpMaterials.highPressureBoilingWater.getFluid(2048), false);
            if (fs1 != null) {
                int necessaryDw = Math.max((int) Math.floor(fs1.amount/100), 1);
                FluidStack fs2 = inputFluidInventory.drain(Materials.DistilledWater.getFluid(necessaryDw), false);
                if (fs2 != null && fs2.amount == necessaryDw) {
                    int utilizedHotWater = 0;
                    int generatedSteam = 0;

                    List<IFluidTank> fluidTanks = outputFluidInventory.getFluidTanks();

                    if (fluidTanks.size() > 0) {
                        utilizedHotWater = fluidTanks.get(0).fill(GTSharpMaterials.highPressureWater.getFluid(fs1.amount), false);
                    }
                    if (fluidTanks.size() > 1) {
                        generatedSteam = fluidTanks.get(1).fill(Materials.Steam.getFluid(fs1.amount * 2), false);
                    }

                    if (utilizedHotWater > 0 && generatedSteam > 0){

                        mbt = generatedSteam;
                        fluidTanks.get(0).fill(GTSharpMaterials.highPressureWater.getFluid(utilizedHotWater), true);
                        fluidTanks.get(1).fill(Materials.Steam.getFluid(generatedSteam), true);

                        inputFluidInventory.drain(GTSharpMaterials.highPressureBoilingWater.getFluid(utilizedHotWater), true);
                        inputFluidInventory.drain(Materials.DistilledWater.getFluid(necessaryDw), true);

                        super.successProcess();
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

    public IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentString("Generating " + mbt + "mb/t of steam"));
    }
}
