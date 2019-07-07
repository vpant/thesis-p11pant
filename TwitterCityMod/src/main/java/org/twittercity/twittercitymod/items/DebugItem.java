package org.twittercity.twittercitymod.items;

import org.twittercity.twittercitymod.DebugData;
import org.twittercity.twittercitymod.schematics.SchematicsLoader;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class DebugItem extends ItemBase{

	public DebugItem(String name) {
		super(name);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		int[][] area = null;
		if (!world.isRemote) {
			DebugData.setupData();		
			//ChunksEditor.editCityChunks(worldIn, DebugData.firstCity);
			//area = Paths.makePaths(worldIn, DebugData.firstCity);
			//Buildings.makeInsideCity(worldIn, area, DebugData.firstCity);
			//ArrayUtils.print2DArrayToFile(area);
			
			if(!world.isRemote){

		        SchematicsLoader.Schematic sh = new SchematicsLoader().get("city.schematic");
		        if(sh==null){
		            playerIn.sendMessage(new TextComponentString("Schematic is ded!"));
		            //world.playSoundAtEntity(playerIn, "microz:other.ded", 0.5F, 1.0F);
		            //this.setUnlocalizedName("builder_corrupt");
		        
		            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
		        }

		       playerIn.sendMessage(new TextComponentString("Building started."));

		        int i = 0;
		        for(int sy = 0; sy < sh.height; sy++)
		        for(int sz = 0; sz < sh.length; sz++)
		        for(int sx = 0; sx < sh.width; sx++){

		                Block b = Block.getBlockById(sh.blocks[i]);
		                if(b != Blocks.AIR)
		                {
		                    //int rx = sut.blockCoordsRotation(sx - this.getxShift(), sz, rotation)[0];
		                    //int rz = sut.blockCoordsRotation(sx - this.getxShift(), sz, rotation)[1];
		                    //world.setBlockToAir(x + rx, y + ylevel + sy, z + rz);
		                    //world.setBlock(x+rx, y+ylevel+sy, z+rz, b, sut.rotateMeta(sh.blocks[i], sh.data[i], rotation), 2);
		                }
		                i++;
		        }

		        if (sh.tileEntities != null)
		        {
		            for (int i1 = 0; i1 < sh.tileEntities.tagCount(); ++i1)
		            {
		                NBTTagCompound nbttagcompound4 = sh.tileEntities.getCompoundTagAt(i1);
		                TileEntity tileEntity = TileEntity.create(world,nbttagcompound4);

		                if (tileEntity != null)
		                {
		                    //int[] conv2 = sh.blockCoordsRotation(tileEntity.xCoord - this.getxShift(), tileEntity.zCoord, rotation);
		                    //tileEntity.xCoord = x + conv2[0];
		                   //tileEntity.yCoord += y+ylevel;
		                    //tileEntity.zCoord = z + conv2[1];
		                    //world.setTileEntity(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, tileEntity);
		                }
		            }
		        }

		        return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
		        }
		        return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
		}
			
		return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
	}
}
