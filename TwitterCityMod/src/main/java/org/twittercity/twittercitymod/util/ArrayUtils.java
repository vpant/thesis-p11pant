package org.twittercity.twittercitymod.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.twittercity.twittercitymod.TwitterCity;

public class ArrayUtils {

	public static void print2DArray (int[][] array2D)
	{
		for (int i = 0; i < array2D.length; i++)
		{
			for (int y = 0; y < array2D[1].length; y++)
			{
				System.out.print(array2D[i][y] + " ");
			}
			System.out.println();
		}
	}
	
	public static void print2DArrayToFile (int[][] array2D)
	{
		Writer candidateOutput = null;
		File candidateFile = new File("2DArrayToFile");
		try {
			candidateOutput = new BufferedWriter(new FileWriter(candidateFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		for (int i = 0; i < array2D.length; i++) {
			for (int y = 0; y < array2D[1].length; y++) {
				//System.out.print(array2D[i][y] + " ");
				try {
					candidateOutput.write(array2D[i][y] + " ");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				candidateOutput.write(System.getProperty("line.separator"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			candidateOutput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int[][] rotateArray (int[][] array, int rotate)
	{
		if (rotate == 0) {
			return array;
		}
		else {
			int[][] newArray;
			if(rotate == 2) {
				newArray = new int[array.length][array[1].length];
			}
			else {
				newArray = new int[array[1].length][array.length];
			}
			
			for (int x = 0; x < array.length; x++) {
				for (int y = 0; y < array[1].length; y++) {
					switch (rotate) {
						case 1:
							newArray[y][x] = array[x][y];
							break;
						case 2:
							newArray[x][y] = array[(array.length - 1) - x][(array[1].length - 1) - y];
							break;
						case 3:
							newArray[(newArray.length - 1) - y][(newArray[1].length - 1) - x] = array[x][y];
							break;
						default:
							TwitterCity.logger.error("Invalid switch result");
					}
				}
			}
			return newArray;
		}
	}

	public static boolean isArraySectionAllZeros2D(int[][] array, int x1, int y1, int x2, int y2) {
		boolean valid = true;
		for (int a = x1; a <= x2 && valid; a++) {
			for (int b = y1; b <= y2 && valid; b++) {
				if(array[a][b] > 0) {
					valid = false;
				}
			}
		}
		return valid;
	}

	public static int zerosInArray2D(int[][] array) {
		int wasted = 0;
		for (int i = 0; i < array.length; i++) {
			for (int z = 0; z < array[1].length; z++) {
				if (array[i][z] == 0) {
					wasted ++;
				}
			}
		}
		return wasted;
	}
}
