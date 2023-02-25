package validation;

import gale.light.GaleShapleyLightV2;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
public class ValidationLightV2Test {

    /**
     * 2d Array representing preferences. Nothing particularly special about it
     */
    int[][] preferNormal1 = new int[][]{
            {4, 5, 6, 7}, //proposer 0
            {5, 4, 6, 7}, //proposer 1
            {4, 7, 6, 5}, //proposer 2
            {7, 5, 6, 4}, //proposer 3
            {2, 1, 0, 3}, //proposed 4
            {3, 1, 2, 0}, //proposed 5
            {0, 3, 2, 1}, //proposed 6
            {0, 2, 3, 1}};//proposed 7

    /**
     * 2d Array representing preferences. One of the worst cases for the Gale Shapley Algorithm. After first iteration
     * all matches are unstable (every proposed is with the least favorite proposer)
     */
    int[][] preferWorst1 = new int[][]{
            {5, 6, 7, 8, 9},
            {6, 7, 8, 5, 9},
            {7, 8, 5, 6, 9},
            {8, 5, 6, 7, 9},
            {5, 6, 7, 8, 9},

            {4, 0, 1, 2, 3},
            {3, 4, 0, 1, 2},
            {2, 3, 4, 0, 1},
            {1, 2, 3, 4, 0},
            {0, 1, 2, 3, 4}};

    /**
     * 2d Array representing preferences. One of the best case scenarios for the Gale Shapley Algorithm. After one
     * iteration every match is already stable
     */
    int[][] preferBest1 = new int[][]{
            {5, 6, 7, 8, 9},
            {6, 7, 8, 9, 5},
            {7, 8, 9, 5, 6},
            {8, 9, 5, 6, 7},
            {9, 5, 6, 7, 8},

            {4, 0, 1, 2, 3},
            {3, 4, 0, 1, 2},
            {2, 3, 4, 0, 1},
            {1, 2, 3, 4, 0},
            {0, 1, 2, 3, 4}};

    /**
     * 2d Array representing preferences. Edge case for testing purposes. Every proposer and proposed has the same
     * preference
     */
    int[][] edgeCase1 = new int[][]{
            {5, 6, 7, 8, 9},
            {5, 6, 7, 8, 9},
            {5, 6, 7, 8, 9},
            {5, 6, 7, 8, 9},
            {5, 6, 7, 8, 9},

            {0, 1, 2, 3, 4},
            {0, 1, 2, 3, 4},
            {0, 1, 2, 3, 4},
            {0, 1, 2, 3, 4},
            {0, 1, 2, 3, 4}};

