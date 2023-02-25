package gale.light;

import java.util.Arrays;

/**
 * Demo class to use in presenation
 */
public class Demo {

    public static void main(String[] args) {

        GaleShapleyLightV2 galeShapleyLightV2 = new GaleShapleyLightV2(3);

        int[][] preferences = new int[][]{
                {4, 3, 5}, //Misty 0
                {4, 5, 3}, //Ash 1
                {3, 5, 4}, //Rocko 2

                {1, 0, 2}, //Evoli 3
                {2, 1, 0}, //Pikachu 4
                {0, 2, 1}};//Schiggy 5

        int[] pairs = galeShapleyLightV2.findCouplesPrint(preferences);
        GaleShapleyLightV2.printMatches(pairs);
    }

}
