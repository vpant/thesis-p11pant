package org.twittercity.twittercitymod.util;

import java.util.Random;
import java.util.stream.IntStream;

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
	
	public static double nextDouble ()
	{
		if (rand == null)
		{
			setRandomSeed();
		}
		return rand.nextDouble();
	}

	public static int randomWeightedNumber(int[] weights) {
		int max = 0;
		int choice = IntStream.of(weights).sum();
		
		choice = RandomHelper.nextInt(choice);
		for (int item = 0; item <= (weights.length - 1); item ++ )
		{
			max += weights[item];
			if (max > choice)
			{
				return item;
			}
			
		}	
		return 0;
	}
}
