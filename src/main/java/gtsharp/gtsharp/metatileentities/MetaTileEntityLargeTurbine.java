package gtsharp.gtsharp.metatileentities;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.multiblock.BlockPattern;
import gregtech.api.multiblock.FactoryBlockPattern;
import gregtech.api.render.ICubeRenderer;
import gregtech.api.render.Textures;
import gregtech.common.blocks.BlockTurbineCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.electric.multiblockpart.MetaTileEntityRotorHolder;
import gtsharp.gtsharp.api.metatileentity.MultiblockWithAbilities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

public class MetaTileEntityLargeTurbine extends MultiblockWithAbilities {

    public static final MultiblockAbility<MetaTileEntityRotorHolder> ABILITY_ROTOR_HOLDER = new MultiblockAbility<>();

    public MetaTileEntityLargeTurbine(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Override
    protected void updateFormedValid() {

    }

    @Override
    protected BlockPattern createStructurePattern() {
        return  FactoryBlockPattern.start()
                .aisle("CCCC", "CCIC", "CCCC")
                .aisle("COCC", "R##D", "CCCC")
                .aisle("CCCC", "CSCC", "CCCC")
                .where('S', selfPredicate())
                .where('#', isAirPredicate())
                .where('C', statePredicate(getCasingState()))
                .where('I', statePredicate(getCasingState()).or(abilityPartPredicate(MultiblockAbility.IMPORT_FLUIDS)))
                .where('O', statePredicate(getCasingState()).or(abilityPartPredicate(MultiblockAbility.EXPORT_FLUIDS)))
                .where('R', abilityPartPredicate(ABILITY_ROTOR_HOLDER))
                .where('D', abilityPartPredicate(MultiblockAbility.OUTPUT_ENERGY))
                .build();
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.SOLID_STEEL_CASING;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder metaTileEntityHolder) {
        return new MetaTileEntityLargeTurbine(metaTileEntityId);
    }

    public IBlockState getCasingState() {
        return MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.STEEL_TURBINE_CASING);
    }

    public MultiblockAbility[] getAllowedAbilities() {
        return new MultiblockAbility[] {MultiblockAbility.IMPORT_FLUIDS, MultiblockAbility.EXPORT_FLUIDS};
    }
}
