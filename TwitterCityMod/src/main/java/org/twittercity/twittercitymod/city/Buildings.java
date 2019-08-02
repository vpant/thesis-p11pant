package org.twittercity.twittercitymod.city;

import java.util.HashMap;

import org.twittercity.twittercitymod.DebugData;
import org.twittercity.twittercitymod.TwitterCity;
import org.twittercity.twittercitymod.city.templatestructures.TemplateStructure;
import org.twittercity.twittercitymod.util.BlockHelper;
import org.twittercity.twittercitymod.util.RandomHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStandingSign;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockWallSign;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class Buildings {
	
	private static HashMap<BlockPos, IBlockState> buildLast = new HashMap<BlockPos, IBlockState>();
	
	private Buildings() {
		// Do nothing, this is a class to create the buildings
	}
	
	public static void makeInsideCity(World world, int area[][], City city) {
		makeBuildings(world, area, city); 
		if(city.hasPaths()) {
			joinPathsToRoad(world, city);
		}
		else {
			removePaths(world, area, city);
		}
		if(city.hasMainStreets()) {
			makeStreetLights(world, city);
		}
	}
	
	private static void makeStreetLights(World world, City city) {
		for (int a = city.getPathExtends() + 1; a <= (city.getMapLength() / 2) - (city.getEdgeLength() + 16); a += 8) {
			makeStreetLight(world, city, (city.getMapLength() / 2) - a, (city.getMapLength() / 2) - (city.getPathExtends() + 1),
                                         (city.getMapLength() / 2) - a, (city.getMapLength() / 2) - city.getPathExtends());
			makeStreetLight(world, city, (city.getMapLength() / 2) - a, (city.getMapLength() / 2) + (city.getPathExtends() + 1),
                                         (city.getMapLength() / 2) - a, (city.getMapLength() / 2) + city.getPathExtends());
			makeStreetLight(world, city, (city.getMapLength() / 2) + a, (city.getMapLength() / 2) - (city.getPathExtends() + 1),
                                         (city.getMapLength() / 2) + a, (city.getMapLength() / 2) - city.getPathExtends());
			makeStreetLight(world, city, (city.getMapLength() / 2) + a, (city.getMapLength() / 2) + (city.getPathExtends() + 1),
                                         (city.getMapLength() / 2) + a, (city.getMapLength() / 2) + city.getPathExtends());

			makeStreetLight(world, city, (city.getMapLength() / 2) - (city.getPathExtends() + 1), (city.getMapLength() / 2) - a,
                                         (city.getMapLength() / 2) - city.getPathExtends(), (city.getMapLength() / 2) - a);
			makeStreetLight(world, city, (city.getMapLength() / 2) + (city.getPathExtends() + 1), (city.getMapLength() / 2) - a,
                                         (city.getMapLength() / 2) + city.getPathExtends(), (city.getMapLength() / 2) - a);
			makeStreetLight(world, city, (city.getMapLength() / 2) - (city.getPathExtends() + 1), (city.getMapLength() / 2) + a,
                                         (city.getMapLength() / 2) - city.getPathExtends(), (city.getMapLength() / 2) + a);
			makeStreetLight(world, city, (city.getMapLength() / 2) + (city.getPathExtends() + 1), (city.getMapLength() / 2) + a,
                                         (city.getMapLength() / 2) + city.getPathExtends(), (city.getMapLength() / 2) + a);
         }
	}

	private static void makeStreetLight(World world, City city, int x1, int z1, int x2, int z2) {
		if(world.getBlockState(new BlockPos(x1, 0, z1).add(city.getStartingPos())) == city.getGroundBlock().getDefaultState() &&
				world.getBlockState(new BlockPos(x1, 1, z1).add(city.getStartingPos())) == Blocks.AIR.getDefaultState() &&
				world.getBlockState(new BlockPos(x1, 2, z1).add(city.getStartingPos())) == Blocks.AIR.getDefaultState()) 
		{
			world.setBlockState(new BlockPos(x1, 1, z1 ).add(city.getStartingPos()), Blocks.OAK_FENCE.getDefaultState());
			world.setBlockState(new BlockPos(x1, 2, z1 ).add(city.getStartingPos()), Blocks.OAK_FENCE.getDefaultState());
			world.setBlockState(new BlockPos(x1, 3, z1 ).add(city.getStartingPos()), Blocks.OAK_FENCE.getDefaultState());
			world.setBlockState(new BlockPos(x1, 4, z1 ).add(city.getStartingPos()), Blocks.OAK_FENCE.getDefaultState());
			world.setBlockState(new BlockPos(x1, 5, z1 ).add(city.getStartingPos()), Blocks.OAK_FENCE.getDefaultState());
			world.setBlockState(new BlockPos(x1, 5, z1 ).add(city.getStartingPos()), Blocks.OAK_FENCE.getDefaultState());
			world.setBlockState(new BlockPos(x1, 4, z1 ).add(city.getStartingPos()), Blocks.GLOWSTONE.getDefaultState());
		}
		
	}

	private static void joinPathsToRoad(World world, City city) {
		int halfPlotBlocksLength = (((city.getPathExtends() / 2) + city.getMapLength()) - (2 * city.getBlockStart())) / 2;
		for(int a = 0; a <= 2 * halfPlotBlocksLength; a++) {
			for(int b = -(city.getPathExtends() + 1); b <= city.getPathExtends() + 1; b += (city.getPathExtends() + 1) * 2) {
				if(world.getBlockState(new BlockPos(city.getBlockStart() + a, 0, city.getBlockStart() + halfPlotBlocksLength + b + Integer.signum(b)).add(city.getStartingPos())) == city.getPathBlock().getDefaultState()
					&& (world.getBlockState(new BlockPos(city.getBlockStart() + a, 0, city.getBlockStart() + halfPlotBlocksLength + b).add(city.getStartingPos())) == city.getGroundBlock().getDefaultState()
					|| world.getBlockState(new BlockPos(city.getBlockStart() + a, 0, city.getBlockStart() + halfPlotBlocksLength + b).add(city.getStartingPos())) == city.getPathBlock().getDefaultState())
					&& world.getBlockState(new BlockPos(city.getBlockStart() + a, 1, city.getBlockStart() + halfPlotBlocksLength + b).add(city.getStartingPos())) == Blocks.AIR.getDefaultState())
				{
					world.setBlockState(new BlockPos(city.getBlockStart() + a, 0, city.getBlockStart() + halfPlotBlocksLength + b).add(city.getStartingPos()), city.getPathBlock().getDefaultState());
					world.setBlockState(new BlockPos(city.getBlockStart() + a, 1, city.getBlockStart() + halfPlotBlocksLength + b - Integer.signum(b)).add(city.getStartingPos()), Blocks.AIR.getDefaultState());					
				}
				if(world.getBlockState(new BlockPos(city.getBlockStart() + halfPlotBlocksLength + b + Integer.signum(b), 0, city.getBlockStart() + a).add(city.getStartingPos())) == city.getPathBlock().getDefaultState()
						&& world.getBlockState(new BlockPos(city.getBlockStart() + halfPlotBlocksLength + (b - Integer.signum(b)), 0, city.getBlockStart() + a).add(city.getStartingPos())) == city.getPathBlock().getDefaultState()
						&& (world.getBlockState(new BlockPos(city.getBlockStart() + halfPlotBlocksLength + b, 0, city.getBlockStart() + a).add(city.getStartingPos())) == city.getGroundBlock().getDefaultState()
						|| world.getBlockState(new BlockPos(city.getBlockStart() + halfPlotBlocksLength + b, 0, city.getBlockStart() + a).add(city.getStartingPos())) == city.getPathBlock().getDefaultState())
						&& world.getBlockState(new BlockPos(city.getBlockStart() + halfPlotBlocksLength + b, 1, city.getBlockStart() + a).add(city.getStartingPos())) == Blocks.AIR.getDefaultState()) 
				{
					world.setBlockState(new BlockPos(city.getBlockStart() + halfPlotBlocksLength + b, 0,city.getBlockStart() + a).add(city.getStartingPos()), city.getPathBlock().getDefaultState());
					world.setBlockState(new BlockPos(city.getBlockStart() + halfPlotBlocksLength + b - Integer.signum(b), 1,city.getBlockStart() + a).add(city.getStartingPos()), Blocks.AIR.getDefaultState());
				}
			}
		}
		
	}

	//Remove paths if they are not activated in the current city
	private static void removePaths(World world, int[][] area, City city) {
		for(int x = 0; x < area.length; x++) {
			for(int z = 0; z < area[1].length; z++) {
				if(area[x][z] == 1) {
					world.setBlockState(new BlockPos(city.getBlockStart() + x, 0, city.getBlockStart() + z).add(city.getStartingPos()), city.getGroundBlock().getDefaultState());
				}
			}
		}
	}

	/*
	 * Inserts all the buildings that are defined by id in the area array
	 */
	public static void makeBuildings(World world, int area[][], City city) {
		int buildingsCount = 0;
		Building[] buildings = Buildings.getAllBuildings();
		if(buildings == null) {
			return;
		}
		for (int x = 0; x < area.length; x++) {
			for(int z = 0; z < area[1].length; z++) {
				if(area[x][z] >= 100 && area[x][z] <= 500 ) {
					int buildingID = area[x][z] - 100;
					if(buildingID >= 0 && buildingID < buildings.length && buildingsCount == 0) {
						Building currentBuilding = buildings[area[x][z] - 100];
						insertBuilding(world, city, area, x, z, currentBuilding, -1);
						
						//insertBuilding(world, city, area, x, z, currentBuilding, 2);
						area[x + currentBuilding.getSizeX() - 2][z + currentBuilding.getSizeZ() - 2] = 0;
						buildingsCount++;
					}
				}
			}
		}
		System.out.println("Number of buildings " + buildingsCount);
	}
	
	/**
	 * Spawns a building in the world
	 * @param world World object
	 * @param city Current city
	 * @param area 2D Representation of the city
	 * @param x1dest X offset of the building for x = 0
	 * @param z1dest Z offset of the building for z = 0
	 * @param building Current building
	 * @param rotationFixed Predifined rotation. If negative, rotation will be calculated.
	 */
	private static void insertBuildingStartingFrom(World world, City city, int[][] area, int x1dest, int z1dest, Building building, int rotationFixed) {
		buildLast.clear();
		int sourceX = 0, sourceZ = 0;
		int rotate = getBuildingRotation(building, area, x1dest, z1dest, rotationFixed);
		TemplateStructure templateStructure = building.getTemplateStructure(world);
		for(int x = 0; x < building.getSizeX(); x++) {
			for(int z = 0; z < building.getSizeZ(); z++) {
				switch(rotate) {
					case 0:
						sourceX = x;
						sourceZ = z;
						break;
					case 1:
						sourceX = (building.getSizeX() - 1) - z;
						sourceZ = x;
						break;
					case 2:
						sourceX = z;
						sourceZ = (building.getSizeZ() - 1) - x;
						break;
					case 3:
						sourceX = (building.getSizeX() - 1) - x;
						sourceZ = (building.getSizeZ() - 1) - z;
						break;
					default:
						TwitterCity.logger.debug("Invalid switch result!");
						break;
				}
				
				int sourceEndY;
				for (sourceEndY = templateStructure.getSize().getY(); sourceEndY > 0; sourceEndY--) {
					BlockPos pos = new BlockPos(sourceX + building.getSourceX(), sourceEndY + 63, sourceZ + building.getSourceZ());
					IBlockState bState = templateStructure.getBlockStateFromBlockPos(pos);
					if(bState != null && bState.getBlock() != Blocks.AIR) {
						break;
            		} 
        		}
				
				for(int ySource = building.getSourceStartY() - 64; ySource <= sourceEndY; ySource++) {
					BlockPos currentPos = new BlockPos(city.getBlockStart() + x + x1dest, ySource, city.getBlockStart() + z + z1dest);
					insertBuildingBlock(world, city, building, currentPos, sourceX, sourceZ, rotate);
				}
			}
		}
		buildLast(buildLast, world);
	}
	
	private static void insertBuilding(World world, City city, int[][] area, int x1dest, int z1dest, Building building, int rotationFixed) {
		insertBuildingStartingFrom(world, city, area, x1dest, z1dest, building, rotationFixed);
	}
	

	private static void insertBuildingBlock(World world, City city, Building building, BlockPos currentPos, int sourceX, int sourceZ, int rotate) {
		TemplateStructure templateStructure = building.getTemplateStructure(world);
		int structureY = currentPos.getY() + 64;
		currentPos = currentPos.add(city.getStartingPos().getX(), city.getStartingPos().getY() + 1, city.getStartingPos().getZ());
		if ((currentPos.getY() != city.getStartingPos().getY() + 1 || world.getBlockState(currentPos).getBlock() == Blocks.AIR)
				&& world.getBlockState(currentPos).getBlock() != Blocks.PLANKS) { 
			IBlockState blockState = templateStructure.getBlockStateFromBlockPos(new BlockPos(sourceX + building.getSourceX(), structureY, sourceZ + building.getSourceZ()));
			if(blockState == null) {
				return;
			}
			Block block = blockState.getBlock();

			if(!BlockHelper.needsToBeBuildedLast(block)) {
				if(BlockHelper.isDoor(block)) {
					blockState = blockState.withProperty(BlockDoor.OPEN, false);
				} else if(block == Blocks.TRAPDOOR) {
					blockState = blockState.withProperty(BlockTrapDoor.OPEN, false);
				}
				if (rotate > 0) {
					if(block == Blocks.STANDING_SIGN) {
						world.setBlockState(currentPos, blockState.withProperty(BlockStandingSign.ROTATION, BlockHelper.rotateStandingSign(blockState.getValue(BlockStandingSign.ROTATION).intValue(), rotate))); 
						addTextToSign(block);
					} else if(BlockHelper.isPumpkin(block)) {
						world.setBlockState(currentPos, blockState.withProperty(BlockPumpkin.FACING, BlockHelper.cardinalRotation(blockState.getValue(BlockPumpkin.FACING), rotate)));
					} else if(BlockHelper.isDoor(block)) {
						world.setBlockState(currentPos, blockState.withProperty(BlockDoor.FACING, BlockHelper.cardinalRotation(blockState.getValue(BlockDoor.FACING), rotate))); 
					} else if(block == Blocks.TRAPDOOR) {
						world.setBlockState(currentPos, blockState.withProperty(BlockTrapDoor.FACING, BlockHelper.cardinalRotation(blockState.getValue(BlockTrapDoor.FACING), rotate)));
					} else if(block == Blocks.BED) { 
						BlockHelper.spawnRotatedBed(world, block, currentPos, blockState, rotate);
					} else if(BlockHelper.isFenceGate(block)) {
						world.setBlockState(currentPos, blockState.withProperty(BlockFenceGate.FACING, BlockHelper.cardinalRotation(blockState.getValue(BlockPistonBase.FACING), rotate)));
					} else if(BlockHelper.isPistonBasePart(block)) {
						world.setBlockState(currentPos, blockState.withProperty(BlockPistonBase.FACING, BlockHelper.cardinalRotation(blockState.getValue(BlockPistonBase.FACING), rotate)));
					} else if(BlockHelper.isPistonPart(block)) {
						world.setBlockState(currentPos, blockState.withProperty(BlockPistonExtension.FACING, BlockHelper.cardinalRotation(blockState.getValue(BlockPistonExtension.FACING), rotate)));
					} else if(BlockHelper.isStairs(block)) {
						world.setBlockState(currentPos, blockState.withProperty(BlockStairs.FACING, BlockHelper.cardinalRotation(blockState.getValue(BlockStairs.FACING), rotate)));
					} else { // default
						world.setBlockState(currentPos, BlockHelper.replaceWithTCBlockState(blockState));
					}
                    
                } // if rotate > 0
				else if(block == Blocks.VINE || BlockHelper.isRepeater(block) || block == Blocks.RAIL || BlockHelper.isMushroom(block)) {
					//do not spawn
				}
				else {
					// Bed needs to notify other blocks no matter if it was rotated
					if(block == Blocks.BED) {
						BlockHelper.spawnRotatedBed(world, block, currentPos, blockState, rotate);
					} else {
						world.setBlockState(currentPos, BlockHelper.replaceWithTCBlockState(blockState));
					}
				}
				
				if((block == Blocks.LIT_REDSTONE_ORE || block == Blocks.REDSTONE_ORE) && currentPos.getY() == city.getStartingPos().getY()) {
					world.setBlockState(currentPos, city.getGroundBlock().getDefaultState());
				} else if(block == Blocks.LAPIS_ORE){ 
					world.setBlockState(currentPos, BlockHelper.replaceWithTCBlockState(Blocks.WOOL.getDefaultState()));
				} else if(block == Blocks.GOLD_ORE && currentPos.getY() == city.getStartingPos().getY()) {
					world.setBlockState(currentPos, city.getPathBlock().getDefaultState());
				} else if(block == Blocks.CHEST) {
					TileEntityChest chest = (TileEntityChest) world.getTileEntity(currentPos);
					if(chest != null) {
						addItemsToChest(chest.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH));					
					}
				} else if(block == Blocks.BREWING_STAND || block == Blocks.NOTEBLOCK) {
					world.setBlockState(currentPos, blockState);
				} 
			} // !isBlockToBuildLast
			else {
				/*
				 * Blocks that must spawn last
				 */
				if(BlockHelper.isTorch(block)) {
					blockState = (rotate > 0) ? blockState.withProperty(BlockTorch.FACING, BlockHelper.cardinalRotation(blockState.getValue(BlockTorch.FACING), rotate)) : blockState;
					buildLast.put(currentPos, blockState);
				} else if(block == Blocks.LEVER) {
					blockState = (rotate > 0) ? blockState.withProperty(BlockLever.FACING, BlockHelper.rotateLever(blockState.getValue(BlockLever.FACING), rotate)) : blockState;
					buildLast.put(currentPos, blockState);
				} else if(block == Blocks.WALL_SIGN || block == Blocks.LADDER || block == Blocks.DISPENSER
						|| block == Blocks.CHEST || block == Blocks.FURNACE || block == Blocks.LIT_FURNACE) {
					blockState = (rotate > 0) ? blockState.withProperty(BlockWallSign.FACING, BlockHelper.cardinalRotation(blockState.getValue(BlockWallSign.FACING), rotate)) : blockState;
					buildLast.put(currentPos, blockState);
					if(block == Blocks.WALL_SIGN) {
						addTextToSign(block);
					}
				} else if(block == Blocks.STONE_BUTTON) {
					blockState = (rotate > 0) ? blockState.withProperty(BlockButton.FACING, BlockHelper.cardinalRotation(blockState.getValue(BlockButton.FACING), rotate)) : blockState;
					buildLast.put(currentPos, blockState);
				}
			}
		}	
	}

	/*
	 * Determines the rotation of the building from the area array
	 */
	private static int getBuildingRotation(Building building, int[][] area, int x1dest, int z1dest, int rotationFixed) {
		int rotate = -1;
		if(rotationFixed >= 0) {
			rotate = rotationFixed;
		}
		else {
			for(int distance = 0; rotate == -1 && distance < 10; distance++) {
				for(int check = 0; rotate == -1 && check <= building.getSizeX(); check++) {
					if (checkArea(area, x1dest + check, z1dest - distance) == 1) {
	                    rotate = 0;
	                }
	                else if (checkArea(area, x1dest - distance, z1dest + check) == 1) {
	                    rotate = 1;
	                }
	                else if (checkArea(area, x1dest + building.getSizeX() + distance, z1dest + check) == 1) {
	                    rotate = 2;
	                }
	                else if (checkArea(area, x1dest + check, z1dest + building.getSizeZ() + distance) == 1) {
	                    rotate = 3;
	                }
				}
			}
		}
		if (rotate == -1) {
			rotate = RandomHelper.nextInt(4);
		}
		return rotate;
	}

	/*
	 * Builds that blocks that are needed to built last
	 */
	private static void buildLast(HashMap<BlockPos,IBlockState> blockMap, World world) {
		if(blockMap.isEmpty()) {
			return;
		}
		for ( BlockPos blockPos : blockMap.keySet() ) {
		    world.setBlockState(blockPos, blockMap.get(blockPos));
		}
	}
	
	private static void addTextToSign(Block block) {
		
	}
	
	/*
	 * Add items to chests
	 */
	private static void addItemsToChest(IItemHandler itemHanlder) {
		Item[] items = { Items.APPLE, Items.COOKIE, Items.BREAD, Items.BOOK, Items.BONE, Items.FISHING_ROD, Items.WOODEN_AXE, 
				Items.EGG, Items.BOAT, Items.BUCKET, Items.GLASS_BOTTLE, Items.BOWL, Items.PAPER, Items.WOODEN_SWORD, Items.WOODEN_SHOVEL, Items.WHEAT}; 
		for(int i = RandomHelper.nextInt(3, 6); i >= 0; i--) {
			itemHanlder.insertItem(i, new ItemStack(items[RandomHelper.nextInt(items.length)], RandomHelper.nextInt(2,8)), false);
		}
	}

	/*
	 * Checks the area for to determine proper building rotation
	 */
	private static int checkArea(int[][] area, int x, int z) {
		if(x >= 0 && z >= 0 && x < area.length && z < area[1].length) {
			return area[x][z];
		}
		else {
			return 0;	
		}
	}
	
	/*
	 * Returns a random building
	 */
	public static Building selectRandomBuilding (Building[] buildings)
	{
		int fail = 0;
		Frequency[] frequencies = Frequency.values();
		int[] frequencyWeights = new int[] { 9, 8, 7, 6, 5 };
		
		int frequency = RandomHelper.randomWeightedNumber(frequencyWeights);
		int buildingID = 0;
		boolean valid;
		
		do {
			if (++fail >= 100) {
				frequency = RandomHelper.randomWeightedNumber(frequencyWeights);
				fail = 0;
			}
			buildingID = RandomHelper.nextInt(buildings.length);
			
			valid = buildings[buildingID].getFrequency() == frequencies[frequency];
		} while(!valid);
		
		return buildings[buildingID];
	}
	
	public static Building[] getAllBuildings() {
		return DebugData.buildings;
	}
}
