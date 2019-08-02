package org.twittercity.twittercitymod.blocks;

import java.util.Random;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class TCBlockStone extends TCBlock {

	public static final PropertyEnum<TCBlockStone.EnumType> VARIANT = PropertyEnum.<TCBlockStone.EnumType>create(
			"variant", TCBlockStone.EnumType.class);

	public TCBlockStone(String name) {
		super(Material.ROCK);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, TCBlockStone.EnumType.STONE));
		this.setSoundType(SoundType.STONE);
		this.setRegistryName(name);
	};

	/**
	 * Get the MapColor for this Block and the given BlockState
	 */
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return ((TCBlockStone.EnumType) state.getValue(VARIANT)).getMapColor();
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return state.getValue(VARIANT) == TCBlockStone.EnumType.STONE ? Item.getItemFromBlock(Blocks.COBBLESTONE)
				: Item.getItemFromBlock(Blocks.STONE);
	}

	/**
	 * Gets the metadata of the item this Block can drop. This method is called when
	 * the block gets destroyed. It returns the metadata of the dropped item based
	 * on the old metadata of the block.
	 */
	public int damageDropped(IBlockState state) {
		return ((TCBlockStone.EnumType) state.getValue(VARIANT)).getMetadata();
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood
	 * returns 4 blocks)
	 */
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (TCBlockStone.EnumType tcblockstone$enumtype : TCBlockStone.EnumType.values()) {
			items.add(new ItemStack(this, 1, tcblockstone$enumtype.getMetadata()));
		}
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANT, TCBlockStone.EnumType.byMetadata(meta));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState state) {
		return ((TCBlockStone.EnumType) state.getValue(VARIANT)).getMetadata();
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { VARIANT });
	}

	public static enum EnumType implements IStringSerializable {
		STONE(0, MapColor.STONE, "tc_stone", "stone", true), GRANITE(1, MapColor.DIRT, "tc_granite", "granite", true),
		GRANITE_SMOOTH(2, MapColor.DIRT, "tc_granite_smooth", "graniteSmooth", false),
		DIORITE(3, MapColor.QUARTZ, "tc_diorite", "diorite", true),
		DIORITE_SMOOTH(4, MapColor.QUARTZ, "tc_diorite_smooth", "dioriteSmooth", false),
		ANDESITE(5, MapColor.STONE, "tc_andesite", "andesite", true),
		ANDESITE_SMOOTH(6, MapColor.STONE, "tc_andesite_smooth", "andesiteSmooth", false);

		/** Array of the Block's BlockStates */
		private static final TCBlockStone.EnumType[] META_LOOKUP = new TCBlockStone.EnumType[values().length];
		/** The BlockState's metadata. */
		private final int meta;
		/** The EnumType's name. */
		private final String name;
		private final String unlocalizedName;
		private final MapColor mapColor;
		private final boolean isNatural;

		private EnumType(int meta, MapColor mapColor, String name, boolean isNatural) {
			this(meta, mapColor, name, name, isNatural);
		}

		private EnumType(int meta, MapColor mapColor, String name, String unlocalizedName, boolean isNatural) {
			this.meta = meta;
			this.name = name;
			this.unlocalizedName = unlocalizedName;
			this.mapColor = mapColor;
			this.isNatural = isNatural;
		}

		/**
		 * Returns the EnumType's metadata value.
		 */
		public int getMetadata() {
			return this.meta;
		}

		public MapColor getMapColor() {
			return this.mapColor;
		}

		public String toString() {
			return this.name;
		}

		/**
		 * Returns an EnumType for the BlockState from a metadata value.
		 */
		public static TCBlockStone.EnumType byMetadata(int meta) {
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

		public boolean isNatural() {
			return this.isNatural;
		}

		static {
			for (TCBlockStone.EnumType tcblockstone$enumtype : values()) {
				META_LOOKUP[tcblockstone$enumtype.getMetadata()] = tcblockstone$enumtype;
			}
		}
	}
}
