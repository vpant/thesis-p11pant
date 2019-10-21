package org.twittercity.twittercitymod.config;

import org.twittercity.twittercitymod.Reference;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;
import net.minecraftforge.common.config.Config.RequiresMcRestart;

@Config(modid = Reference.MOD_ID)
public class ConfigurationManager {
    
	@Name("Twitter cities")
    @Comment("Options regarding the cities that are builded by the mod")
    public static final BuildingOptions buildingOptions = new BuildingOptions();
		
	public static class BuildingOptions {
		@Name("1) Spawn all blocks immediately")
        @Comment
        ({
        	"Spawn blocks immediately or queue them to spawn.", 
        	"Caution: Spawning all the blocks at once can cause serius lag."
        })
		public BinarySetting spawnImmediately = BinarySetting.Enable;
		@Name("2) Unbreakable city")
        @Comment
        ({
        	"Breaking blocks that are inside the city will cause them to respawn.", 
        	"If this is disabled broken blocks will not respawn ever."
        })
		public BinarySetting unbreakableCity = BinarySetting.Disable;
		@Name("3) Colored blocks")
        @Comment
        ({
        	"Color the blocks based on the emotion that are expressing"
        })
		@RequiresMcRestart
		public BinarySetting coloredBlocks = BinarySetting.Enable;
		
		
        @Name("4) Blocks spawned per tick.")
        @Comment
        ({
        	"How many blocks will be spawned per tick when spawn all blocks immediately is disabled.",
        	"The bigger the number the faster the city will be builded.",
        	"Reduce this setting if you experience lag caused by the building process."
        })
        @RangeInt(min = 0, max = 100)
        public int blocksPerTick = 10;
        
        @Name("5) Minutes between database checks")
        @Comment
        ({
        	"How many minutes should pass before checking database for new tweets.",
        })
        @RangeInt(min = 0, max = 60)
        public int minutesBetweenCheckingForNewTweets = 1;
	}
	
	public enum BinarySetting
    {
        Enable(true),
        Disable(false);
        
		private boolean bool;
		
        private BinarySetting(boolean bool) {
        	this.bool = bool;
        }
        
        public boolean isEnabled() {
        	return bool;
        }
    }
}

