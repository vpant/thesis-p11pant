package org.twittercity.twittercitymod.registrationhandlers;

import org.twittercity.twittercitymod.Reference;
import org.twittercity.twittercitymod.blocks.TCBlockColored;
import org.twittercity.twittercitymod.blocks.TCBlockPlanks;
import org.twittercity.twittercitymod.blocks.TCBlockStone;
import org.twittercity.twittercitymod.blocks.TCBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
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
		TCBlocks.PLANKS = (TCBlockPlanks) (new TCBlockPlanks("tc_planks")).setHardness(2.0F).setResistance(5.0F).setUnlocalizedName("wood");
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(TCBlocks.STONE, TCBlocks.WOOL, TCBlocks.PLANKS);
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

	}

	@SubscribeEvent
	public static void registerRenders(ModelRegistryEvent event) {
		for (TCBlockStone.EnumType variant : TCBlockStone.EnumType.values()) {
			registerRender(Item.getItemFromBlock(TCBlocks.STONE), variant.getMetadata(),
					new ResourceLocation(Reference.MOD_ID, variant.getName()));
		}
		for (EnumDyeColor color : EnumDyeColor.values()) {
			registerRender(Item.getItemFromBlock(TCBlocks.WOOL), color.getMetadata(),
					new ResourceLocation("minecraft", color.getName() + "_wool"));
		}
	}

	public static void registerRender(Item item, int meta, ResourceLocation rs) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(rs, "inventory"));
	}
}
