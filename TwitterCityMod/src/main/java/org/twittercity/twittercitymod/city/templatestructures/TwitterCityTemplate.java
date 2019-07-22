package org.twittercity.twittercitymod.city.templatestructures;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.Rotation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.IDataFixer;
import net.minecraft.util.datafix.IDataWalker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.PlacementSettings;

public class TwitterCityTemplate {
	/** blocks in the structure */
	private final List<TwitterCityTemplate.BlockInfo> blocks = Lists.<TwitterCityTemplate.BlockInfo>newArrayList();
	/** entities in the structure */
	private final List<TwitterCityTemplate.EntityInfo> entities = Lists.<TwitterCityTemplate.EntityInfo>newArrayList();
	/** size of the structure */
	private BlockPos size = BlockPos.ORIGIN;
	/** The author of this template. */
	private String author = "?";

	public BlockPos getSize() {
		return this.size;
	}

	public void setAuthor(String authorIn) {
		this.author = authorIn;
	}

	public String getAuthor() {
		return this.author;
	}

	public BlockPos calculateConnectedPos(PlacementSettings placementIn, BlockPos p_186262_2_,
			PlacementSettings p_186262_3_, BlockPos p_186262_4_) {
		BlockPos blockpos = transformedBlockPos(placementIn, p_186262_2_);
		BlockPos blockpos1 = transformedBlockPos(p_186262_3_, p_186262_4_);
		return blockpos.subtract(blockpos1);
	}

	public static BlockPos transformedBlockPos(PlacementSettings placementIn, BlockPos pos) {
		return transformedBlockPos(pos, placementIn.getMirror(), placementIn.getRotation());
	}

	public TemplateStructure getTemplateStructureFromTemplate(World worldIn, BlockPos pos, PlacementSettings placementIn) {
		return this.getTemplateStructureFromTemplate(worldIn, pos, new TwitterCityBlockRotationProcessor(pos, placementIn), placementIn, 2);
	}

	public TemplateStructure getTemplateStructureFromTemplate(World worldIn, BlockPos pos, PlacementSettings placementIn, int flags) {
		return this.getTemplateStructureFromTemplate(worldIn, pos, new TwitterCityBlockRotationProcessor(pos, placementIn), placementIn, flags);
	}
	
	public TemplateStructure getTemplateStructureFromTemplate(World worldIn, BlockPos pos,
			@Nullable ITwitterCityTemplateProcessor templateProcessor, PlacementSettings placementIn, int flags) {
		
		List<TwitterCityTemplate.BlockInfo> structureBlocks = Lists.<TwitterCityTemplate.BlockInfo>newArrayList();
		
		if ((!this.blocks.isEmpty() || !placementIn.getIgnoreEntities() && !this.entities.isEmpty())
				&& this.size.getX() >= 1 && this.size.getY() >= 1 && this.size.getZ() >= 1) {
			Block block = placementIn.getReplacedBlock();
			StructureBoundingBox structureboundingbox = placementIn.getBoundingBox();
			
			for (TwitterCityTemplate.BlockInfo template$blockinfo : this.blocks) {
				BlockPos blockpos = transformedBlockPos(placementIn, template$blockinfo.pos).add(pos);
				TwitterCityTemplate.BlockInfo template$blockinfo1 = templateProcessor != null
						? templateProcessor.processBlock(worldIn, blockpos, template$blockinfo)
						: template$blockinfo;

				if (template$blockinfo1 != null) {
					Block block1 = template$blockinfo1.blockState.getBlock();

					if ((block == null || block != block1)
							&& (!placementIn.getIgnoreStructureBlock() || block1 != Blocks.STRUCTURE_BLOCK)
							&& (structureboundingbox == null || structureboundingbox.isVecInside(blockpos))) {
						IBlockState iblockstate = template$blockinfo1.blockState.withMirror(placementIn.getMirror());
						IBlockState iblockstate1 = iblockstate.withRotation(placementIn.getRotation());

						if (template$blockinfo1.tileentityData != null) {
							TileEntity tileentity = worldIn.getTileEntity(blockpos);

							if (tileentity != null) {
								if (tileentity instanceof IInventory) {
									((IInventory) tileentity).clear();
								}
								
								structureBlocks.add(new TwitterCityTemplate.BlockInfo(blockpos, Blocks.BARRIER.getDefaultState(), null));
								//worldIn.setBlockState(blockpos, Blocks.BARRIER.getDefaultState(), 4);
							}
						}
						structureBlocks.add(new TwitterCityTemplate.BlockInfo(blockpos, iblockstate1, null));
					}
				}
			}
		}
		return new TemplateStructure(structureBlocks, this.getSize());
	}


