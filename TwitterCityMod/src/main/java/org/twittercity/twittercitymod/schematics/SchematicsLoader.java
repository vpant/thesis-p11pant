package org.twittercity.twittercitymod.schematics;

import java.io.InputStream;

import org.twittercity.twittercitymod.Reference;
import org.twittercity.twittercitymod.TwitterCity;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class SchematicsLoader {
	public Schematic get(String res) {
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("assets/" + Reference.MOD_ID + "/schematics/"+res);
            if(is==null) {
            	TwitterCity.logger.error("I can't load schematic, because I can't open file. ");
            	return null;
            }

            NBTTagCompound nbtData = CompressedStreamTools.readCompressed(is);
            short width = nbtData.getShort("Width");
            short height = nbtData.getShort("Height");
            short length = nbtData.getShort("Length");

            byte[] blocks = nbtData.getByteArray("Blocks");
            byte[] data = nbtData.getByteArray("Data");
            TwitterCity.logger.info("Loaded schem size: " + width + " x " + height + " x " + length);
            NBTTagList tileentities = nbtData.getTagList("TileEntities", 10);
            is.close();
            return new Schematic(tileentities, width, height, length, blocks, data);
        } catch (Exception e) {
        	TwitterCity.logger.error("I can't load schematic, because " + e.toString());
            return null;
        }
    }

    public class Schematic { 
        public  NBTTagList tileEntities;
        public  short width;
        public  short height;
        public short length;
        public byte[] blocks;
        public byte[] data;
        public Schematic(NBTTagList tileEntities, short width, short height, short length, byte[] blocks, byte[] data) {
            this.tileEntities = tileEntities;
            this.width = width;
            this.height = height;
            this.length = length;
            this.blocks = blocks;
            this.data = data;
        }

    }
}
