package me.ultradev.deathswap.api.util;

import java.util.Random;

public class NumberUtil {

    public static final Random RANDOM = new Random();

    /***
     * Gets a random number between two specified bounds.
     * @param min the minimum value
     * @param max the maximum value
     * @return a random number between the minimum and maximum values
     */
    public static int getRandomBetween(int min, int max) {
        return RANDOM.nextInt(max + 1 - min) + min;
    }

}