	public BlockPos transformedSize(Rotation rotationIn) {
		switch (rotationIn) {
		case COUNTERCLOCKWISE_90:
		case CLOCKWISE_90:
			return new BlockPos(this.size.getZ(), this.size.getY(), this.size.getX());
		default:
			return this.size;
		}
	}

	private static BlockPos transformedBlockPos(BlockPos pos, Mirror mirrorIn, Rotation rotationIn) {
		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();
		boolean flag = true;

		switch (mirrorIn) {
		case LEFT_RIGHT:
			k = -k;
			break;
		case FRONT_BACK:
			i = -i;
			break;
		default:
			flag = false;
		}

		switch (rotationIn) {
		case COUNTERCLOCKWISE_90:
			return new BlockPos(k, j, -i);
		case CLOCKWISE_90:
			return new BlockPos(-k, j, i);
		case CLOCKWISE_180:
			return new BlockPos(-i, j, -k);
		default:
			return flag ? new BlockPos(i, j, k) : pos;
		}
	}

	public BlockPos getZeroPositionWithTransform(BlockPos p_189961_1_, Mirror p_189961_2_, Rotation p_189961_3_) {
		return getZeroPositionWithTransform(p_189961_1_, p_189961_2_, p_189961_3_, this.getSize().getX(),
				this.getSize().getZ());
	}

	public static BlockPos getZeroPositionWithTransform(BlockPos p_191157_0_, Mirror p_191157_1_, Rotation p_191157_2_,
			int p_191157_3_, int p_191157_4_) {
		--p_191157_3_;
		--p_191157_4_;
		int i = p_191157_1_ == Mirror.FRONT_BACK ? p_191157_3_ : 0;
		int j = p_191157_1_ == Mirror.LEFT_RIGHT ? p_191157_4_ : 0;
		BlockPos blockpos = p_191157_0_;

		switch (p_191157_2_) {
		case COUNTERCLOCKWISE_90:
			blockpos = p_191157_0_.add(j, 0, p_191157_3_ - i);
			break;
		case CLOCKWISE_90:
			blockpos = p_191157_0_.add(p_191157_4_ - j, 0, i);
			break;
		case CLOCKWISE_180:
			blockpos = p_191157_0_.add(p_191157_3_ - i, 0, p_191157_4_ - j);
			break;
		case NONE:
			blockpos = p_191157_0_.add(i, 0, j);
		}

		return blockpos;
	}

	public static void registerFixes(DataFixer fixer) {
		fixer.registerWalker(FixTypes.STRUCTURE, new IDataWalker() {
			public NBTTagCompound process(IDataFixer fixer, NBTTagCompound compound, int versionIn) {
				if (compound.hasKey("entities", 9)) {
					NBTTagList nbttaglist = compound.getTagList("entities", 10);

					for (int i = 0; i < nbttaglist.tagCount(); ++i) {
						NBTTagCompound nbttagcompound = (NBTTagCompound) nbttaglist.get(i);

						if (nbttagcompound.hasKey("nbt", 10)) {
							nbttagcompound.setTag("nbt",
									fixer.process(FixTypes.ENTITY, nbttagcompound.getCompoundTag("nbt"), versionIn));
						}
					}
				}

				if (compound.hasKey("blocks", 9)) {
					NBTTagList nbttaglist1 = compound.getTagList("blocks", 10);

					for (int j = 0; j < nbttaglist1.tagCount(); ++j) {
						NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist1.get(j);

						if (nbttagcompound1.hasKey("nbt", 10)) {
							nbttagcompound1.setTag("nbt", fixer.process(FixTypes.BLOCK_ENTITY,
									nbttagcompound1.getCompoundTag("nbt"), versionIn));
						}
					}
				}

				return compound;
			}
		});
	}

	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		TwitterCityTemplate.BasicPalette template$basicpalette = new TwitterCityTemplate.BasicPalette();
		NBTTagList nbttaglist = new NBTTagList();

