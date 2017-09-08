package tehnut.xtones.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tehnut.xtones.Xtones;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;

public class BlockXtone extends BlockEnum<BlockXtone.XtoneType> {

    private final String name;

    private boolean seeThrough;

    public BlockXtone(String name) {
        super(Material.ROCK, XtoneType.class, "variant");

        this.name = name;

        setRegistryName(name);
        setUnlocalizedName(Xtones.ID + "." + name);
        setCreativeTab(Xtones.TAB);
        setResistance(3.0F);
        setHardness(3.0F);
    }

    @Override
    @Nonnull
    public BlockRenderLayer getBlockLayer() {
        return seeThrough ? BlockRenderLayer.TRANSLUCENT : BlockRenderLayer.SOLID;
    }

    @Override
    public boolean isNormalCube(IBlockState state) {
        return !seeThrough;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return !seeThrough;
    }

    @Override
    public boolean causesSuffocation(IBlockState state) {
        return !seeThrough;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return !seeThrough;
    }

    @Override
    public int getLightOpacity(IBlockState state) {
        return seeThrough ? 0 : super.getLightOpacity(state);
    }

    @Override
    @Nonnull
    public Material getMaterial(IBlockState state) {
        return seeThrough ? Material.GLASS : super.getMaterial(state);
    }

    @Override
    @Nonnull
    public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
        return seeThrough ? SoundType.GLASS : super.getSoundType(state, world, pos, entity);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        if (!seeThrough)
            return super.shouldSideBeRendered(state, world, pos, side);

        IBlockState neighbor = world.getBlockState(pos.offset(side));
        if (neighbor.getBlock() != this)
            return true;

        if (neighbor.getValue(getProperty()) != state.getValue(getProperty()))
            return true;

        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return !seeThrough;
    }

    public BlockXtone setSeeThrough() {
        this.seeThrough = true;
        return this;
    }

    public String getName() {
        return name;
    }

    public enum XtoneType implements IStringSerializable {
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN,
        ELEVEN,
        TWELVE,
        THIRTEEN,
        FOURTEEN,
        FIFTEEN,
        SIXTEEN;

        private final int displayIndex;

        XtoneType() {
            this.displayIndex = ordinal() + 1;
        }

        public int getDisplayIndex() {
            return displayIndex;
        }

        @Override
        public String getName() {
            return toString().toLowerCase(Locale.ROOT);
        }
    }
}
