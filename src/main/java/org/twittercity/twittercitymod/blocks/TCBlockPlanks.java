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

public class TCBlockPlanks extends TCBlock {

	public static final PropertyEnum<TCBlockPlanks.EnumType> VARIANT = PropertyEnum.<TCBlockPlanks.EnumType>create(
			"variant", TCBlockPlanks.EnumType.class);

	public TCBlockPlanks(String name) {
		super(Material.WOOD);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, TCBlockPlanks.EnumType.OAK));
		this.setRegistryName(name);	
		this.setSoundType(SoundType.WOOD);
	}

	/**
	 * Gets the metadata of the item this Block can drop. This method is called when
	 * the block gets destroyed. It returns the metadata of the dropped item based
	 * on the old metadata of the block.
	 */
	@Override
	public int damageDropped(IBlockState state) {
		return ((TCBlockPlanks.EnumType) state.getValue(VARIANT)).getMetadata();
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood
	 * returns 4 blocks)
	 */
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (TCBlockPlanks.EnumType blockplanks$enumtype : TCBlockPlanks.EnumType.values()) {
			items.add(new ItemStack(this, 1, blockplanks$enumtype.getMetadata()));
		}
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	@Deprecated
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANT, TCBlockPlanks.EnumType.byMetadata(meta));
	}

	/**
	 * Get the MapColor for this Block and the given BlockState
	 */
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return ((TCBlockPlanks.EnumType) state.getValue(VARIANT)).getMapColor();
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((TCBlockPlanks.EnumType) state.getValue(VARIANT)).getMetadata();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { VARIANT });
	}

	public enum EnumType implements IStringSerializable {
		OAK(0, "oak", MapColor.WOOD), SPRUCE(1, "spruce", MapColor.OBSIDIAN), BIRCH(2, "birch", MapColor.SAND),
		JUNGLE(3, "jungle", MapColor.DIRT), ACACIA(4, "acacia", MapColor.ADOBE),
		DARK_OAK(5, "dark_oak", "big_oak", MapColor.BROWN);

		private static final TCBlockPlanks.EnumType[] META_LOOKUP = new TCBlockPlanks.EnumType[values().length];
		private final int meta;
		private final String name;
		private final String unlocalizedName;
		/** The color that represents this entry on a map. */
		private final MapColor mapColor;

		private EnumType(int metaIn, String nameIn, MapColor mapColorIn) {
			this(metaIn, nameIn, nameIn, mapColorIn);
		}

		private EnumType(int metaIn, String nameIn, String unlocalizedNameIn, MapColor mapColorIn) {
			this.meta = metaIn;
			this.name = nameIn;
			this.unlocalizedName = unlocalizedNameIn;
			this.mapColor = mapColorIn;
		}

		public int getMetadata() {
			return this.meta;
		}

		/**
		 * The color which represents this entry on a map.
		 */
		public MapColor getMapColor() {
			return this.mapColor;
		}

		public String toString() {
			return this.name;
		}

		public static TCBlockPlanks.EnumType byMetadata(int meta) {
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
			for (TCBlockPlanks.EnumType tcblockplanks$enumtype : values()) {
				META_LOOKUP[tcblockplanks$enumtype.getMetadata()] = tcblockplanks$enumtype;
			}
		}
	}

}
