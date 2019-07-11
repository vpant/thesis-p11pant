package org.twittercity.twittercitymod.city;

import org.twittercity.twittercitymod.DebugData;
import org.twittercity.twittercitymod.TwitterCity;
import org.twittercity.twittercitymod.util.RandomHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWallSign;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class Buildings {
	
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
		Block streetLight = Blocks.GLOWSTONE;
		 for (int a = city.getPathExtends() + 1; a <= (city.getMapLength() / 2) - (city.getEdgeLength() + 16); a += 8)
         {
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
		if(world.getBlockState(new BlockPos(city.getX() + x1, 63, city.getZ() + z1)) == city.getGroundBlock() &&
				world.getBlockState(new BlockPos(city.getX() + x1, 64, city.getZ() + z1)) == Blocks.AIR.getDefaultState() &&
				world.getBlockState(new BlockPos(city.getX() + x1, 65, city.getZ() + z1)) == Blocks.AIR.getDefaultState()) 
		{
			world.setBlockState(new BlockPos(city.getX() + x1, 64, city.getZ() + z1 ), Blocks.OAK_FENCE.getDefaultState());
			world.setBlockState(new BlockPos(city.getX() + x1, 65, city.getZ() + z1 ), Blocks.OAK_FENCE.getDefaultState());
			world.setBlockState(new BlockPos(city.getX() + x1, 66, city.getZ() + z1 ), Blocks.OAK_FENCE.getDefaultState());
			world.setBlockState(new BlockPos(city.getX() + x1, 67, city.getZ() + z1 ), Blocks.OAK_FENCE.getDefaultState());
			world.setBlockState(new BlockPos(city.getX() + x1, 68, city.getZ() + z1 ), Blocks.OAK_FENCE.getDefaultState());
			world.setBlockState(new BlockPos(city.getX() + x1, 68, city.getZ() + z1 ), Blocks.OAK_FENCE.getDefaultState());
			world.setBlockState(new BlockPos(city.getX() + x1, 67, city.getZ() + z1 ), Blocks.GLOWSTONE.getDefaultState());
		}
		
	}

	private static void joinPathsToRoad(World world, City city) {
		int halfPlotBlocksLength = (((city.getPathExtends() / 2) + city.getMapLength()) - (2 * city.getBlockStart())) / 2;
		for(int a = 0; a <= 2 * halfPlotBlocksLength; a++) {
			for(int b = -(city.getPathExtends() + 1); b <= city.getPathExtends() + 1; b += (city.getPathExtends() + 1) * 2) {
				if(world.getBlockState(new BlockPos(city.getX() + city.getBlockStart() + a, 63, city.getZ() + city.getBlockStart() + halfPlotBlocksLength + b + Integer.signum(b))) == city.getPathBlock().getDefaultState()
					&& (world.getBlockState(new BlockPos(city.getX() + city.getBlockStart() + a, 63, city.getZ() + city.getBlockStart() + halfPlotBlocksLength + b)) == city.getGroundBlock().getDefaultState()
					|| world.getBlockState(new BlockPos(city.getX() + city.getBlockStart() + a, 63, city.getZ() + city.getBlockStart() + halfPlotBlocksLength + b)) == city.getPathBlock().getDefaultState())
					&& world.getBlockState(new BlockPos(city.getX() + city.getBlockStart() + a, 64, city.getZ() + city.getBlockStart() + halfPlotBlocksLength + b)) == Blocks.AIR.getDefaultState())
				{
					world.setBlockState(new BlockPos(city.getX() + city.getBlockStart() + a, 63, city.getZ() + city.getBlockStart() + halfPlotBlocksLength + b), city.getPathBlock().getDefaultState());
					world.setBlockState(new BlockPos(city.getX() + city.getBlockStart() + a, 64, city.getZ() + city.getBlockStart() + halfPlotBlocksLength + b - Integer.signum(b)), Blocks.AIR.getDefaultState());					
				}
				if(world.getBlockState(new BlockPos(city.getX() + city.getBlockStart() + halfPlotBlocksLength + b + Integer.signum(b), 63, city.getZ() + city.getBlockStart() + a)) == city.getPathBlock().getDefaultState()
						&& world.getBlockState(new BlockPos(city.getX() + city.getBlockStart() + halfPlotBlocksLength + (b - Integer.signum(b)), 63, city.getZ() + city.getBlockStart() + a)) == city.getPathBlock().getDefaultState()
						&& (world.getBlockState(new BlockPos(city.getX() + city.getBlockStart() + halfPlotBlocksLength + b, 63, city.getZ() + city.getBlockStart() + a)) == city.getGroundBlock().getDefaultState()
						|| world.getBlockState(new BlockPos(city.getX() + city.getBlockStart() + halfPlotBlocksLength + b, 63, city.getZ() + city.getBlockStart() + a)) == city.getPathBlock().getDefaultState())
						&& world.getBlockState(new BlockPos(city.getX() + city.getBlockStart() + halfPlotBlocksLength + b, 64, city.getZ() + city.getBlockStart() + a)) == Blocks.AIR.getDefaultState()) 
				{
					world.setBlockState(new BlockPos(city.getX() + city.getBlockStart() + halfPlotBlocksLength + b, 63, city.getZ() + city.getBlockStart() + a), city.getPathBlock().getDefaultState());
					world.setBlockState(new BlockPos(city.getX() + city.getBlockStart() + halfPlotBlocksLength + b - Integer.signum(b), 64, city.getZ() + city.getBlockStart() + a), Blocks.AIR.getDefaultState());
				}
			}
		}
		
	}

	private static void removePaths(World world, int[][] area, City city) {
		for(int x = 0; x < area.length; x++) {
			for(int z = 0; z < area[1].length; z++) {
				if(area[x][z] == 1) {
					world.setBlockState(new BlockPos(city.getX() + city.getBlockStart() + x, 63, city.getZ() + city.getBlockStart() + z), city.getGroundBlock().getDefaultState());
				}
			}
		}
		
	}

	public static void makeBuildings(World world, int area[][], City city) {
		int buildingsCount = 0;
		Building[] buildings = Buildings.getAllBuildings();
		for (int x = 0; x < area.length; x++) {
			for(int z = 0; z < area[1].length; z++) {
				if(area[x][z] >= 100 && area[x][z] <= 500 ) {
					Building currentBuilding = buildings[area[x][z] - 100];

					insertBuilding(world, area, x, z, currentBuilding, 0, -1, city);
					
					area[x + currentBuilding.getSizeX() - 2][z + currentBuilding.getSizeZ() - 2] = 0;
					buildingsCount++;
				}
			}
		}
		
		System.out.println("Number of buildings " + buildingsCount);
	}

	private static void insertBuilding(World world, int[][] area, int x1dest, int z1dest,
			Building building, int y1dest, int rotationFixed, City city) {
		
		int randomWoolColour = RandomHelper.nextInt(16);
		
		int sourceX = 0, sourceZ = 0;
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
				//Checks if blocks from y = 128 to y = 64 are not blocks of air
				for (sourceEndY = 128; sourceEndY > 64; sourceEndY--) {
            
					if (world.getBlockState(new BlockPos(sourceX + building.getSourceX(), sourceEndY, sourceZ + building.getSourceZ())).getBlock() != Blocks.AIR ) {
						break;
            		} 
        		}
				
				
				for(int ySource = building.getSourceStartY(); ySource <= sourceEndY; ySource++) {
					int yDest = ySource;
					BlockPos currentPos = new BlockPos(city.getX() + city.getBlockStart() + x + x1dest, yDest, city.getZ() + city.getBlockStart() + z + z1dest);
					if ((yDest != 64 || world.getBlockState(currentPos).getBlock() == Blocks.AIR)
							&& world.getBlockState(currentPos).getBlock() != Blocks.PLANKS) { 
						Block block = world.getBlockState(new BlockPos(sourceX + building.getSourceX(), ySource, sourceZ + building.getSourceZ())).getBlock(); 
						world.setBlockState(currentPos, block.getDefaultState());
						
						//region import paintings
						if (rotate > 0)
		                {
							if(block == Blocks.STANDING_SIGN) {
								world.setBlockState(currentPos, block.getDefaultState());
							} else if(block == Blocks.STONE_BUTTON) {
								world.setBlockState(currentPos, block.getDefaultState());
							} else if(block == Blocks.PUMPKIN || block == Blocks.LIT_PUMPKIN) {
								world.setBlockState(currentPos, block.getDefaultState());
							} else if(block == Blocks.VINE) {
								world.setBlockState(currentPos, block.getDefaultState());
							} else if(block == Blocks.BROWN_MUSHROOM || block == Blocks.RED_MUSHROOM) {
								world.setBlockState(currentPos, block.getDefaultState());
							} else if(block == Blocks.IRON_DOOR) {
								world.setBlockState(currentPos, block.getDefaultState());
							} else if(block == Blocks.TRAPDOOR) {
								world.setBlockState(currentPos, block.getDefaultState());
							} else if(block == Blocks.BED) {
								world.setBlockState(currentPos, block.getDefaultState());
							} else if(block == Blocks.ACACIA_FENCE_GATE || block == Blocks.BIRCH_FENCE_GATE || block == Blocks.DARK_OAK_FENCE_GATE
									|| block == Blocks.JUNGLE_FENCE_GATE || block == Blocks.OAK_FENCE_GATE || block == Blocks.SPRUCE_FENCE_GATE) {
								world.setBlockState(currentPos, block.getDefaultState());
							} else if(block == Blocks.PISTON || block == Blocks.STICKY_PISTON) {
								world.setBlockState(currentPos, block.getDefaultState());
							} else if(block == Blocks.PISTON_HEAD || block == Blocks.PISTON_EXTENSION) {
								world.setBlockState(currentPos, block.getDefaultState());
							} else if(block == Blocks.POWERED_REPEATER || block == Blocks.UNPOWERED_REPEATER) {
								world.setBlockState(currentPos, block.getDefaultState());
							} else if(block == Blocks.UNLIT_REDSTONE_TORCH || block == Blocks.REDSTONE_TORCH || block == Blocks.TORCH) {
								world.setBlockState(currentPos, block.getDefaultState());
							} else if(block == Blocks.LEVER) {
								world.setBlockState(currentPos, block.getDefaultState());
							} else if(block == Blocks.WALL_SIGN || block == Blocks.LADDER || block == Blocks.DISPENSER
									|| block == Blocks.CHEST || block == Blocks.FURNACE || block == Blocks.LIT_FURNACE) {
								world.setBlockState(currentPos, block.getDefaultState());
							} else if(block == Blocks.STONE_BRICK_STAIRS || block == Blocks.BRICK_STAIRS || block == Blocks.NETHER_BRICK_STAIRS
									 || block == Blocks.STONE_STAIRS || block == Blocks.BIRCH_STAIRS) {
								world.setBlockState(currentPos, block.getDefaultState());
							}
		                    
		                } // if rotate > 0
						
						if((block == Blocks.LIT_REDSTONE_ORE || block == Blocks.REDSTONE_ORE) && yDest == 63) {
							world.setBlockState(currentPos, city.getGroundBlock().getDefaultState());
						} else if(block == Blocks.GOLD_ORE && yDest == 63) {
							world.setBlockState(currentPos, city.getPathBlock().getDefaultState());
						} else if(block == Blocks.CHEST) {
							//spawn chest
						} else if(block == Blocks.DISPENSER || block == Blocks.BREWING_STAND || block == Blocks.FURNACE || block == Blocks.NOTEBLOCK) {
							//do something
						} else if(block == Blocks.STANDING_SIGN || block == Blocks.WALL_SIGN) {
							//get text for the sign
						}
						
					}
				}
			}
		}
	} // insertBuilding End

	private static int checkArea(int[][] area, int x, int z) {
		if(x >= 0 && z >= 0 && x < area.length && z < area[1].length) {
			return area[x][z];
		}
		else {
			return 0;	
		}
	}
	
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
