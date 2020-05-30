package org.twittercity.twittercitymod.city.templatestructures;

import java.util.List;

import org.twittercity.twittercitymod.TwitterCity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

public class TemplateStructure {
	
	// Holds all the BlockInfo objects of the structure
	private List<TwitterCityTemplate.BlockInfo> blockInfo; 
	private BlockPos size;
	
	public TemplateStructure() {
		
	}
	
	public TemplateStructure(List<TwitterCityTemplate.BlockInfo> blockInfo, BlockPos size) {
		
		this.blockInfo = blockInfo;
		this.size = size;
	}
	
	public BlockPos getSize() {
		return size;
	}
	
	public List<TwitterCityTemplate.BlockInfo> getBlockInfoList() {
		return blockInfo;
	}
	
	public IBlockState getBlockStateFromBlockPos(BlockPos pos) {
		for(TwitterCityTemplate.BlockInfo blockInfo : this.blockInfo) {
			if(pos.equals(blockInfo.pos)) {
				return blockInfo.blockState;
			}
		}
		TwitterCity.logger.debug("There is no block with these coordinates: " + pos.toString());
		return null;
	}
}