		for (TwitterCityTemplate.BlockInfo template$blockinfo : this.blocks) {
			NBTTagCompound nbttagcompound = new NBTTagCompound();
			nbttagcompound.setTag("pos", this.writeInts(template$blockinfo.pos.getX(), template$blockinfo.pos.getY(),
					template$blockinfo.pos.getZ()));
			nbttagcompound.setInteger("state", template$basicpalette.idFor(template$blockinfo.blockState));

			if (template$blockinfo.tileentityData != null) {
				nbttagcompound.setTag("nbt", template$blockinfo.tileentityData);
			}

			nbttaglist.appendTag(nbttagcompound);
		}

		NBTTagList nbttaglist1 = new NBTTagList();

		for (TwitterCityTemplate.EntityInfo template$entityinfo : this.entities) {
			NBTTagCompound nbttagcompound1 = new NBTTagCompound();
			nbttagcompound1.setTag("pos",
					this.writeDoubles(template$entityinfo.pos.x, template$entityinfo.pos.y, template$entityinfo.pos.z));
			nbttagcompound1.setTag("blockPos", this.writeInts(template$entityinfo.blockPos.getX(),
					template$entityinfo.blockPos.getY(), template$entityinfo.blockPos.getZ()));

			if (template$entityinfo.entityData != null) {
				nbttagcompound1.setTag("nbt", template$entityinfo.entityData);
			}

			nbttaglist1.appendTag(nbttagcompound1);
		}

		NBTTagList nbttaglist2 = new NBTTagList();

		for (IBlockState iblockstate : template$basicpalette) {
			nbttaglist2.appendTag(NBTUtil.writeBlockState(new NBTTagCompound(), iblockstate));
		}

