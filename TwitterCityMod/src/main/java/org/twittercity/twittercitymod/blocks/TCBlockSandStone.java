package org.twittercity.twittercitymod.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class TCBlockSandStone extends TCBlock {
	public static final PropertyEnum<TCBlockSandStone.EnumType> TYPE = PropertyEnum.<TCBlockSandStone.EnumType>create(
			"type", TCBlockSandStone.EnumType.class);

	public TCBlockSandStone(String name) {
		super(Material.ROCK);
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, TCBlockSandStone.EnumType.DEFAULT));
		this.setRegistryName(name);
		this.setSoundType(SoundType.STONE);
	}

	/**
	 * Gets the metadata of the item this Block can drop. This method is called when
	 * the block gets destroyed. It returns the metadata of the dropped item based
	 * on the old metadata of the block.
	 */
	public int damageDropped(IBlockState state) {
		return ((TCBlockSandStone.EnumType) state.getValue(TYPE)).getMetadata();
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood
	 * returns 4 blocks)
	 */
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (TCBlockSandStone.EnumType blocksandstone$enumtype : TCBlockSandStone.EnumType.values()) {
			items.add(new ItemStack(this, 1, blocksandstone$enumtype.getMetadata()));
		}
	}

	/**
	 * Get the MapColor for this Block and the given BlockState
	 */
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return MapColor.SAND;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TYPE, TCBlockSandStone.EnumType.byMetadata(meta));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState state) {
		return ((TCBlockSandStone.EnumType) state.getValue(TYPE)).getMetadata();
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { TYPE });
	}

	public static enum EnumType implements IStringSerializable {
		DEFAULT(0, "sandstone", "default"), CHISELED(1, "chiseled_sandstone", "chiseled"),
		SMOOTH(2, "smooth_sandstone", "smooth");

		private static final TCBlockSandStone.EnumType[] META_LOOKUP = new TCBlockSandStone.EnumType[values().length];
		private final int metadata;
		private final String name;
		private final String unlocalizedName;

		private EnumType(int meta, String name, String unlocalizedName) {
			this.metadata = meta;
			this.name = name;
			this.unlocalizedName = unlocalizedName;
		}

		public int getMetadata() {
			return this.metadata;
		}

		public String toString() {
			return this.name;
		}

		/**
		 * Returns the matching EnumType for the given metadata.
		 */
		public static TCBlockSandStone.EnumType byMetadata(int meta) {
			if (meta < 0 || meta >= META_LOOKUP.length) {
				meta = 0;
			}

			return META_LOOKUP[meta];
		}

		public String getName() {
			return this.name;
		}

		public String getUnlocalizedName() {
			return this.unlocalizedName;
		}

		static {
			for (TCBlockSandStone.EnumType tcblocksandstone$enumtype : values()) {
				META_LOOKUP[tcblocksandstone$enumtype.getMetadata()] = tcblocksandstone$enumtype;
			}
		}
	}
}
