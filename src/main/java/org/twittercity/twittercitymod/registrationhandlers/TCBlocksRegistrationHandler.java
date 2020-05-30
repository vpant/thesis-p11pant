package org.twittercity.twittercitymod.registrationhandlers;

import org.twittercity.twittercitymod.Reference;
import org.twittercity.twittercitymod.blocks.TCBlock;
import org.twittercity.twittercitymod.blocks.TCBlockColored;
import org.twittercity.twittercitymod.blocks.TCBlockNewLog;
import org.twittercity.twittercitymod.blocks.TCBlockOldLog;
import org.twittercity.twittercitymod.blocks.TCBlockPlanks;
import org.twittercity.twittercitymod.blocks.TCBlockSandStone;
import org.twittercity.twittercitymod.blocks.TCBlockStone;
import org.twittercity.twittercitymod.blocks.TCBlockStoneBrick;
import org.twittercity.twittercitymod.blocks.TCBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemCloth;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class TCBlocksRegistrationHandler {

	public static void init() {
		TCBlocks.STONE = (TCBlockStone) new TCBlockStone("tc_stone").setHardness(1.5F).setResistance(10.0F).setUnlocalizedName("stone");
		TCBlocks.WOOL = (TCBlockColored) new TCBlockColored("tc_wool").setHardness(0.8F).setUnlocalizedName("cloth");
		TCBlocks.PLANKS = (TCBlockPlanks) new TCBlockPlanks("tc_planks").setHardness(2.0F).setResistance(5.0F).setUnlocalizedName("wood");
		TCBlocks.LOG = (TCBlockOldLog) new TCBlockOldLog("tc_log").setUnlocalizedName("log");
		TCBlocks.LOG2 = (TCBlockNewLog) new TCBlockNewLog("tc_log2").setUnlocalizedName("log");
		TCBlocks.COBBLESTONE = (TCBlock) new TCBlock("tc_cobblestone", Material.ROCK, SoundType.STONE).setHardness(2.0F).setResistance(10.0F).setUnlocalizedName("stonebrick");
		TCBlocks.MOSSY_COBBLESTONE = (TCBlock) new TCBlock("tc_mossy_cobblestone", Material.ROCK, SoundType.STONE).setHardness(2.0F).setResistance(10.0F).setUnlocalizedName("stoneMoss");
		TCBlocks.STONEBRICK = (TCBlockStoneBrick) new TCBlockStoneBrick("tc_stonebrick").setHardness(1.5F).setResistance(10.0F).setUnlocalizedName("stonebricksmooth");
		TCBlocks.BRICK_BLOCK = (TCBlock) new TCBlock("tc_brick_block", Material.ROCK, MapColor.RED, SoundType.STONE).setHardness(2.0F).setResistance(10.0F).setUnlocalizedName("brick");
		TCBlocks.SANDSTONE = (TCBlockSandStone) new TCBlockSandStone("tc_sandstone").setHardness(0.8F).setUnlocalizedName("sandStone");
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(TCBlocks.STONE, TCBlocks.WOOL, TCBlocks.PLANKS, TCBlocks.LOG, TCBlocks.LOG2, 
				TCBlocks.COBBLESTONE, TCBlocks.MOSSY_COBBLESTONE, TCBlocks.STONEBRICK, TCBlocks.BRICK_BLOCK, TCBlocks.SANDSTONE);
	}

	@SubscribeEvent
	public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
		event.getRegistry().register((new ItemMultiTexture(TCBlocks.STONE, TCBlocks.STONE, new ItemMultiTexture.Mapper() {
				public String apply(ItemStack stack) {
					return TCBlockStone.EnumType.byMetadata(stack.getMetadata()).getUnlocalizedName();
				}
			})).setUnlocalizedName("tc_stone").setRegistryName(TCBlocks.STONE.getRegistryName()));
		event.getRegistry().register(new ItemCloth(TCBlocks.WOOL).setRegistryName(TCBlocks.WOOL.getRegistryName()));
		event.getRegistry().register(new ItemMultiTexture(TCBlocks.PLANKS, TCBlocks.PLANKS, new ItemMultiTexture.Mapper() {
				public String apply(ItemStack stack) {
					return TCBlockPlanks.EnumType.byMetadata(stack.getMetadata()).getUnlocalizedName();
				}
			}).setUnlocalizedName("wood").setRegistryName(TCBlocks.PLANKS.getRegistryName()));
		event.getRegistry().register(new ItemMultiTexture(TCBlocks.LOG, TCBlocks.LOG, new ItemMultiTexture.Mapper() {
            public String apply(ItemStack stack) {
                return TCBlockPlanks.EnumType.byMetadata(stack.getMetadata()).getUnlocalizedName();
            }
        }).setUnlocalizedName("log").setRegistryName(TCBlocks.LOG.getRegistryName()));
		event.getRegistry().register(new ItemMultiTexture(TCBlocks.LOG2, TCBlocks.LOG2, new ItemMultiTexture.Mapper() {
            public String apply(ItemStack stack) {
                return TCBlockPlanks.EnumType.byMetadata(stack.getMetadata() + 4).getUnlocalizedName();
            }
        }).setUnlocalizedName("log").setRegistryName(TCBlocks.LOG2.getRegistryName()));
		event.getRegistry().register(new ItemBlock(TCBlocks.COBBLESTONE).setRegistryName(TCBlocks.COBBLESTONE.getRegistryName()));
		event.getRegistry().register(new ItemBlock(TCBlocks.MOSSY_COBBLESTONE).setRegistryName(TCBlocks.MOSSY_COBBLESTONE.getRegistryName()));
		event.getRegistry().register((new ItemMultiTexture(TCBlocks.STONEBRICK, TCBlocks.STONEBRICK, new ItemMultiTexture.Mapper() {
            public String apply(ItemStack stack) {
                return TCBlockStoneBrick.EnumType.byMetadata(stack.getMetadata()).getUnlocalizedName();
            }
        })).setUnlocalizedName("stonebricksmooth").setRegistryName(TCBlocks.STONEBRICK.getRegistryName()));
		event.getRegistry().register(new ItemBlock(TCBlocks.BRICK_BLOCK).setRegistryName(TCBlocks.BRICK_BLOCK.getRegistryName()));
		event.getRegistry().register((new ItemMultiTexture(TCBlocks.SANDSTONE, TCBlocks.SANDSTONE, new ItemMultiTexture.Mapper() {
            public String apply(ItemStack stack) {
                return TCBlockSandStone.EnumType.byMetadata(stack.getMetadata()).getUnlocalizedName();
            }
        })).setUnlocalizedName("sandStone").setRegistryName(TCBlocks.SANDSTONE.getRegistryName()));
	}

	@SubscribeEvent
	public static void registerRenders(ModelRegistryEvent event) {
		for (TCBlockStone.EnumType variant : TCBlockStone.EnumType.values()) {
			registerRender(Item.getItemFromBlock(TCBlocks.STONE), variant.getMetadata(),
					new ResourceLocation(Reference.MOD_ID, variant.getName()));
		}
		for (TCBlockPlanks.EnumType variant : TCBlockPlanks.EnumType.values()) {
			registerRender(Item.getItemFromBlock(TCBlocks.PLANKS), variant.getMetadata(),
					new ResourceLocation("minecraft", variant.getName() + "_planks"));
			registerRender(Item.getItemFromBlock(TCBlocks.LOG), variant.getMetadata(),
					new ResourceLocation("minecraft", variant.getName() + "_log"));
			registerRender(Item.getItemFromBlock(TCBlocks.LOG2), variant.getMetadata(),
					new ResourceLocation("minecraft", variant.getName() + "_log"));
		}
		for (EnumDyeColor color : EnumDyeColor.values()) {
			registerRender(Item.getItemFromBlock(TCBlocks.WOOL), color.getMetadata(),
					new ResourceLocation("minecraft", color.getName() + "_wool"));
		}
		registerRender(Item.getItemFromBlock(TCBlocks.COBBLESTONE), 0, new ResourceLocation("minecraft", "cobblestone"));
		registerRender(Item.getItemFromBlock(TCBlocks.MOSSY_COBBLESTONE), 0, new ResourceLocation("minecraft", "mossy_cobblestone"));
		for (TCBlockStoneBrick.EnumType variant : TCBlockStoneBrick.EnumType.values()) {
			registerRender(Item.getItemFromBlock(TCBlocks.STONEBRICK), variant.getMetadata(),
					new ResourceLocation("minecraft", variant.getName()));
		}
		registerRender(Item.getItemFromBlock(TCBlocks.BRICK_BLOCK), 0, new ResourceLocation("minecraft", "brick_block"));
		for (TCBlockSandStone.EnumType type : TCBlockSandStone.EnumType.values()) {
			registerRender(Item.getItemFromBlock(TCBlocks.SANDSTONE), type.getMetadata(),
					new ResourceLocation("minecraft", type.getName()));
		}
	}

	public static void registerRender(Item item, int meta, ResourceLocation rs) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(rs, "inventory"));
	}
}
