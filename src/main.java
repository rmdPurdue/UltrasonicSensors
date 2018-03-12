import java.util.ArrayList;
import java.util.Iterator;

public class main {

    public static void main(String[] args) {

        // Define five ultrasonic receivers (with current temp and distance from emitter)
        UltrasonicReceiver r1 = new UltrasonicReceiver(25,-.2);
        UltrasonicReceiver r2 = new UltrasonicReceiver(25, -.1);
        UltrasonicReceiver r3 = new UltrasonicReceiver(25, 0);
        UltrasonicReceiver r4 = new UltrasonicReceiver(25, .1);
        UltrasonicReceiver r5 = new UltrasonicReceiver(25, 1);

        // Clear all TOF return arraylists
        r1.clearTofReturns();
        r2.clearTofReturns();
        r3.clearTofReturns();
        r4.clearTofReturns();
        r5.clearTofReturns();

        // Set dummy TOF returns for all five receivers
        r1.setTofReturn(3210.1);
        r1.setTofReturn(3209.2);

        r2.setTofReturn(3122.3);
        r2.setTofReturn(3029.7);
        r2.setTofReturn(2864.6);

        r3.setTofReturn(3059.1);
        r3.setTofReturn(2886.0);

        r4.setTofReturn(3022.5);
        r4.setTofReturn(2789.6);
        r4.setTofReturn(2963.0);

        r5.setTofReturn(3012.9);
        r5.setTofReturn(2751.2);
        r5.setTofReturn(3087.7);

        // Calculate lengths to objects based on TOF returns
        r1.calculateLengths();
        r2.calculateLengths();
        r3.calculateLengths();
        r4.calculateLengths();
        r5.calculateLengths();

        // Create an Arraylist of all possible 5-receiver TOF groupings
        double[] tofGroup = new double[9];
        ArrayList<double[]> tofGroups = new ArrayList<>();
        Iterator iteratorR1 = r1.getLengths().iterator();
        int i = 0;
        while(iteratorR1.hasNext()) {
            tofGroup[0] = (double)iteratorR1.next();
            Iterator iteratorR2 = r2.getLengths().iterator();
            while(iteratorR2.hasNext()) {
                tofGroup[1] = (double)iteratorR2.next();
                Iterator iteratorR3 = r3.getLengths().iterator();
                while(iteratorR3.hasNext()) {
                    tofGroup[2] = (double)iteratorR3.next();
                    Iterator iteratorR4 = r4.getLengths().iterator();
                    while(iteratorR4.hasNext()) {
                        tofGroup[3] = (double)iteratorR4.next();
                        Iterator iteratorR5 = r5.getLengths().iterator();
                        while(iteratorR5.hasNext()) {
                            i++;
                            tofGroup[4] = (double)iteratorR5.next();
                            //System.out.println(tofGroup[0] + " : " + tofGroup[1] + " : " + tofGroup[2] + " : " + tofGroup[3] + " : " + tofGroup[4]);
                            tofGroup[5] = getAverage(
                                    getLength(r1, r2, tofGroup[0], tofGroup[1]),
                                    getLength(r2, r3, tofGroup[1], tofGroup[2]),
                                    getLength(r3, r4, tofGroup[2], tofGroup[3]),
                                    getLength(r4, r5, tofGroup[3], tofGroup[4])
                            );
                            System.out.println("Avg group " + i + ": " + tofGroup[5]);
                            tofGroup[6] = getStandardDeviation(
                                    getLength(r1, r2, tofGroup[0], tofGroup[1]),
                                    getLength(r2, r3, tofGroup[1], tofGroup[2]),
                                    getLength(r3, r4, tofGroup[2], tofGroup[3]),
                                    getLength(r4, r5, tofGroup[3], tofGroup[4]),
                                    tofGroup[5]
                            );
                            System.out.println("Standard Deviation: " + tofGroup[6]);
                            System.out.println();
                            tofGroups.add(tofGroup);
                        }
                    }
                }
            }
        }

        System.out.println(tofGroups.get(106)[5]);

/*
        Iterator iterator1 = tofGroups.iterator();
        while(iterator1.hasNext()) {
            double[] tofGroup1 = (double[])iterator1.next();
            System.out.println(tofGroup1[0] + " : " + tofGroup1[1] + " : " + tofGroup1[2] + " : " +
                    tofGroup1[3] + " : " + tofGroup1[4]);
        }

        // Calculate the average distance (based on two-pair calculations) for each TOF grouping (assume edge-type)
        int i = 0;
        Iterator iterator = tofGroups.iterator();
        while(iterator.hasNext()) {
            i++;
            double[] group = (double[])iterator.next();
            System.out.println(group[0] + " : " + group[1] + " : " + group[2] + " : " + group[3] + " : " + group[4]);
            System.out.println("Avg group " + i + ": " + getAverage(
                    getLength(r1, r2, group[0], group[1]),
                    getLength(r2, r3, group[1], group[2]),
                    getLength(r3, r4, group[2], group[3]),
                    getLength(r4, r5, group[3], group[4])
            ));
        }
*/

        // Calculate the average polar position (based on two-pair calculations) for each TOF grouping (assume edge-type)

        // Calculate the standard deviation for each TOF grouping (assume edge-type)

        // Determine the grouping with the smallest standard deviation (assume edge-type)

        // Calculate the average distance (based on two-pair calculations for each TOG grouping (assume face-type)

        // Calculate the average polar position (based on two-pair calculations) for each TOF grouping (assume face-type)

        // Calculate the standard deviation for each TOF grouping (assume face-type)

        // Determine the grouping with the smallest standard deviation (assume face-type)


/*
        Calculations distance = new Calculations();
        System.out.println("Object 1:");
        System.out.println("l = " + distance.getDistance(25,3210.1,3122.3,-.2,-.1));
        System.out.println("l = " + distance.getDistance(25,3122.3,3059.1,-.1,0));
        System.out.println("l = " + distance.getDistance(25,3059.1,3022.5,0,.1));
        System.out.println("l = " + distance.getDistance(25,3022.5,3012.9,.1,.2));

        System.out.println();
        System.out.print("Average l = " + distance.getAverage(
                distance.getDistance(25,3210.1,3122.3,-.2,-.1),
                distance.getDistance(25,3122.3,3059.1,-.1,0),
                distance.getDistance(25,3059.1,3022.5,0,.1),
                distance.getDistance(25,3022.5,3012.9,.1,.2)));
        System.out.println();
        System.out.println("Standard deviation = " + distance.getStandardDeviation(
                distance.getDistance(25,3210.1,3122.3,-.2,-.1),
                distance.getDistance(25,3122.3,3059.1,-.1,0),
                distance.getDistance(25,3059.1,3022.5,0,.1),
                distance.getDistance(25,3022.5,3012.9,.1,.2),
                distance.getAverage(
                    distance.getDistance(25,3210.1,3122.3,-.2,-.1),
                    distance.getDistance(25,3122.3,3059.1,-.1,0),
                    distance.getDistance(25,3059.1,3022.5,0,.1),
                    distance.getDistance(25,3022.5,3012.9,.1,.2))
                ));
        System.out.println();

        System.out.println("theta = " + distance.getAngle(25,3210.1,3122.3,-.2,-.1));
        System.out.println("theta = " + distance.getAngle(25,3122.3,3059.1,-.1,0));
        System.out.println("theta = " + distance.getAngle(25,3059.1,3022.5,0,.1));
        System.out.println("theta = " + distance.getAngle(25,3022.5,3012.9,.1,.2));

        System.out.println();
        System.out.print("Average l = " + distance.getAverage(
                distance.getAngle(25,3210.1,3122.3,-.2,-.1),
                distance.getAngle(25,3122.3,3059.1,-.1,0),
                distance.getAngle(25,3059.1,3022.5,0,.1),
                distance.getAngle(25,3022.5,3012.9,.1,.2)));
        System.out.println();
        System.out.println("Standard deviation = " + distance.getStandardDeviation(
                distance.getAngle(25,3210.1,3122.3,-.2,-.1),
                distance.getAngle(25,3122.3,3059.1,-.1,0),
                distance.getAngle(25,3059.1,3022.5,0,.1),
                distance.getAngle(25,3022.5,3012.9,.1,.2),
                distance.getAverage(
                        distance.getAngle(25,3210.1,3122.3,-.2,-.1),
                        distance.getAngle(25,3122.3,3059.1,-.1,0),
                        distance.getAngle(25,3059.1,3022.5,0,.1),
                        distance.getAngle(25,3022.5,3012.9,.1,.2))
        ));
        System.out.println();

        System.out.println("Object 2:");
        System.out.println("l = " + distance.getDistance(25,3209.2,3029.7,-.2,-.1));
        System.out.println("l = " + distance.getDistance(25,3029.7,2886.0,-.1,0));
        System.out.println("l = " + distance.getDistance(25,2886.0,2789.6,0,.1));
        System.out.println("l = " + distance.getDistance(25,2789.6,2751.2,.1,.2));

        System.out.println("theta = " + distance.getAngle(25,3209.2,3209.7,-.2,-.1));
        System.out.println("theta = " + distance.getAngle(25,3029.7,2886.0,-.1,0));
        System.out.println("theta = " + distance.getAngle(25,2886.0,2789.6,0,.1));
        System.out.println("theta = " + distance.getAngle(25,2789.6,2751.2,.1,.2));
        System.out.println();

        System.out.println("Object 3:");
        System.out.println("l = " + distance.getDistance(25,2901.0,2864.6,-.2,-.1));
        System.out.println("l = " + distance.getDistance(25,2864.6,2886.0,-.1,0));
        System.out.println("l = " + distance.getDistance(25,2886.0,2963.0,0,.1));
        System.out.println("l = " + distance.getDistance(25,2963.0,3087.7,.1,.2));

        System.out.println("theta = " + distance.getAngle(25,2901.0,2864.6,-.2,-.1));
        System.out.println("theta = " + distance.getAngle(25,2864.6,2886.0,-.1,0));
        System.out.println("theta = " + distance.getAngle(25,2886.0,2963.0,0,.1));
        System.out.println("theta = " + distance.getAngle(25,2963.0,3087.7,.1,.2));
        System.out.println();
*/
    }

    private static double getLength(UltrasonicReceiver r1, UltrasonicReceiver r2, double length1, double length2) {
        double length;
        double d1squared = Math.pow(r1.getDistance(),2);
        double d2squared = Math.pow(r2.getDistance(),2);
        double l1squared = Math.pow(length1,2);
        double l2squared = Math.pow(length2,2);
        double numerator = (r2.getDistance() * (l1squared - d1squared))-(r1.getDistance() * (l2squared - d2squared));
        double denominator = (r2.getDistance() * length1) - (r1.getDistance() * length2);
        length = 0.5 * numerator / denominator;
        return length;
    }

    private static double getAverage(double d1, double d2, double d3, double d4) {
        return (d1 + d2 + d3 + d4) / 4;
    }

    private static double getStandardDeviation(double d1, double d2, double d3, double d4, double average) {
        double sum = Math.pow((d1 - average), 2) + Math.pow((d2 - average), 2) + Math.pow((d3 - average), 2) + Math.pow((d4 - average), 2);
        double sigma = Math.sqrt(sum / 3);
        return sigma;
    }

}
