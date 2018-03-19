import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class main {

    public static void main(String[] args) {

        // Define five ultrasonic receivers (with current temp and distance from emitter)
        UltrasonicReceiver r1 = new UltrasonicReceiver(25,-.2);
        UltrasonicReceiver r2 = new UltrasonicReceiver(25, -.1);
        UltrasonicReceiver r3 = new UltrasonicReceiver(25, 0);
        UltrasonicReceiver r4 = new UltrasonicReceiver(25, .1);
        UltrasonicReceiver r5 = new UltrasonicReceiver(25, .2);

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
        ArrayList<double[]> tofGroups = new ArrayList<>();
        tofGroups.clear();
        Iterator iteratorR1 = r1.getLengths().iterator();
        int i = 0;
        while(iteratorR1.hasNext()) {
            double one = (double)iteratorR1.next();
            Iterator iteratorR2 = r2.getLengths().iterator();
            while(iteratorR2.hasNext()) {
                double two = (double)iteratorR2.next();
                Iterator iteratorR3 = r3.getLengths().iterator();
                while(iteratorR3.hasNext()) {
                    double three = (double)iteratorR3.next();
                    Iterator iteratorR4 = r4.getLengths().iterator();
                    while(iteratorR4.hasNext()) {
                        double four = (double)iteratorR4.next();
                        Iterator iteratorR5 = r5.getLengths().iterator();
                        while(iteratorR5.hasNext()) {
                            System.out.println(i + " ---------------------------------");
                            double[] tofGroup = new double[10];
                            tofGroup[0] = one;
                            tofGroup[1] = two;
                            tofGroup[2] = three;
                            tofGroup[3] = four;
                            tofGroup[4] = (double)iteratorR5.next();

                            // Calculate the average distance (based on two-pair calculations) for each TOF grouping (assume edge-type)
                            tofGroup[5] = (getAverage(
                                    getLength(r1, r2, tofGroup[0], tofGroup[1]),
                                    getLength(r2, r3, tofGroup[1], tofGroup[2]),
                                    getLength(r3, r4, tofGroup[2], tofGroup[3]),
                                    getLength(r4, r5, tofGroup[3], tofGroup[4])
                            ));

                            // Calculate the standard deviation of distance for each TOF grouping (assume edge-type)
                            tofGroup[6] = (getStandardDeviation(
                                    getLength(r1, r2, tofGroup[0], tofGroup[1]),
                                    getLength(r2, r3, tofGroup[1], tofGroup[2]),
                                    getLength(r3, r4, tofGroup[2], tofGroup[3]),
                                    getLength(r4, r5, tofGroup[3], tofGroup[4]),
                                    tofGroup[5]
                            ));

                            // I don't think the calculations below are working. I'm getting a lot of NaN results.

                            // Calculate the average polar position (based on two-pair calculations) for each TOF grouping (assume edge-type)
                            tofGroup[7] = (getAverage(
                                    getAngle(r1, r2, tofGroup[0], tofGroup[1]),
                                    getAngle(r2, r3, tofGroup[1], tofGroup[2]),
                                    getAngle(r3, r4, tofGroup[2], tofGroup[3]),
                                    getAngle(r4, r5, tofGroup[3], tofGroup[4])
                            ));

                            /*
                            System.out.println("Angle 1: " + getAngle(r1, r2, tofGroup[0], tofGroup[1]) + " : " +
                                    "Angle 2: " + getAngle(r2, r3, tofGroup[1], tofGroup[2]) + " : " +
                                    "Angle 3: " + getAngle(r3, r4, tofGroup[2], tofGroup[3]) + " : " +
                                    "Angle 4: " + getAngle(r4, r5, tofGroup[3], tofGroup[4]));

                            // Calculate the standard deviation of angle for each TOF grouping (assume edge-type)
                            tofGroup[8] = (getStandardDeviation(
                                    getAngle(r1, r2, tofGroup[0], tofGroup[1]),
                                    getAngle(r2, r3, tofGroup[1], tofGroup[2]),
                                    getAngle(r3, r4, tofGroup[2], tofGroup[3]),
                                    getAngle(r4, r5, tofGroup[3], tofGroup[4]),
                                    tofGroup[7]
                            ));

                            System.out.println("Average Angle: " + tofGroup[7] + " Deviation: " + tofGroup[8]);
*/
                            // Calculate the average distance (based on two-pair calculations for each TOG grouping (assume face-type)

                            // Calculate the standard deviation of distance for each TOF grouping (assume face-type)

                            // Calculate the average polar position (based on two-pair calculations) for each TOF grouping (assume face-type)

                            // Calculate the standard deviation of angle for each TOF grouping (assume face-type)

                            tofGroups.add(tofGroup);
                            i++;
                        }
                    }
                }
            }
        }


        // Determine the grouping with the smallest standard deviation (assume edge-type)
        ListIterator deviationIteration = tofGroups.listIterator();
        int smallestLengthDeviationIndex = 0;
        int smallestAngleDeviationIndex = 0;
        double lastLengthDeviation = 100;
        double lastAngleDeviation = 100;
        while(deviationIteration.hasNext()) {
            int nextIndex = deviationIteration.nextIndex();
            double[] group = (double[]) deviationIteration.next();
            if(group[5] )
            if(group[5] <= lastLengthDeviation) {
                lastLengthDeviation = group[5];
                smallestLengthDeviationIndex = nextIndex;
            }
            if(group[7] <= lastAngleDeviation) {
                lastAngleDeviation = group[7];
                smallestAngleDeviationIndex = nextIndex;
            }
        }


        // Use this code to check against the white paper that we're getting the right group.
        System.out.println("Smallest Length Deviation Index = " + smallestLengthDeviationIndex);
        System.out.println("Smallest Angle Deviation Index = " + smallestAngleDeviationIndex);
        System.out.println("TOF Group: " + tofGroups.get(smallestLengthDeviationIndex)[0] + " : " + tofGroups.get(smallestLengthDeviationIndex)[1]
             + " : " + tofGroups.get(smallestLengthDeviationIndex)[2] + " : " + tofGroups.get(smallestLengthDeviationIndex)[3] + " : " +
            tofGroups.get(smallestLengthDeviationIndex)[4]);
        System.out.println("Average distance: " + tofGroups.get(smallestLengthDeviationIndex)[5]);
        System.out.println("Average angle: " + Math.toDegrees(tofGroups.get(smallestAngleDeviationIndex)[7]));

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

    private static double getAngle(UltrasonicReceiver r1, UltrasonicReceiver r2, double l1, double l2) {
        double angle = 0;
        double d1 = r1.getDistance();
        double d2 = r2.getDistance();
        double d1squared = Math.pow(d1,2);
        double d2squared = Math.pow(d2,2);
        double l1squared = Math.pow(l1, 2);
        double l2squared = Math.pow(l2, 2);
        double numerator = (l2 * (l1squared - d1squared)) - (l1 * (l2squared - d2squared));
        double denominator = (d2 * (l1squared - d1squared)) - (d1 * (l2squared - d2squared));
        if(numerator / denominator > 1) { angle = -(Math.asin(1)); }
        if (numerator / denominator < -1) { angle = -(Math.asin(-1)); }
        if((numerator / denominator <= -1) && (numerator / denominator >= 1)) {angle = -(Math.asin(numerator / denominator)); }
        return angle;
    }
}
