package com.hashwave;


import java.util.Random;

public class RandomGen {
	
	Random rand;
	public RandomGen (){
		rand = new Random();
	}
	
	public int generateRandom (int end, int length){
		int n = 0;
		for(int x=0;x<length;x++){
			n += rand.nextInt(end);
		}
		return n;
	}
}
