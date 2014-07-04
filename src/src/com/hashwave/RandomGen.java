package com.hashwave;


import java.util.Random;
import java.util.ArrayList;

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
	
	public String generateRandomLN (int end, int slength){
		String[] lettersA = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
		ArrayList<String> letters = new ArrayList<String>();  
		
		for(int x=0; x < lettersA.length; x++){
			letters.add(x,lettersA[x]);
		}
		String n = "";
		int i = 0;
		while (i < slength){
			n += letters.get(rand.nextInt(end));
			i++;
		}
		return n;
	}
	
	public int getRandom () {
		int[] numbers = {1,2,3,4,5};
		int rands = rand.nextInt(5);
		return numbers[rands];
	}
}
