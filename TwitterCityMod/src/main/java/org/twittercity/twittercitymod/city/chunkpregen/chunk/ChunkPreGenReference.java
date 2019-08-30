package org.twittercity.twittercitymod.city.chunkpregen.chunk;

import java.util.LinkedList;

public class ChunkPreGenReference {
	public static double numChunksPerTick = 1.0; // Number of chunks loaded per tick
	public static boolean pauseForPlayers = false; // Pause chunk generation when players are logged on
	public static Integer maxChunksLoaded = 3000; // Pause chunk generation if more chunks than this are in memory

	public static LinkedList<ChunkPosition> toGenerate = new LinkedList<>();
	public static int startingSize;
	public static int updateDelay = 40; // Number of chunks inbetween percentage updates

	public static boolean isPreGenFinished = false;
}
