package me.symi.owndrop.utils;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {

    public static Double getRandomDouble(double min, double max)
    {
        return ThreadLocalRandom.current().nextDouble(min, max + 1);
    }

    public static int getRandomInteger(int min, int max)
    {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

}
