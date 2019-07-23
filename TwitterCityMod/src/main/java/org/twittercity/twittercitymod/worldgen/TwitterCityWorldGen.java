package org.twittercity.twittercitymod.worldgen;

import javax.annotation.Nullable;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TwitterCityWorldGen {
	    public static final String NAME = "twitter_city";
	    public static final int DIM_ID = findFreeDimensionID();
	    public static final DimensionType DIM_TYPE = DimensionType.register(NAME, "_"+NAME, DIM_ID, WorldProviderTwitterCity.class, true);
	    public static final WorldType TWITTER_CITY_WORLD_TYPE = new WorldTypeTwitterCity(NAME); // although instance isn't used, must create the instance to register the WorldType
	    //public static final IVillageCreationHandler CLOUD_VILLAGE_HANDLER = new VillageHouseCloudCreationHandler();
	    
	    /**
	     * Register dimensions.
	     */
	    public static final void registerDimensions() {
	        DimensionManager.registerDimension(DIM_ID, DIM_TYPE);
	    }
	    
	    @Nullable
	    private static Integer findFreeDimensionID() {
	        for (int i=2; i<Integer.MAX_VALUE; i++)
	        {
	            if (!DimensionManager.isDimensionRegistered(i))
	            {
	                // DEBUG
	                System.out.println("Found free dimension ID = "+i);
	                return i;
	            }
	        }
	        
	        // DEBUG
	        System.out.println("ERROR: Could not find free dimension ID");
	        return null;
	    }

	    /**
	     * Register world generators.
	     */
	    public static void registerWorldGenerators() {
	        // DEBUG
	        System.out.println("Registering world generators");
	        GameRegistry.registerWorldGenerator(new WorldGenTwitterCity(), 10);
	    }
}