    /**
     * Checks if the proposedPrefersProposerOverMatch() method works as intended. It checks all the possibilities of 3 Proposed
     * with a groupSize of 4
     */
    @Test
    void ProposedPrefersProposerOverMatchTest(){

        GaleShapleyLightV2 galeShapleyLightV2 = new GaleShapleyLightV2(4);
        int[][] prefer = preferNormal1;

        //Proposed 4 with match 0
        assertFalse(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 4, 0, 0));
        assertTrue(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 4, 1, 0));
        assertTrue(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 4, 2, 0));
        assertFalse(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 4, 3, 0));
        //Proposed 4 with match 1
        assertFalse(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 4, 0, 1));
        assertFalse(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 4, 1, 1));
        assertTrue(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 4, 2, 1));
        assertFalse(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 4, 3, 1));
        //Proposed 4 with match 2
        assertFalse(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 4, 0, 2));
        assertFalse(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 4, 1, 2));
        assertFalse(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 4, 2, 2));
        assertFalse(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 4, 3, 2));
        //Proposed 4 with match 3
        assertTrue(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 4, 0, 3));
        assertTrue(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 4, 1, 3));
        assertTrue(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 4, 2, 3));
        assertFalse(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 4, 3, 3));


        //Proposed 6 with match 0
        assertFalse(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 6, 0, 0));
        assertFalse(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 6, 1, 0));
        assertFalse(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 6, 2, 0));
        assertFalse(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 6, 3, 0));
        //Proposed 6 with match 1
        assertTrue(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 6, 0, 1));
        assertFalse(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 6, 1, 1));
        assertTrue(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 6, 2, 1));
        assertTrue(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 6, 3, 1));
        //Proposed 6 with match 2
        assertTrue(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 6, 0, 2));
        assertFalse(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 6, 1, 2));
        assertFalse(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 6, 2, 2));
        assertTrue(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 6, 3, 2));
        //Proposed 6 with match 3 (rank1)
        assertTrue(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 6, 0, 3));
        assertFalse(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 6, 1, 3));
        assertFalse(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 6, 2, 3));
        assertFalse(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 6, 3, 3));


        //Proposed 7 with match 0
        assertFalse(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 7, 0, 0));
        assertFalse(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 7, 1, 0));
        assertFalse(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 7, 2, 0));
        assertFalse(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 7, 3, 0));
        //Proposed 7 with match 1
        assertTrue(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 7, 0, 1));
        assertFalse(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 7, 1, 1));
        assertFalse(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 7, 2, 1));
        assertTrue(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 7, 3, 1));
        //Proposed 7 with match 2
        assertTrue(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 7, 0, 2));
        assertTrue(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 7, 1, 2));
        assertFalse(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 7, 2, 2));
        assertTrue(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 7, 3, 2));
        //Proposed 7 with match 3
        assertTrue(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 7, 0, 3));
        assertFalse(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 7, 1, 3));
        assertFalse(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 7, 2, 3));
        assertFalse(galeShapleyLightV2.proposedPrefersProposerOverMatch(prefer, 7, 3, 3));
    }

    /**
     * Checks if the proposerPrefersProposedOverMatch() function works as intended. It checks all possibilites of 1
     * Proposer with a groupSize of 4
     */
    @Test
    void proposerPrefersProposedOverMatchTest(){

        GaleShapleyLightV2 galeShapleyLightV2 = new GaleShapleyLightV2(4);
        int[][] prefer = preferNormal1;

        //proposer 1 with match 4
        assertFalse(galeShapleyLightV2.proposerPrefersProposedOverMatch(prefer, 4, 1, 4));
        assertTrue(galeShapleyLightV2.proposerPrefersProposedOverMatch(prefer, 5, 1, 4));
        assertFalse(galeShapleyLightV2.proposerPrefersProposedOverMatch(prefer, 6, 1, 4));
        assertFalse(galeShapleyLightV2.proposerPrefersProposedOverMatch(prefer, 7, 1, 4));
        //proposer 1 with match 5
        assertFalse(galeShapleyLightV2.proposerPrefersProposedOverMatch(prefer, 4, 1, 5));
        assertFalse(galeShapleyLightV2.proposerPrefersProposedOverMatch(prefer, 5, 1, 5));
        assertFalse(galeShapleyLightV2.proposerPrefersProposedOverMatch(prefer, 6, 1, 5));
        assertFalse(galeShapleyLightV2.proposerPrefersProposedOverMatch(prefer, 7, 1, 5));
        //proposer 1 with match 6
        assertTrue(galeShapleyLightV2.proposerPrefersProposedOverMatch(prefer, 4, 1, 6));
        assertTrue(galeShapleyLightV2.proposerPrefersProposedOverMatch(prefer, 5, 1, 6));
        assertFalse(galeShapleyLightV2.proposerPrefersProposedOverMatch(prefer, 6, 1, 6));
        assertFalse(galeShapleyLightV2.proposerPrefersProposedOverMatch(prefer, 7, 1, 6));
        //proposer 1 with match 7
        assertTrue(galeShapleyLightV2.proposerPrefersProposedOverMatch(prefer, 4, 1, 7));
        assertTrue(galeShapleyLightV2.proposerPrefersProposedOverMatch(prefer, 5, 1, 7));
        assertTrue(galeShapleyLightV2.proposerPrefersProposedOverMatch(prefer, 6, 1, 7));
        assertFalse(galeShapleyLightV2.proposerPrefersProposedOverMatch(prefer, 7, 1, 7));
    }

    /**
     * Checks if the simple Input Validation of the findCouplpes() method works properly
     */
    @Test
    void inputValidationTest(){

        GaleShapleyLightV2 galeShapleyLightV2 = new GaleShapleyLightV2(4);
        int[][] prefer = preferNormal1;

        assertDoesNotThrow(() -> {
            galeShapleyLightV2.findCouples(prefer);
        });

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            galeShapleyLightV2.groupSize = 8;
            galeShapleyLightV2.findCouples(prefer);
        });
        assertEquals("Group size does not match preference array size", exception.getMessage());

        int[][] prefer2 = new int[][]{
                {4, 5, 6, 7}, //proposer 0
                {5, 4, 6, 7}, //proposer 1
                {7, 5, 6, 4}, //proposer 3
                {2, 1, 0, 3}, //proposed 4
                {3, 1, 2, 0}, //proposed 5
                {0, 3, 2, 1}};//proposed 7

        exception = assertThrows(IllegalStateException.class, () -> {
            galeShapleyLightV2.groupSize = 4;
            galeShapleyLightV2.findCouples(prefer2);
        });
        assertEquals("Group size does not match preference array size", exception.getMessage());
    }

    /**
     * Checks that the output of the findCouples() function has no proposer twice in the list of matchings. It checks
     * preferNormal1, edgeCase1, preferWorst1, preferBest1 and a couple of random runs
     */
    @Test
    void noProposerTwiceTest(){

        //1 Non Random run
        int[][] prefer = preferNormal1;

        GaleShapleyLightV2 galeShapleyLightV2 = new GaleShapleyLightV2(4);
        int[] proposedPartners = galeShapleyLightV2.findCouples(prefer);
        assertTrue(this.noDuplicates(proposedPartners));

        galeShapleyLightV2.groupSize = 5;
        //edgecase
        proposedPartners = galeShapleyLightV2.findCouples(edgeCase1);
        assertTrue(this.noDuplicates(proposedPartners));

        //worstcase
        proposedPartners = galeShapleyLightV2.findCouples(preferWorst1);
        assertTrue(this.noDuplicates(proposedPartners));

        //bestcase
        proposedPartners = galeShapleyLightV2.findCouples(preferBest1);
        assertTrue(this.noDuplicates(proposedPartners));

        //10 Random runs with groupSize of 100
        galeShapleyLightV2.groupSize = 100;
        for (int i = 0; i < 10; i++){
            prefer = GaleShapleyLightV2.generateRandomData(galeShapleyLightV2.groupSize);
            proposedPartners = galeShapleyLightV2.findCouples(prefer);
            assertTrue(this.noDuplicates(proposedPartners));
        }

        //10 Random runs with groupSize of 500
        galeShapleyLightV2.groupSize = 500;
        for (int i = 0; i < 10; i++){
            prefer = GaleShapleyLightV2.generateRandomData(galeShapleyLightV2.groupSize);
            proposedPartners = galeShapleyLightV2.findCouples(prefer);
            assertTrue(this.noDuplicates(proposedPartners));
        }

        assertFalse(this.noDuplicates(new int[]{1,0,3,1}));
        assertTrue(this.noDuplicates(new int[]{66, 77, 88, 99}));
        assertFalse(this.noDuplicates(new int[]{66, 66, 88, 99}));
        assertFalse(this.noDuplicates(new int[]{0, 0, 0, 0}));
    }

    /**
     * Helper method for the noProposerTwiceTest. Checks for duplicates in a given array
     * @param array Input Array that should be checked for duplicates
     * @return boolean that indicates if there were duplicates in the array or not
     */
    public boolean noDuplicates(int[] array){

        Set<Integer> seenNumbers = new HashSet<>();
        for (int num : array) {
            if (seenNumbers.contains(num)) {
                return false;
            }
            seenNumbers.add(num);
        }
        return true;
    }

    /**
     * Tests if the getProposersMatch() function works as intended
     */
    @Test
    void getProposersMatchTest(){

        GaleShapleyLightV2 galeShapleyLightV2 = new GaleShapleyLightV2(4);
        int[] proposedPartners = new int[]{0,3,2,1};

        assertEquals(4, galeShapleyLightV2.getProposersMatch(0, proposedPartners));
        assertEquals(7, galeShapleyLightV2.getProposersMatch(1, proposedPartners));
        assertEquals(6, galeShapleyLightV2.getProposersMatch(2, proposedPartners));
        assertEquals(5, galeShapleyLightV2.getProposersMatch(3, proposedPartners));
    }

    /**
     * Checks if the generateRandomData() function works as intended. The output is checked for the right groupSize and
     * that the output is random. This could lead to an error because prefer1 and prefer2 could lead to the same array.
     * This is unlikely but possible
     */
    @Test
    void generateRandomDataTest(){

        //Testing if the function returns 2 different arrays with the right groupSize
        int[][] prefer = GaleShapleyLightV2.generateRandomData(100);
        assertEquals(200, prefer.length);
        assertEquals(100, prefer[0].length);

        int[][] prefer2 = GaleShapleyLightV2.generateRandomData(100);
        assertEquals(200, prefer2.length);
        assertEquals(100, prefer2[0].length);

        assertNotEquals(prefer, prefer2);
        assertFalse(Arrays.deepEquals(prefer, prefer2));

        //Testing the input validation
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            GaleShapleyLightV2.generateRandomData(-1);
        });
        assertEquals("Group size cannot be negative", exception.getMessage());

        exception = assertThrows(IllegalStateException.class, () -> {
            GaleShapleyLightV2.generateRandomData(0);
        });
        assertEquals("Group size cannot be negative", exception.getMessage());

        exception = assertThrows(IllegalStateException.class, () -> {
            GaleShapleyLightV2.generateRandomData(-329746);
        });
        assertEquals("Group size cannot be negative", exception.getMessage());
    }

    /**
     * Checks if the convertV1PreferenceToV2() function works as intended
     */
    @Test
    void convertV1PreferenceToV2Test(){

        int[] input = new int[]{0, 3, 2, 1};
        assertArrayEquals(new int[]{0, 3, 2, 1},
                GaleShapleyLightV2.convertV1PreferenceToV2(input));

        input = new int[]{0, 2, 3, 1};
        assertArrayEquals(new int[]{0, 3, 1, 2},
                GaleShapleyLightV2.convertV1PreferenceToV2(input));

        input = new int[]{5, 2, 0, 1, 7, 4, 3, 6};
        assertArrayEquals(new int[]{2, 3, 1, 6, 5, 0, 7, 4},
                GaleShapleyLightV2.convertV1PreferenceToV2(input));
    }

    /**
     * Checks if the function allMatchesStable() works as intended. preferNormal1, edgeCase1, preferWorst1, preferBest1
     * and random data is checked
     */
    @Test
    void allMatchesStableTest(){

        //normal non random run
        GaleShapleyLightV2 galeShapleyLightV2 = new GaleShapleyLightV2(4);
        int[][] prefer = preferNormal1;
        int[] proposedPartners = galeShapleyLightV2.findCouples(prefer);
        assertTrue(galeShapleyLightV2.allMatchesStable(prefer, proposedPartners));

        //changing that proposer 3 is matched with proposed 6 --> generates unstable match
        proposedPartners = new int[]{2, 1, 3, 0};
        assertFalse(galeShapleyLightV2.allMatchesStable(prefer, proposedPartners));

        galeShapleyLightV2.groupSize = 5;
        //edgecase
        proposedPartners = galeShapleyLightV2.findCouples(edgeCase1);
        assertTrue(galeShapleyLightV2.allMatchesStable(edgeCase1, proposedPartners));

        //worstcase
        proposedPartners = galeShapleyLightV2.findCouples(preferWorst1);
        assertTrue(galeShapleyLightV2.allMatchesStable(preferWorst1, proposedPartners));

        //bestcase
        proposedPartners = galeShapleyLightV2.findCouples(preferBest1);
        assertTrue(galeShapleyLightV2.allMatchesStable(preferBest1, proposedPartners));

        //random
        int iterations = 100;
        int groupSize = 500;
        galeShapleyLightV2.groupSize = groupSize;
        for (int i = 0; i < iterations; i++){

            int[][] preferRandom = GaleShapleyLightV2.generateRandomData(groupSize);
            int[] proposerPartnersRandom = galeShapleyLightV2.findCouples(preferRandom);
            assertTrue(galeShapleyLightV2.allMatchesStable(preferRandom, proposerPartnersRandom));
        }
    }
}
