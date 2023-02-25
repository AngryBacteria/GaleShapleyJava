package perfomance;

import gale.light.GaleShapleyLightV2;
import gale.old.light.GaleShapleyLight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class which is used to measure the performance of the various Gale Shapley Algorithm implementations
 */
public class PerformanceTesting {

    /**
     * Number of iterations that the performance test will go through
     */
    private final int iterations;
    /**
     * Size of the Proposer and Proposed Groups the Algorithm will use
     */
    private final int groupSize;
    /**
     * System path where the csv-Files will be saved to
     */
    private final String savePath;

    /**
     * Main method used to test the performance of the various Gale Shapley Algorithm implementations
     * @param args args which are optionally used to set the instance variables for the GaleShapleyV2 Class. If none are
     * specified it will use the defaults. If 3 are specified it will use the args. args[0] is iterations, args[1] is
     * the groupSize and args[2] is the savePath
     */
    public static void main(String[] args) {

        //setup
        int iterations = 1_000;
        int groupSize = 6_000;
        String savePath = "D:\\Programming\\Java\\2021\\topic10\\csv\\";
        if (args.length == 3){
            iterations = Integer.parseInt(args[0]);
            groupSize = Integer.parseInt(args[1]);
            savePath = args[2];
        }
        //setup
        PerformanceTesting performanceTesting = new PerformanceTesting(iterations, groupSize, savePath);

        //performance testing
        performanceTesting.galeShapleyLightV2Test();
        performanceTesting.galeShapleyLightTest();
    }

    /**
     * Constructor for the PerfomanceTesting Class
     * @param iterations Number of iterations that the performance test will go through
     * @param groupSize Size of the Proposer and Proposed Groups the Algorithm will use
     * @param savePath System path where the csv-Files will be saved to
     */
    public PerformanceTesting(int iterations, int groupSize, String savePath) {
        this.iterations = iterations;
        this.groupSize = groupSize;
        this.savePath = savePath;
    }

    /**
     * Test for the V2 Implementation. Measures the time used in nanoseconds. Generates the data with the
     * generateRandomData() function from the GaleShapleyLightV2 class
     */
    public void galeShapleyLightV2Test(){

        List<Long> results = new ArrayList<>();
        GaleShapleyLightV2 galeShapleyLightV2 = new GaleShapleyLightV2(this.groupSize);

        for (int i = 0; i < this.iterations; i++){
            int[][] preferences = GaleShapleyLightV2.generateRandomData(this.groupSize);
            long start = System.nanoTime();
            galeShapleyLightV2.findCouples(preferences);
            long end = System.nanoTime();
            results.add(end-start);
            System.out.printf("%d. run done [groupSize=%d]\n", i, this.groupSize);
        }
        writeCSV(results, String.format(this.savePath + "galeShapleyV2[groupSize=%d].csv", this.groupSize));
    }

    /**
     * Test for the V1 Implementation. Measures the time used in nanoseconds. Generates the data with the
     * generateRandomData() function from the GaleShapleyLight class
     */
    public void galeShapleyLightTest(){

        List<Long> results = new ArrayList<>();
        GaleShapleyLight galeShapleyLight = new GaleShapleyLight(this.groupSize);

        for (int i = 0; i < this.iterations; i++){
            int[][] preferences = GaleShapleyLightV2.generateRandomData(this.groupSize);
            long start = System.nanoTime();
            galeShapleyLight.findCouplesLightWithInit(preferences);
            long end = System.nanoTime();
            results.add(end-start);
            System.out.printf("%d. run done [groupSize=%d]\n", i, this.groupSize);
        }
        writeCSV(results, String.format(this.savePath + "galeShapleyV1[groupSize=%d].csv", this.groupSize));
    }

    /**
     * Helper method that writes the results into a csv file and saves it into the systemPath
     * @param results List representing the nanoseconds values of the performance Testing
     * @param fileName File Name that should be used to save the results
     */
    public void writeCSV(List<Long> results, String fileName){

        System.out.println("Collecting csv");
        String csv = (results.stream().map(String::valueOf)
                .collect(Collectors.joining(",\n")));

        System.out.println("Writing csv");
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
            writer.write(csv);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Done");
    }
}
