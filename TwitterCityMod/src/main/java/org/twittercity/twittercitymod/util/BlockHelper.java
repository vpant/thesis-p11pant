package org.twittercity.twittercitymod.util;

import java.util.List;

import org.twittercity.twittercitymod.TwitterCity;
import org.twittercity.twittercitymod.blocks.TCBlock;
import org.twittercity.twittercitymod.blocks.TCBlockColored;
import org.twittercity.twittercitymod.blocks.TCBlockNewLog;
import org.twittercity.twittercitymod.blocks.TCBlockOldLog;
import org.twittercity.twittercitymod.blocks.TCBlockPlanks;
import org.twittercity.twittercitymod.blocks.TCBlockSandStone;
import org.twittercity.twittercitymod.blocks.TCBlockStone;
import org.twittercity.twittercitymod.blocks.TCBlockStoneBrick;
import org.twittercity.twittercitymod.blocks.TCBlocks;
import org.twittercity.twittercitymod.city.lazyblockspawn.LazyBlockSpawnQueue;
import org.twittercity.twittercitymod.config.TwitterCityConfiguration;

import com.sun.istack.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockLever.EnumOrientation;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockHelper {

	public static EnumFacing cardinalRotation(EnumFacing facing, int rotation) {
		switch (rotation) {
			case 1:
				switch (facing) {
					case UP:
					case DOWN:
						return facing;
					case NORTH:
						return EnumFacing.WEST;
					case EAST:
						return EnumFacing.NORTH;
					case SOUTH:
						return EnumFacing.EAST;
					case WEST:
						return EnumFacing.SOUTH;
					default:
						TwitterCity.logger.error("Invalid facing in switch statement.");
						return facing;
				}
			case 2:
				switch (facing) {
				case UP:
				case DOWN:
					return facing;
				case NORTH:
					return EnumFacing.EAST;
				case EAST:
					return EnumFacing.SOUTH;
				case SOUTH:
					return EnumFacing.WEST;
				case WEST:
					return EnumFacing.NORTH;	
				default:
					TwitterCity.logger.error("Invalid facing in switch statement.");
				}
			case 3:
				switch (facing) {
				case UP:
				case DOWN:
					return facing;
				case NORTH:
					return EnumFacing.SOUTH;
				case EAST:
					return EnumFacing.WEST;
				case SOUTH:
					return EnumFacing.NORTH;
				case WEST:
					return EnumFacing.EAST;	
				default:
					TwitterCity.logger.error("Invalid facing in switch statement.");
				}
			default:
				return facing;
		}
	}	
	
	public static EnumOrientation rotateLever(EnumOrientation orientation, int rotation) {
		switch (rotation) {
            case 1:
                switch (orientation) {
                    case NORTH: return EnumOrientation.WEST;
                    case EAST: return EnumOrientation.NORTH; 
                    case SOUTH:return EnumOrientation.EAST; 
                    case WEST: return EnumOrientation.SOUTH; 
                    default: TwitterCity.logger.error("Invalid facing in switch statement.");
                }
                break;
            case 2:
                switch (orientation) {
                	case NORTH: return EnumOrientation.EAST; 
                	case EAST: return EnumOrientation.SOUTH; 
                	case SOUTH:return EnumOrientation.WEST; 
                	case WEST: return EnumOrientation.NORTH; 
                    default: TwitterCity.logger.error("Invalid facing in switch statement.");
                }
                break;
            case 3:
                switch (orientation) {
                	case NORTH: return EnumOrientation.SOUTH; 
                	case EAST: return EnumOrientation.WEST; 
                	case SOUTH:return EnumOrientation.NORTH; 
                	case WEST: return EnumOrientation.EAST; 
                    default: TwitterCity.logger.error("Invalid facing in switch statement.");
                }
                break;
            default:
				TwitterCity.logger.error("Invalid facing in switch statement.");
				return orientation;
        }
		return orientation;
	}
	
	public static boolean needsToBeBuildedLast(Block block) {
		if(block == Blocks.UNLIT_REDSTONE_TORCH || block == Blocks.REDSTONE_TORCH || block == Blocks.TORCH || block == Blocks.LEVER 
			|| block == Blocks.WALL_SIGN || block == Blocks.LADDER || block == Blocks.DISPENSER || block == Blocks.CHEST 
			|| block == Blocks.FURNACE || block == Blocks.LIT_FURNACE || block == Blocks.STONE_BUTTON) {
			return true;
		}
		return false;
	}
	
	public static boolean isStairs(Block block) {
		if(block == Blocks.STONE_BRICK_STAIRS || block == Blocks.BRICK_STAIRS || block == Blocks.NETHER_BRICK_STAIRS
				 || block == Blocks.STONE_STAIRS || block == Blocks.BIRCH_STAIRS || block == Blocks.ACACIA_STAIRS 
				 || block == Blocks.DARK_OAK_STAIRS || block == Blocks.JUNGLE_STAIRS || block == Blocks.OAK_STAIRS
				 || block == Blocks.PURPUR_STAIRS || block == Blocks.QUARTZ_STAIRS || block == Blocks.RED_SANDSTONE_STAIRS
				 || block == Blocks.SANDSTONE_STAIRS || block == Blocks.SPRUCE_STAIRS) 
		{
			return true;
		}
		return false;
	}
	
	public static boolean isFenceGate(Block block) {
		if(block == Blocks.ACACIA_FENCE_GATE || block == Blocks.BIRCH_FENCE_GATE || block == Blocks.DARK_OAK_FENCE_GATE
				|| block == Blocks.JUNGLE_FENCE_GATE || block == Blocks.OAK_FENCE_GATE || block == Blocks.SPRUCE_FENCE_GATE) {
			return true;
		}
		return false;
	}
	
	public static boolean isPistonPart(Block block) {
		if(block == Blocks.PISTON_HEAD || block == Blocks.PISTON_EXTENSION) {
			return true;
		}
		return false;
	}
	
	public static boolean isPistonBasePart(Block block) {
		if(block == Blocks.PISTON || block == Blocks.STICKY_PISTON) {
			return true;
		}
		return false;
	}
	
	public static boolean isTorch(Block block) {
		if(block == Blocks.UNLIT_REDSTONE_TORCH || block == Blocks.REDSTONE_TORCH || block == Blocks.TORCH) {
			return true;
		}
		return false;
	}
	
	public static boolean isDoor(Block block) {
		if(block == Blocks.IRON_DOOR || block == Blocks.DARK_OAK_DOOR || block == Blocks.ACACIA_DOOR || block == Blocks.BIRCH_DOOR 
				|| block == Blocks.JUNGLE_DOOR || block == Blocks.OAK_DOOR || block == Blocks.SPRUCE_DOOR) {
			return true;
		}
		return false;
	}

	public static boolean isMushroom(Block block) {
		if(block == Blocks.BROWN_MUSHROOM || block == Blocks.RED_MUSHROOM) {
			return true;
		}
		return false;
	}
	
	public static boolean isPumpkin(Block block) {
		if(block == Blocks.PUMPKIN || block == Blocks.LIT_PUMPKIN) {
			return true;
		}
		return false;
	}
	
	public static boolean isRepeater(Block block) {
		if(block == Blocks.POWERED_REPEATER || block == Blocks.UNPOWERED_REPEATER) {
			return true;
		}
		return false;
	}

	public static int rotateStandingSign(int value, int rotate) {
		switch (rotate)
        {
        	case 1: //COUNTERCLOCKWISE_90: 
        		return (value + 16 * 3 / 4) % 16;
            case 2: //CLOCKWISE_90:
            	return (value + 16 / 4) % 16;
            case 3: //CLOCKWISE_180:
            	return (value + 16 / 2) % 16;
            default:
                return value;
        }
	}

	public static void enqueueRotatedBedForSpawn(World world, BlockPos currentPos, IBlockState blockState, int rotation) {	
		if(blockState.getValue(BlockBed.PART).equals(BlockBed.EnumPartType.HEAD)) {
			return;
		}
		
		EnumFacing enumFacing = BlockHelper.cardinalRotation(blockState.getValue(BlockBed.FACING), rotation);
		IBlockState iBlockState2 = Blocks.BED.getDefaultState().withProperty(BlockBed.OCCUPIED, Boolean.valueOf(false)).withProperty(BlockBed.FACING, enumFacing).withProperty(BlockBed.PART, BlockBed.EnumPartType.FOOT);
		BlockPos blockPos = currentPos.offset(enumFacing);
		
		LazyBlockSpawnQueue.enqueueBlockForSpawn(new BlockData(currentPos, iBlockState2, 10, true));
		LazyBlockSpawnQueue.enqueueBlockForSpawn(new BlockData(blockPos, iBlockState2.withProperty(BlockBed.PART, BlockBed.EnumPartType.HEAD), 10, true));
	}
	

	public static void spawnRotatedBed(World world, BlockPos currentPos, IBlockState blockState, int rotation) {	
		if(blockState.getValue(BlockBed.PART).equals(BlockBed.EnumPartType.HEAD)) {
			return;
		}
		
		EnumFacing enumFacing = BlockHelper.cardinalRotation(blockState.getValue(BlockBed.FACING), rotation);
		IBlockState iBlockState2 = Blocks.BED.getDefaultState().withProperty(BlockBed.OCCUPIED, Boolean.valueOf(false)).withProperty(BlockBed.FACING, enumFacing).withProperty(BlockBed.PART, BlockBed.EnumPartType.FOOT);
		BlockPos blockPos = currentPos.offset(enumFacing);
		world.setBlockState(currentPos, iBlockState2, 10);
		world.setBlockState(blockPos, iBlockState2.withProperty(BlockBed.PART, BlockBed.EnumPartType.HEAD), 10);
        
		world.notifyNeighborsRespectDebug(currentPos, Blocks.BED, false);
		world.notifyNeighborsRespectDebug(blockPos, blockState.getBlock(), false); 
	}
	
	public static IBlockState replaceWithTCBlockState(IBlockState vanillaBlockState) {
		Block vanillaBlock = vanillaBlockState.getBlock();
		IBlockState tcBlockState = vanillaBlockState;
		if(vanillaBlock == Blocks.STONE) {
			tcBlockState = TCBlocks.STONE.getDefaultState().withProperty(TCBlockStone.VARIANT, TCBlockStone.EnumType.byMetadata(vanillaBlockState.getValue(BlockStone.VARIANT).getMetadata()));
		} else if(vanillaBlock == Blocks.WOOL) {
			tcBlockState = TCBlocks.WOOL.getDefaultState().withProperty(TCBlockColored.COLOR, EnumDyeColor.byMetadata(vanillaBlockState.getValue(BlockColored.COLOR).getMetadata()));
		} else if(vanillaBlock == Blocks.PLANKS) {
			tcBlockState = TCBlocks.PLANKS.getDefaultState().withProperty(TCBlockPlanks.VARIANT, TCBlockPlanks.EnumType.byMetadata(vanillaBlockState.getValue(BlockPlanks.VARIANT).getMetadata()));
		} else if(vanillaBlock == Blocks.LOG) {
			tcBlockState = TCBlocks.LOG.getDefaultState().withProperty(TCBlockOldLog.VARIANT, TCBlockPlanks.EnumType.byMetadata(vanillaBlockState.getValue(BlockOldLog.VARIANT).getMetadata()));
		} else if(vanillaBlock == Blocks.LOG2) {
			tcBlockState = TCBlocks.LOG2.getDefaultState().withProperty(TCBlockNewLog.VARIANT, TCBlockPlanks.EnumType.byMetadata(vanillaBlockState.getValue(BlockNewLog.VARIANT).getMetadata()));
		} else if(vanillaBlock == Blocks.COBBLESTONE) {
			tcBlockState = TCBlocks.COBBLESTONE.getDefaultState();
		} else if(vanillaBlock == Blocks.MOSSY_COBBLESTONE) {
			tcBlockState = TCBlocks.MOSSY_COBBLESTONE.getDefaultState();
		} else if(vanillaBlock == Blocks.STONEBRICK) {
			tcBlockState = TCBlocks.STONEBRICK.getDefaultState()
					.withProperty(TCBlockStoneBrick.VARIANT, TCBlockStoneBrick.EnumType.byMetadata(vanillaBlockState.getValue(BlockStoneBrick.VARIANT).getMetadata()));
		} else if(vanillaBlock == Blocks.BRICK_BLOCK) {
			tcBlockState = TCBlocks.BRICK_BLOCK.getDefaultState();
		} else if(vanillaBlock == Blocks.SANDSTONE) {
			tcBlockState = TCBlocks.SANDSTONE.getDefaultState().withProperty(TCBlockSandStone.TYPE, TCBlockSandStone.EnumType.byMetadata(vanillaBlockState.getValue(BlockSandStone.TYPE).getMetadata()));
		}
		return tcBlockState;
	}

	/**
	 * Checks if block's neighbors are instance of TCBlock (or it's children)
	 * @param world
	 * @param pos
	 * @return
	 */
	public static boolean isTCBlockNeighbor(World world, BlockPos pos) {
		for(int x = -1; x <= 1; x++) {
			for(int z = -1; z <= 1; z++) {
				for(int y = -1; y <= 1; y++) {
					BlockPos nextPos = new BlockPos(x, y, z).add(pos);
					if(!nextPos.equals(pos) && (world.getBlockState(nextPos).getBlock() instanceof TCBlock)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Spawns or enqueue a block for spawning
	 */
	public static void spawnOrEnqueue(BlockData blockData, @Nullable World world) {
		if(TwitterCityConfiguration.spawnImmediately && world != null) {
			world.setBlockState(blockData.pos, blockData.blockState, blockData.flags);
		} else {
			LazyBlockSpawnQueue.enqueueBlockForSpawn(blockData);
		}
	}
	
	public static void spawnOrEnqueue(BlockData blockData) {
		spawnOrEnqueue(blockData, null);
	}
	
	public static void spawnOrEnqueue(BlockPos pos, IBlockState state, int flags) {
		spawnOrEnqueue(new BlockData(pos, state, flags));
	}
	
	public static void spawnOrEnqueue(BlockPos pos, IBlockState state, int flags, World world) {
		spawnOrEnqueue(new BlockData(pos, state, flags), world);
	}
	
	public static void spawnOrEnqueue(BlockPos pos, IBlockState state) {
		spawnOrEnqueue(new BlockData(pos, state, 3));
	}
	
	public static void spawnOrEnqueue(BlockPos pos, IBlockState state, World world) {
		spawnOrEnqueue(new BlockData(pos, state, 3), world);
	}
	
	/**
	 * Spawns or enqueues a list of BlockData for spawning
	 */
	public static void spawnOrEnqueue(List<BlockData> blockList, @Nullable World world) {
		if(blockList.isEmpty()) {
			return;
		}
		if(TwitterCityConfiguration.spawnImmediately && world != null) { 
			for(BlockData blockData : blockList) {
				spawnOrEnqueue(blockData, world);
			}
		} else {
			LazyBlockSpawnQueue.enqeueBlockListForSpawn(blockList);
		}
	}
	
	public static void spawnOrEnqueueRotatedBed(World world, BlockPos currentPos, IBlockState blockState, int rotation) {
		if(TwitterCityConfiguration.spawnImmediately && world != null) {
			spawnRotatedBed(world, currentPos, blockState, rotation);
		} else {
			enqueueRotatedBedForSpawn(world, currentPos, blockState, rotation);
		}
	}
}
