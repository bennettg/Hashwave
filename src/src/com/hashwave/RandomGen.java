package com.hashwave;


import java.util.Random;

public class RandomGen {
	
	Random rand;
	public RandomGen (){
		rand = new Random();
	}
	
	public String generateRandom (int end, int length){
		String n = "";
		int i = 0;
		while (i < length){
			n += rand.nextInt(end);
			i++;
		}
		return n;
	}
}
