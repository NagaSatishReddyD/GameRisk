package com.soen6441.risk;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Dice class is to handle the dice event in the attack phase
 * @author Naga Satish Reddy
 *
 */
public class Dice {
	
	/**
	 * Returns an integer array of values between 1 and 6 representing the outcome of rolling the dice.
	 * @param numberOfDice, number of dice rolled by the player it should be between 1 to 3. 
	 * @return int array, with the values of dice.
	 * @throws NoSuchAlgorithmException , exception while creating the random number
	 */
	public Integer[] diceRoll(int numberOfDice) throws NoSuchAlgorithmException {
		Random random = SecureRandom.getInstanceStrong();
		Integer[] diceArray = new Integer[numberOfDice];
		for(int i = 0; i < numberOfDice;i++) {
			diceArray[i] = random.nextInt(5)+1;
		}
		Arrays.sort(diceArray, Collections.reverseOrder());
		return diceArray;
	}

}
