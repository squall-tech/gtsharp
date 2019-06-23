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
import gtsharp.gtsharp.config.GTSharpConfig;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.List;

/*
    TODO LIST - depreted rod
                centralize colling
                graphite center blocks
 */

public class MetaTileEntityFissionReactor extends MultiblockWithAbilities {

    private int efficiency = 0;
    private float cooling = 0;
    private float coreTemperature = 28.0f;
    private int consumableTime = 20;

    public MetaTileEntityFissionReactor(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Override
    protected void updateFormedValid() {
        if (!getWorld().isRemote) {
            efficiency = 0;
            int heat = 0;
            int cold = 0;
            for (IItemHandlerModifiable slot: getAbilities(MultiblockAbility.IMPORT_ITEMS)) {
                boolean found = false;
                for (int x = 0; x < slot.getSlots(); x++) {
                    ItemStack stack = slot.getStackInSlot(x);
                    FuelRodBehavior fuelRodBehavior = FuelRodBehavior.getInstanceFor(stack);
                    if (fuelRodBehavior != null) {
                        if (fuelRodBehavior.getPartDamage(stack) < fuelRodBehavior.getPartMaxDurability(stack)) {
                            if (fuelRodBehavior.getFuelEfficiency(stack) > 0) {
                                if (!found) {
                                    heat += fuelRodBehavior.getFuelEfficiency(stack);
                                    found = true;
                                }
                            } else {
                                cold += fuelRodBehavior.getFuelEfficiency(stack);
                            }
                            coreTemperature += (fuelRodBehavior.getHeatPerTick(stack) + (fuelRodBehavior.getFuelEfficiency(stack)/1000));
                            if (getTimer() % consumableTime == 0 && fuelRodBehavior.isConsumable(stack)) {
                                fuelRodBehavior.setPartDamage(stack, fuelRodBehavior.getPartDamage(stack) + 1);
                            }
                        } else {
                            // TODO create depleated rod
                        }
                    }
                }
            }
            cold = cold * -1;
            if (getTimer() % 20 == 0 && cold > 0 && heat > 0) {
                consumableTime = Math.max(20, Math.min((cold*100)/heat,100) * 2);
            }

            efficiency =  Math.max(heat - cold,0);
            if (efficiency > 0) {
                FluidStack drainedWater = inputFluidInventory.drain(GTSharpMaterials.highPressureWater.getFluid(efficiency), true);
                if (drainedWater != null && drainedWater.amount > 0) {
                    FluidStack steamStack = GTSharpMaterials.highPressureBoilingWater.getFluid(efficiency);
                    if (outputFluidInventory.fill(steamStack, true) <= 0) {
                        getWorld().createExplosion(null, getPos().getX() + 0.5, getPos().getY() + 0.5, getPos().getZ() + 0.5,100,true);
                    }
                    super.successProcess();
                } else if (GTSharpConfig.doExplosions) {
                    getWorld().createExplosion(null, getPos().getX() + 0.5, getPos().getY() + 0.5, getPos().getZ() + 0.5,100,true);
                }
            }

            coreTemperature = Math.max(coreTemperature - cooling, 30);
            if (getTimer() % 20 == 0) {
                cooling = 3.3f;

                IBlockState state = getWorld().getBlockState(getPos().add(0, 0, 0));
                state.getBlock();

                //needed to improve to get only connected wather.
                for (int x = getPos().getX() - 5; x < getPos().getX() + 5; x++) {
                    for (int z = getPos().getZ() - 5; z < getPos().getZ() + 5; z++) {
                        for (int y = getPos().getY() - 5; y < getPos().getY() + 5; y++) {
                            IBlockState blockState = getWorld().getBlockState(new BlockPos(x, y, z));
                            //instance of BlockLiquid
                            if (blockState.getBlock() == Blocks.WATER) {
                                int level = blockState.getValue(BlockLiquid.LEVEL).intValue();
                                level++;
                                if (level > 0)
                                    cooling += 0.1 / (float) level;
                            }

                        }
                    }
                }
            }
            if (coreTemperature > 10000 && GTSharpConfig.doExplosions) {
                getWorld().createExplosion(null, getPos().getX() + 0.5, getPos().getY() + 0.5, getPos().getZ() + 0.5,200,true);
            }
        }
    }


    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXXXX", "XXRXX", "XXXXX", "XXXXX", "XXXXX",  "XXXXX",  "XXXXX")
                .aisle("XXXXX", "XMMMX", "XMMMX", "XMMMX", "XMMMX", "XMMMX",  "XOOOX")
                .aisle("XXXXX", "XMMMX", "XMMMX", "XMMMX", "XMMMX", "IMMML",  "XOOOX")
                .aisle("XXXXX", "XMMMX", "XMMMX", "XMMMX", "XMMMX",  "XMMMX",  "XOOOX")
                .aisle("XXXXX", "XXSXX", "XXXXX", "XXXXX", "XXXXX", "XXXXX", "XXXXX")
                .where('S', selfPredicate())
                .where('O', statePredicate(getCasingState()).or(abilityPartPredicate(MultiblockAbility.IMPORT_ITEMS)))
                .where('I', statePredicate(getCasingState()).or(abilityPartPredicate(MultiblockAbility.IMPORT_FLUIDS)))
                .where('L', statePredicate(getCasingState()).or(abilityPartPredicate(MultiblockAbility.EXPORT_FLUIDS)))
                .where('R', statePredicate(getCasingState()).or(abilityPartPredicate(MultiblockAbility.EXPORT_ITEMS)))
                .where('X', statePredicate(getCasingState()))
                .where('M', statePredicate(getModerator()))
                .build();
    }

    private IBlockState getCasingState() {
        return GTSharpMetaBlocks.GT_SHARP_BLOCK_MULTIBLOCK_CASING.getState(GTSharpBlockMultiblockCasing.MultiblockCasingType.REACTOR_PRESSURE_VESSEL);
    }

    private IBlockState getModerator() {
        return GTSharpMetaBlocks.GT_SHARP_BLOCK_MULTIBLOCK_CASING.getState(GTSharpBlockMultiblockCasing.MultiblockCasingType.GRAPHITE_MODERATOR);
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
        if (isStructureFormed()) {
            textList.add(new TextComponentString("Efficiency: " + efficiency + "mb/t"));
            textList.add(new TextComponentString("Cooling level: " + cooling));
            textList.add(new TextComponentString("Core Temperature: " + coreTemperature));
        }
    }
}
