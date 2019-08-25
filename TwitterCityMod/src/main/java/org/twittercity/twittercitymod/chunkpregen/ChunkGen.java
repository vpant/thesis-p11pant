package org.twittercity.twittercitymod.chunkpregen;

import java.text.DecimalFormat;

import org.apache.logging.log4j.LogManager;
import org.twittercity.twittercitymod.chunkpregen.commands.ChunkGenCommand;
import org.twittercity.twittercitymod.chunkpregen.handlers.ConfigurationHandler;
import org.twittercity.twittercitymod.chunkpregen.handlers.TickHandler;
import org.twittercity.twittercitymod.chunkpregen.reference.Reference;
import org.twittercity.twittercitymod.chunkpregen.util.Utilities;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = Reference.MOD_ID, version = Reference.VERSION, guiFactory=Reference.GUI_FACTORY, acceptableRemoteVersions = "*")
public class ChunkGen
{

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Reference.logger = LogManager.getLogger(Reference.MOD_ID);

		Reference.decimalFormat = new DecimalFormat();
		Reference.decimalFormat.setMaximumFractionDigits(2);

		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
		MinecraftForge.EVENT_BUS.register(new ConfigurationHandler());
		MinecraftForge.EVENT_BUS.register(new TickHandler());
	}

	@EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new ChunkGenCommand());
        if (Reference.x != null && Reference.z != null && Reference.depth != null && Reference.width != null && Reference.depth > 0 && Reference.width > 0 && Reference.dimension != null) {
            Reference.logger.info(String.format("Starting initial Generation in Dimension:%d with x:%d z:%d width:%d depth:%d", Reference.dimension, Reference.x, Reference.z, Reference.width, Reference.depth));
            Utilities.queueChunkGeneration(event.getServer(), Reference.x, Reference.z, Reference.width, Reference.depth, Reference.dimension, false);
        }
    }
}
