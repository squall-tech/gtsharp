package gtsharp.gtsharp.block;

import gregtech.common.blocks.VariantBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;

import javax.annotation.Nullable;

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
        GRAPHITE_MODERATOR ("graphite_moderator");

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
