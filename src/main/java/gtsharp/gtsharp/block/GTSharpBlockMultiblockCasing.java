package gtsharp.gtsharp.block;

import gregtech.common.blocks.VariantBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.IStringSerializable;

public class GTSharpBlockMultiblockCasing extends VariantBlock<GTSharpBlockMultiblockCasing.MultiblockCasingType> {


    public GTSharpBlockMultiblockCasing() {
        super(Material.IRON);
        setUnlocalizedName("gtsharp_multiblock_casing");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("wrench", 2);
        setDefaultState(getState(GTSharpBlockMultiblockCasing.MultiblockCasingType.REACTOR_PRESSURE_VESSEL));
    }

    public enum MultiblockCasingType implements IStringSerializable {

        REACTOR_PRESSURE_VESSEL("reactor_pressure_vessel"),
        HV_COMPUTER_CASING("hv_computer_casing"),
        HV_CONNECTOR_CASING("hv_connector_casing");

        private String name;

        MultiblockCasingType(String name) {
            this.name = name;
        };

        @Override
        public String getName() {
            return this.name;
        }
    }
}
