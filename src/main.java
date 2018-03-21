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
        r1.setTofReturn(2901.0);

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
                            double[] tofGroup = new double[13];
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

                            // Calculate the average polar position (based on two-pair calculations) for each TOF grouping (assume edge-type)
                            double angle1 = getAngle(r1, r2, tofGroup[0], tofGroup[1]);
                            double angle2 = getAngle(r2, r3, tofGroup[1], tofGroup[2]);
                            double angle3 = getAngle(r3, r4, tofGroup[2], tofGroup[3]);
                            double angle4 = getAngle(r4, r5, tofGroup[3], tofGroup[4]);
                            if(Double.isNaN(angle1) || Double.isNaN(angle2) || Double.isNaN(angle3) || Double.isNaN(angle4)) {
                                tofGroup[7] = 0;
                                tofGroup[8] = 0;
                            } else {
                                double x = (Math.cos(angle1) + Math.cos(angle2) + Math.cos(angle3) + Math.cos(angle4)) / 4;
                                double y = (Math.sin(angle1) + Math.sin(angle2) + Math.sin(angle3) + Math.sin(angle4)) / 4;
                                double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
                                double angle = Math.atan(y / x);
                                if (y > 0 && x > 0) {
                                    angle = Math.toDegrees(angle);
                                }
                                if(y < 0 && x > 0) {
                                    angle = Math.toDegrees(angle) + 360;
                                }
                                if(x < 0) {
                                    angle = Math.toDegrees(angle) + 180;
                                }

                                if(angle > 270 && angle <= 361) { angle = angle - 360; }

                                tofGroup[7] = angle;
                                tofGroup[8] = 1 - r;
                            }

                            // Calculate the average distance (based on two-pair calculations for each TOG grouping (assume face-type)
                            tofGroup[9] = (getAverage(
                                    getFaceLength(r1, r2, tofGroup[0], tofGroup[1]),
                                    getFaceLength(r2, r3, tofGroup[1], tofGroup[2]),
                                    getFaceLength(r3, r4, tofGroup[2], tofGroup[3]),
                                    getFaceLength(r4, r5, tofGroup[3], tofGroup[4])
                            ));

                            // Calculate the standard deviation of distance for each TOF grouping (assume face-type)
                            tofGroup[10] = (getStandardDeviation(
                                    getFaceLength(r1, r2, tofGroup[0], tofGroup[1]),
                                    getFaceLength(r2, r3, tofGroup[1], tofGroup[2]),
                                    getFaceLength(r3, r4, tofGroup[2], tofGroup[3]),
                                    getFaceLength(r4, r5, tofGroup[3], tofGroup[4]),
                                    tofGroup[9]
                            ));

                            // Calculate the average polar position (based on two-pair calculations) for each TOF grouping (assume face-type)
                            angle1 = getFaceAngle(r1, r2, tofGroup[0], tofGroup[1]);
                            angle2 = getFaceAngle(r2, r3, tofGroup[1], tofGroup[2]);
                            angle3 = getFaceAngle(r3, r4, tofGroup[2], tofGroup[3]);
                            angle4 = getFaceAngle(r4, r5, tofGroup[3], tofGroup[4]);
                            if(Double.isNaN(angle1) || Double.isNaN(angle2) || Double.isNaN(angle3) || Double.isNaN(angle4)) {
                                tofGroup[11] = 0;
                                tofGroup[12] = 0;
                            } else {
                                double x = (Math.cos(angle1) + Math.cos(angle2) + Math.cos(angle3) + Math.cos(angle4)) / 4;
                                double y = (Math.sin(angle1) + Math.sin(angle2) + Math.sin(angle3) + Math.sin(angle4)) / 4;
                                double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
                                double angle = Math.atan(y / x);
                                if (y > 0 && x > 0) {
                                    angle = Math.toDegrees(angle);
                                }
                                if(y < 0 && x > 0) {
                                    angle = Math.toDegrees(angle) + 360;
                                }
                                if(x < 0) {
                                    angle = Math.toDegrees(angle) + 180;
                                }

                                if(angle > 270 && angle <= 361) { angle = angle - 360; }

                                tofGroup[11] = angle;
                                tofGroup[12] = 1 - r;
                            }

                            tofGroups.add(tofGroup);
                            i++;
                        }
                    }
                }
            }
        }

        // Determine number of objects
        int maxReturns = 0;
        if(r1.getNumberofReturns() > maxReturns) { maxReturns = r1.getNumberofReturns(); }
        if(r2.getNumberofReturns() > maxReturns) { maxReturns = r2.getNumberofReturns(); }
        if(r3.getNumberofReturns() > maxReturns) { maxReturns = r3.getNumberofReturns(); }
        if(r4.getNumberofReturns() > maxReturns) { maxReturns = r4.getNumberofReturns(); }
        if(r5.getNumberofReturns() > maxReturns) { maxReturns = r5.getNumberofReturns(); }

        // Determine the groupings with the smallest standard deviation (assume edge-type)
        int[] groupIndices = new int[maxReturns];
        int smallestLengthDeviationIndex = 0;
        int smallestAngleDeviationIndex = 0;
        double lastLengthDeviation = 100;
        double lastAngleDeviation = 100;
        double lastLoopLengthDeviation = 0;
        double lastLoopAngleDeviation = 0;
        for(i = 0; i < maxReturns; i++) {
            ListIterator deviationIteration = tofGroups.listIterator();
            while (deviationIteration.hasNext()) {
                int nextIndex = deviationIteration.nextIndex();
                double[] group = (double[]) deviationIteration.next();
                if (group[6] <= lastLengthDeviation && group[6] > lastLoopLengthDeviation) {
                    lastLengthDeviation = group[6];
                    smallestLengthDeviationIndex = nextIndex;
                }
                if (group[8] <= lastAngleDeviation && group[8] > lastLoopAngleDeviation) {
                    lastAngleDeviation = group[8];
                    smallestAngleDeviationIndex = nextIndex;
                }
            }

            lastLoopLengthDeviation = lastLengthDeviation;
            lastLoopAngleDeviation = lastAngleDeviation;
            lastLengthDeviation = 100;
            lastAngleDeviation = 100;

            if(lastAngleDeviation <= lastLengthDeviation) {
                groupIndices[i] = smallestAngleDeviationIndex;
            } else {
                groupIndices[i] = smallestLengthDeviationIndex;
            }
        }

        // Determine the groupings with the smallest standard deviation (assume face-type)
        int[] groupIndicesFace = new int[maxReturns];
        int smallestFaceLengthDeviationIndex = 0;
        int smallestFaceAngleDeviationIndex = 0;
        double lastFaceLengthDeviation = 100;
        double lastFaceAngleDeviation = 100;
        double lastFaceLoopLengthDeviation = 0;
        double lastFaceLoopAngleDeviation = 0;
        for(i = 0; i < maxReturns; i++) {
            ListIterator faceDeviationIteration = tofGroups.listIterator();
            while (faceDeviationIteration.hasNext()) {
                int nextIndex = faceDeviationIteration.nextIndex();
                double[] group = (double[]) faceDeviationIteration.next();
                if (group[10] <= lastFaceLengthDeviation && group[10] > lastFaceLoopLengthDeviation) {
                    lastFaceLengthDeviation = group[10];
                    smallestFaceLengthDeviationIndex = nextIndex;
                }
                if (group[12] <= lastFaceAngleDeviation && group[12] > lastFaceLoopAngleDeviation) {
                    lastFaceAngleDeviation = group[12];
                    smallestFaceAngleDeviationIndex = nextIndex;
                }
            }

            lastFaceLoopLengthDeviation = lastFaceLengthDeviation;
            lastFaceLoopAngleDeviation = lastFaceAngleDeviation;
            lastFaceLengthDeviation = 100;
            lastFaceAngleDeviation = 100;

            if(lastFaceAngleDeviation <= lastFaceLengthDeviation) {
                groupIndicesFace[i] = smallestFaceAngleDeviationIndex;
            } else {
                groupIndicesFace[i] = smallestFaceLengthDeviationIndex;
            }
        }

        for(i = 0; i < maxReturns; i++) {
            if(tofGroups.get(groupIndices[i])[6] < tofGroups.get(groupIndices[i])[8]) {
                if(tofGroups.get(groupIndices[i])[6] < tofGroups.get(groupIndicesFace[i])[10]){
                    System.out.println("Edge type object, index: " + groupIndices[i] + ".");
                    System.out.println("Distance: " + tofGroups.get(groupIndices[i])[5]);
                    System.out.println("Angle: " + tofGroups.get(groupIndices[i])[7]);
                } else {
                    System.out.println("Face type object, index: " + groupIndicesFace[i] + ".");
                    System.out.println("Distance: " + tofGroups.get(groupIndicesFace[i])[9]);
                    System.out.println("Angle: " + tofGroups.get(groupIndicesFace[i])[11]);
                }
            } else {
                if (tofGroups.get(groupIndices[i])[8] < tofGroups.get(groupIndicesFace[i])[12]) {
                    System.out.println("Edge type object, index: " + groupIndices[i] + ".");
                    System.out.println("Distance: " + tofGroups.get(groupIndices[i])[5]);
                    System.out.println("Angle: " + tofGroups.get(groupIndices[i])[7]);
                } else {
                    System.out.println("Face type object, index: " + groupIndicesFace[i] + ".");
                    System.out.println("Distance: " + tofGroups.get(2)[9]);
                    System.out.println("Angle: " + tofGroups.get(2)[11]);
                }
            }
            System.out.println();
        }

        /*
        // Use this code to check against the white paper that we're getting the right group.
        System.out.println("Smallest Length Deviation Index = " + smallestLengthDeviationIndex);
        System.out.println("Smallest Angle Deviation Index = " + smallestAngleDeviationIndex);
        System.out.println("TOF Group: " + tofGroups.get(smallestLengthDeviationIndex)[0] + " : " + tofGroups.get(smallestLengthDeviationIndex)[1]
             + " : " + tofGroups.get(smallestLengthDeviationIndex)[2] + " : " + tofGroups.get(smallestLengthDeviationIndex)[3] + " : " +
            tofGroups.get(smallestLengthDeviationIndex)[4]);
        System.out.println("Average distance: " + tofGroups.get(smallestAngleDeviationIndex)[5]);
        System.out.println("Distance deviation: " + tofGroups.get(smallestLengthDeviationIndex)[6]);
        System.out.println("Average angle: " + (Math.toDegrees(tofGroups.get(smallestAngleDeviationIndex)[7]) - 360));
        System.out.println("Angle Deviation: " + tofGroups.get(smallestAngleDeviationIndex)[8]);
*/
        // Calculate the average distance (based on two-pair calculations for each TOG grouping (assume face-type)

        // Calculate the average polar position (based on two-pair calculations) for each TOF grouping (assume face-type)

        // Calculate the standard deviation for each TOF grouping (assume face-type)

        // Determine the grouping with the smallest standard deviation (assume face-type)


    }

    private static double getLength(UltrasonicReceiver r1, UltrasonicReceiver r2, double l1, double l2) {
        double length;
        double d1 = r1.getDistance();
        double d2 = r2.getDistance();
        double d1squared = Math.pow(d1, 2);
        double d2squared = Math.pow(d2, 2);
        double l1squared = Math.pow(l1, 2);
        double l2squared = Math.pow(l2, 2);
        double numerator = (d2 * (l1squared - d1squared)) - (d1 * (l2squared - d2squared));
        double denominator = (d2 * l1) - (d1 * l2);
        length = 0.5 * numerator / denominator;
        return length;
    }

    private static double getFaceLength(UltrasonicReceiver r1, UltrasonicReceiver r2, double l1, double l2) {
        double length;
        double d1 = r1.getDistance();
        double d2 = r2.getDistance();
        double d1squared = Math.pow(d1, 2);
        double d2squared = Math.pow(d2, 2);
        double l1squared = Math.pow(l1, 2);
        double l2squared = Math.pow(l2, 2);
        double numerator = (d2 * (l1squared -d1squared)) - (d1 * (l2squared - d2squared));
        double denominator = d2 - d1;
        length = 0.5 * Math.sqrt(numerator / denominator);
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
        double d1squared = Math.pow(d1, 2);
        double d2squared = Math.pow(d2, 2);
        double l1squared = Math.pow(l1, 2);
        double l2squared = Math.pow(l2, 2);
        double numerator = (l2 * (l1squared - d1squared)) - (l1 * (l2squared - d2squared));
        double denominator = (d2 * (l1squared - d1squared)) - (d1 * (l2squared - d2squared));
        angle = -(Math.asin(numerator / denominator));
        return angle;
    }

    private static double getFaceAngle(UltrasonicReceiver r1, UltrasonicReceiver r2, double l1, double l2) {
        double angle = 0;
        double d1 = r1.getDistance();
        double d2 = r2.getDistance();
        double d1squared = Math.pow(d1, 2);
        double d2squared = Math.pow(d2, 2);
        double l1squared = Math.pow(l1, 2);
        double l2squared = Math.pow(l2, 2);
        double numerator = (l1squared - d1squared) - (l2squared - d2squared);
        double denominator = 2 * Math.sqrt((d2 - d1) * (d2 * (l1squared - d1squared) - d1 * (l2squared - d2squared)));
        angle = -Math.asin(numerator / denominator);
        return angle;
    }
}
