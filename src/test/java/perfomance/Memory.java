package perfomance;

import gale.old.medium.Proposed;
import gale.old.medium.Proposer;
import org.openjdk.jol.info.ClassLayout;
import java.util.List;


/**
 * Helper Class that was used to troubleshoot initial heap space problems with the LightV1 and Medium/Heavy Algorithms
 */
public class Memory {

    /**
     * Various Memory Size testing with the Medium Algorithm Version. Using the org.openjdk.jol.info.ClassLayout classes
     * @param size Size of the individual groups
     */
    public static void memorySizeMedium(int size){

        Proposed proposed = new Proposed("proposed1");
        Proposer proposer = new Proposer("proposer1");
        proposed.addAllInterests(List.of(proposer));
        proposer.addAllInterests(List.of(proposed));

        System.out.printf("----------------------Memory Usage Medium [Size = %d]----------------------%n", size);

        //Proposer size
        long proposerSize = ClassLayout.parseInstance(proposer).instanceSize()
                + ClassLayout.parseInstance(proposer.identifier).instanceSize()
                + ClassLayout.parseInstance(proposer.identifier.getBytes()).instanceSize()
                + ClassLayout.parseInstance(proposer.interests).instanceSize();
        System.out.println("Proposer size without references: " + proposerSize);
        System.out.println("Proposer size with references: " + (proposerSize + 4L * size));

        //Proposed size
        long proposedSize = ClassLayout.parseInstance(proposed).instanceSize()
                + ClassLayout.parseInstance(proposed.identifier).instanceSize()
                + ClassLayout.parseInstance(proposed.identifier.getBytes()).instanceSize()
                + ClassLayout.parseInstance(proposed.interests).instanceSize();
        System.out.println("Proposed size without references: " + proposedSize);
        System.out.println("Proposed size with references: " + (proposedSize + 4L * size));

        System.out.printf("----------------------Memory Usage Medium [Size = %d]----------------------%n", size);
    }

    /**
     * Various Memory Size testing with the Light Algorithm Version
     * @param size Size of the individual groups
     */
    public static void memorySizeLight(int size){

        System.out.printf("----------------------Memory Usage Light [Size = %d]----------------------%n", size);
        int[][] intArray = new int[size*2][size];
        long intArraySize = (long) size * size * 4 * 2;
        System.out.println("Size of preferences Array: " + intArraySize);
        System.out.printf("----------------------Memory Usage Light [Size = %d]----------------------%n", size);
    }
}
