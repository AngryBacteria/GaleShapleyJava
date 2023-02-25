package gale.old.light;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Uses a lot less memory than the medium algorithm but runs a bit slower. Can computate a lot more before running out
 * of heap space
 */
public class GaleShapleyLight {

    public int groupSize;
    public static void main(String[] args) {

        for (int i = 0; i < 10; i++){
            GaleShapleyLight galeShapleyLight = new GaleShapleyLight(8000);
            galeShapleyLight.runRandom();
        }
    }

    public GaleShapleyLight(int groupSize) {
        this.groupSize = groupSize;
    }

    //Proposers are numbered as 0 to N-1. Proposed are numbered as N to 2N-1.
    public void findCouplesLightPrintWithInit(int[][] preferences) {

        //Queue of proposers and filling it with 0 to N (number of proposers)
        Queue<Integer> proposerQueue = new LinkedList<>();
        for (int i = 0; i < groupSize; i++) {
            proposerQueue.add(i);
        }

        //Stores partners of the proposed group. The value of proposedPartners[i] indicates the partner
        //assigned to proposed N+i. The value -1 indicates that the entity is unmatched
        //Initialize all proposers and proposed as unmatched (-1)
        int[] proposedPartners = new int[groupSize];
        Arrays.fill(proposedPartners, -1);

        //While there are free proposers in the queue
        while (!proposerQueue.isEmpty()) {
            //Pick the first free proposer (we could pick any)
            int proposer = proposerQueue.poll();
            //Go through the preferences of the current proposer in order
            for (int i = 0; i < groupSize; i++) {
                int proposed = preferences[proposer][i];
                System.out.println(proposer + " Is proposing to " + proposed);
                //Proposed is unmatched, we match them
                if (proposedPartners[proposed - groupSize] == -1) {
                    proposedPartners[proposed - groupSize] = proposer;
                    break;
                }

                //Proposed is already matched, we have to check if new match is preferred
                else {
                    int currentMatch = proposedPartners[proposed - groupSize];
                    System.out.println("\t\tProposed: " + proposed + " already matched to: " + currentMatch);
                    //If proposed prefers proposer over currentMatch, rematch them
                    if (prefersProposerOverMatch(preferences, proposed, proposer, currentMatch)) {
                        System.out.println("\t\tProposed prefers new match");
                        proposedPartners[proposed - groupSize] = proposer;
                        proposerQueue.add(currentMatch);
                        break;
                    }
                }
            }
        }
        //Printing
        System.out.println("proposed\t\tproposer");
        for (int i = 0; i < groupSize; i++) {
            System.out.print("");
            System.out.println(i + groupSize + "\t\t\t\t" +
                    proposedPartners[i]);
        }
    }

    public void findCouplesLightWithInit(int[][] preferences) {

        Queue<Integer> proposerQueue = new LinkedList<>();
        for (int i = 0; i < groupSize; i++) {
            proposerQueue.add(i);
        }

        int[] proposedPartners = new int[groupSize];
        Arrays.fill(proposedPartners, -1);

        while (!proposerQueue.isEmpty()) {

            int proposer = proposerQueue.poll();
            for (int i = 0; i < groupSize; i++) {

                int proposed = preferences[proposer][i];
                if (proposedPartners[proposed - groupSize] == -1) {
                    proposedPartners[proposed - groupSize] = proposer;
                    break;
                }
                else {
                    int currentMatch = proposedPartners[proposed - groupSize];
                    if (prefersProposerOverMatch(preferences, proposed, proposer, currentMatch)) {
                        proposedPartners[proposed - groupSize] = proposer;
                        proposerQueue.add(currentMatch);
                        break;
                    }
                }
            }
        }
    }

    public void findCouplesLightWithoutInit(int[][] preferences, Queue<Integer> proposerQueue, int[] proposedPartners) {

        //While there are free proposers in the queue
        while (!proposerQueue.isEmpty()) {
            //Pick the first free proposer (we could pick any)
            int proposer = proposerQueue.poll();

            //Go through the preferences of the current proposer in order
            for (int i = 0; i < groupSize; i++) {
                int proposed = preferences[proposer][i];
                //Proposed is unmatched, we match them
                if (proposedPartners[proposed - groupSize] == -1) {
                    proposedPartners[proposed - groupSize] = proposer;
                    break;
                }

                //Proposed is already matched, we have to check if new match is preferred
                else {
                    int currentMatch = proposedPartners[proposed - groupSize];

                    //If proposed prefers proposer over currentMatch, rematch them
                    if (prefersProposerOverMatch(preferences, proposed, proposer, currentMatch)) {
                        proposedPartners[proposed - groupSize] = proposer;
                        proposerQueue.add(currentMatch);
                        break;
                    }
                }
            }
        }
    }

    // This function returns true if proposed prefers proposer over currentMatch
    public boolean prefersProposerOverMatch(int[][] prefer, int proposed, int proposer, int currentMatch) {

        for (int i = 0; i < groupSize; i++) {

            if (prefer[proposed][i] == proposer)
                return true;

            // If currentMatch comes before proposer in list of proposed, then proposed prefers her current engagement
            if (prefer[proposed][i] == currentMatch)
                return false;
        }
        return false;
    }

    public void run2(){

        int[][] prefer2 = new int[][]{
                {4, 5, 6, 7},
                {5, 4, 6, 7},
                {4, 7, 6, 5},
                {7, 5, 6, 4},
                {2, 1, 0, 3},
                {3, 1, 2, 0},
                {0, 3, 2, 1},
                {0, 1, 2, 3}};

        int[][] prefer = new int[][]{
                {4, 5, 6, 7},
                {5, 4, 6, 7},
                {4, 7, 6, 5},
                {7, 5, 6, 4},
                {2, 1, 0, 3},
                {3, 1, 2, 0},
                {0, 3, 2, 1},
                {0, 1, 2, 3}};
        this.groupSize = 4;
        findCouplesLightPrintWithInit(prefer);
    }

    public void runRandom(){

        System.out.println("----------------------Generate Data START----------------------");
        //Generate groups
        int[] men = IntStream.range(0, groupSize).toArray();
        int[] women = IntStream.range(groupSize, 2*groupSize).toArray();

        //Fill it into preferences randomly
        int[][] preferences = new int[groupSize*2][groupSize];
        for (int row = 0; row < preferences.length; row++) {
            shuffleArray(men);
            shuffleArray(women);
            for (int col = 0; col < preferences[row].length; col++) {

                if (row < groupSize)
                    preferences[row][col] = women[col];
                else
                    preferences[row][col] = men[col];
            }
        }
        System.out.println("----------------------Generate Data END----------------------");
        long start = System.nanoTime();
        this.findCouplesLightWithInit(preferences);
        long finish = System.nanoTime();
        System.out.println(finish-start);
    }

    static void shuffleArray(int[] array) {

        Random rand = new Random();
        for (int i = 0; i < array.length; i++) {
            int randomIndexToSwap = rand.nextInt(array.length);
            int temp = array[randomIndexToSwap];
            array[randomIndexToSwap] = array[i];
            array[i] = temp;
        }
    }
}
