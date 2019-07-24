package org.twittercity.twittercitymod.worldgen;

import org.twittercity.twittercitymod.Reference;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

@ObjectHolder(Reference.MOD_ID)
public class TwitterCityBiomes {

	// instantiate Biomes
	public final static BiomeTwitterCity twitter_city = null;

	@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
	public static class RegistrationHandler {
		/**
		 * Register this mod's {@link Biome}s.
		 *
		 * @param event The event
		 */
		@SubscribeEvent
		public static void onEvent(final RegistryEvent.Register<Biome> event) {
			final IForgeRegistry<Biome> registry = event.getRegistry();

			System.out.println("Registering biomes");

			registry.register(new BiomeTwitterCity().setRegistryName(Reference.MOD_ID, TwitterCityWorldGen.NAME));
		}
	}

	/**
	 * This method should be called during the "init" FML lifecycle because it must
	 * happen after object handler injection.
	 */
	public static void initBiomeManagerAndDictionary() {
		BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(twitter_city, 10));
		BiomeManager.addSpawnBiome(twitter_city);
		BiomeDictionary.addTypes(twitter_city, BiomeDictionary.Type.COLD, BiomeDictionary.Type.DRY,
				BiomeDictionary.Type.MAGICAL);
	}
}