		net.minecraftforge.fml.common.FMLCommonHandler.instance().getDataFixer().writeVersionData(nbt); // Moved up for
																										// MC updating
																										// reasons.
		nbt.setTag("palette", nbttaglist2);
		nbt.setTag("blocks", nbttaglist);
		nbt.setTag("entities", nbttaglist1);
		nbt.setTag("size", this.writeInts(this.size.getX(), this.size.getY(), this.size.getZ()));
		nbt.setString("author", this.author);
		nbt.setInteger("DataVersion", 1343);
		return nbt;
	}

	public void read(NBTTagCompound compound) {
		this.blocks.clear();
		this.entities.clear();
		NBTTagList nbttaglist = compound.getTagList("size", 3);
		this.size = new BlockPos(nbttaglist.getIntAt(0), nbttaglist.getIntAt(1), nbttaglist.getIntAt(2));
		this.author = compound.getString("author");
		TwitterCityTemplate.BasicPalette template$basicpalette = new TwitterCityTemplate.BasicPalette();
		NBTTagList nbttaglist1 = compound.getTagList("palette", 10);

		for (int i = 0; i < nbttaglist1.tagCount(); ++i) {
			template$basicpalette.addMapping(NBTUtil.readBlockState(nbttaglist1.getCompoundTagAt(i)), i);
		}

		NBTTagList nbttaglist3 = compound.getTagList("blocks", 10);

		for (int j = 0; j < nbttaglist3.tagCount(); ++j) {
			NBTTagCompound nbttagcompound = nbttaglist3.getCompoundTagAt(j);
			NBTTagList nbttaglist2 = nbttagcompound.getTagList("pos", 3);
			BlockPos blockpos = new BlockPos(nbttaglist2.getIntAt(0), nbttaglist2.getIntAt(1), nbttaglist2.getIntAt(2));
			IBlockState iblockstate = template$basicpalette.stateFor(nbttagcompound.getInteger("state"));
			NBTTagCompound nbttagcompound1;

			if (nbttagcompound.hasKey("nbt")) {
				nbttagcompound1 = nbttagcompound.getCompoundTag("nbt");
			} else {
				nbttagcompound1 = null;
			}

			this.blocks.add(new TwitterCityTemplate.BlockInfo(blockpos, iblockstate, nbttagcompound1));
		}

		NBTTagList nbttaglist4 = compound.getTagList("entities", 10);

		for (int k = 0; k < nbttaglist4.tagCount(); ++k) {
			NBTTagCompound nbttagcompound3 = nbttaglist4.getCompoundTagAt(k);
			NBTTagList nbttaglist5 = nbttagcompound3.getTagList("pos", 6);
			Vec3d vec3d = new Vec3d(nbttaglist5.getDoubleAt(0), nbttaglist5.getDoubleAt(1), nbttaglist5.getDoubleAt(2));
			NBTTagList nbttaglist6 = nbttagcompound3.getTagList("blockPos", 3);
			BlockPos blockpos1 = new BlockPos(nbttaglist6.getIntAt(0), nbttaglist6.getIntAt(1),
					nbttaglist6.getIntAt(2));

			if (nbttagcompound3.hasKey("nbt")) {
				NBTTagCompound nbttagcompound2 = nbttagcompound3.getCompoundTag("nbt");
				this.entities.add(new TwitterCityTemplate.EntityInfo(vec3d, blockpos1, nbttagcompound2));
			}
		}
	}

	private NBTTagList writeInts(int... values) {
		NBTTagList nbttaglist = new NBTTagList();

		for (int i : values) {
			nbttaglist.appendTag(new NBTTagInt(i));
		}

		return nbttaglist;
	}

	private NBTTagList writeDoubles(double... values) {
		NBTTagList nbttaglist = new NBTTagList();

		for (double d0 : values) {
			nbttaglist.appendTag(new NBTTagDouble(d0));
		}

		return nbttaglist;
	}

	static class BasicPalette implements Iterable<IBlockState> {
		public static final IBlockState DEFAULT_BLOCK_STATE = Blocks.AIR.getDefaultState();
		final ObjectIntIdentityMap<IBlockState> ids;
		private int lastId;

		private BasicPalette() {
			this.ids = new ObjectIntIdentityMap<IBlockState>(16);
		}

		public int idFor(IBlockState state) {
			int i = this.ids.get(state);

			if (i == -1) {
				i = this.lastId++;
				this.ids.put(state, i);
			}

			return i;
		}

		@Nullable
		public IBlockState stateFor(int id) {
			IBlockState iblockstate = this.ids.getByValue(id);
			return iblockstate == null ? DEFAULT_BLOCK_STATE : iblockstate;
		}

		public Iterator<IBlockState> iterator() {
			return this.ids.iterator();
		}

		public void addMapping(IBlockState p_189956_1_, int p_189956_2_) {
			this.ids.put(p_189956_1_, p_189956_2_);
		}
	}

	public static class BlockInfo {
		/** the position the block is to be generated to */
		public final BlockPos pos;
		/** The type of block in this particular spot in the structure. */
		public final IBlockState blockState;
		/** NBT data for the tileentity */
		public final NBTTagCompound tileentityData;

		public BlockInfo(BlockPos posIn, IBlockState stateIn, @Nullable NBTTagCompound compoundIn) {
			this.pos = posIn;
			this.blockState = stateIn;
			this.tileentityData = compoundIn;
			
		}
		
	}

	public static class EntityInfo {
		/** the position the entity is will be generated to */
		public final Vec3d pos;
		/** None */
		public final BlockPos blockPos;
		/** the serialized NBT data of the entity in the structure */
		public final NBTTagCompound entityData;

		public EntityInfo(Vec3d vecIn, BlockPos posIn, NBTTagCompound compoundIn) {
			this.pos = vecIn;
			this.blockPos = posIn;
			this.entityData = compoundIn;
		}
	}
}
