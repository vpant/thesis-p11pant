package org.twittercity.twittercity.util;

import java.util.Random;

public class RandomHelper {
	
	private static Random rand;
	
	public static void setSeed (int seed)
	{
		rand = new Random(seed);
	}
	
	public static void setRandomSeed()
	{
		rand = new Random();
	}
	
	public static int nextInt()
	{
		if (rand == null)
		{
			setRandomSeed();
		}
		return rand.nextInt();
	}
	
	public static int nextInt(int max)
	{
		if (rand == null)
		{
			setRandomSeed();
		}
		return rand.nextInt(max);
	}
	
	public static int nextInt (int min, int max)
	{
		if (rand == null)
		{
			setRandomSeed();
		}
		return (rand.nextInt(max - min) + min);
	}
}
