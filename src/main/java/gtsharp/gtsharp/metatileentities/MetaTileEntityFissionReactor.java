package gtsharp.gtsharp.metatileentities;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.multiblock.BlockPattern;
import gregtech.api.multiblock.FactoryBlockPattern;
import gregtech.api.render.ICubeRenderer;
import gtsharp.gtsharp.GTSharpMaterials;
import gtsharp.gtsharp.GTSharpTextures;
import gtsharp.gtsharp.api.items.metaitem.FuelRodBehavior;
import gtsharp.gtsharp.api.metatileentity.MultiblockWithAbilities;
import gtsharp.gtsharp.block.GTSharpBlockMultiblockCasing;
import gtsharp.gtsharp.block.GTSharpMetaBlocks;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class MetaTileEntityFissionReactor extends MultiblockWithAbilities {

    private int mbt = 0;
    private float cooling = 0;
    private float coreTemperature = 28.0f;

    public MetaTileEntityFissionReactor(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Override
    protected void updateFormedValid() {
        if (!getWorld().isRemote) {
            mbt = 0;
            for (int slot = 0; slot < inputInventory.getSlots(); slot++) {
                ItemStack stack = inputInventory.getStackInSlot(slot);
                FuelRodBehavior fuelRodBehavior = FuelRodBehavior.getInstanceFor(stack);
                if (fuelRodBehavior != null) {
                    if (fuelRodBehavior.getPartDamage(stack) < fuelRodBehavior.getPartMaxDurability(stack)) {
                        int fuelEfficiency = fuelRodBehavior.getFuelEfficiency(stack);
                        FluidStack drainedWater = inputFluidInventory.drain(GTSharpMaterials.highPressureWater.getFluid(fuelEfficiency), true);
                        if (drainedWater != null && drainedWater.amount > 0) {
                            FluidStack steamStack = GTSharpMaterials.highPressureBoilingWater.getFluid(fuelEfficiency);
                            mbt += fuelEfficiency;
                            coreTemperature += fuelRodBehavior.getHeatPerTick(stack);

                            if (outputFluidInventory.fill(steamStack, true) <= 0) {
                                //getWorld().createExplosion(null, getPos().getX() + 0.5, getPos().getY() + 0.5, getPos().getZ() + 0.5,100,true);
                            }
                            super.successProcess();
                        } else {
                            // getWorld().createExplosion(null, getPos().getX() + 0.5, getPos().getY() + 0.5, getPos().getZ() + 0.5,100,true);
                        }
                        if (getTimer() % 20 == 0) {
                            fuelRodBehavior.setPartDamage(stack, fuelRodBehavior.getPartDamage(stack) + 1);
                        }
                    }
                }
            }
            coreTemperature = Math.max(coreTemperature - cooling,30);
            if (getTimer() % 20 == 0) {
                cooling = 3.3f;
                for(int x = getPos().getX() - 5; x < getPos().getX() + 5; x++) {
                    for(int z = getPos().getZ() - 5; z < getPos().getZ() + 5; z++) {
                        for(int y = getPos().getY() - 5; y < getPos().getY() + 5; y++) {
                            IBlockState blockState = getWorld().getBlockState(new BlockPos(x, y, z));
                            if (blockState.getBlock() == Blocks.WATER) {
                                int level = blockState.getValue(BlockLiquid.LEVEL).intValue();
                                level++;
                                if (level > 0)
                                    cooling += 0.1/(float)level;
                            }

                        }
                    }
                }
            }


        }
    }


    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXXXX", "XXRXX", "XXXXX", "XXXXX", "XXXXX")
                .aisle("XXXXX", "X###X", "X###X", "X###X", "XOOOX")
                .aisle("XXXXX", "I###L", "X###X", "X###X", "XOOOX")
                .aisle("XXXXX", "X###X", "X###X", "X###X", "XOOOX")
                .aisle("XXXXX", "XXSXX", "XXXXX", "XXXXX", "XXXXX")
                .where('S', selfPredicate())
                .where('O', statePredicate(getCasingState()).or(abilityPartPredicate(MultiblockAbility.IMPORT_ITEMS)))
                .where('I', statePredicate(getCasingState()).or(abilityPartPredicate(MultiblockAbility.IMPORT_FLUIDS)))
                .where('L', statePredicate(getCasingState()).or(abilityPartPredicate(MultiblockAbility.EXPORT_FLUIDS)))
                .where('R', statePredicate(getCasingState()).or(abilityPartPredicate(MultiblockAbility.EXPORT_ITEMS)))
                .where('X', statePredicate(getCasingState()))
                .where('#', (tile) -> true)
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

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentString("Generating: " + mbt + "mb/t"));
        textList.add(new TextComponentString("Cooling level: " + cooling));
        textList.add(new TextComponentString("Core Temperature: " + coreTemperature));
    }
}
