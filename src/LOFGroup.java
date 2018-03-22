import java.util.ArrayList;

/**
 * @author Rich Dionne
 * @project UltrasonicSensors
 * @package PACKAGE_NAME
 * @date 3/21/2018
 */
public class LOFGroup {
    private ArrayList<Double> lofs = new ArrayList<>();
    private ArrayList<Double> distances = new ArrayList<>();
    private ArrayList<Double> edgeLengths = new ArrayList<>();
    private ArrayList<Double> edgeAngles = new ArrayList<>();
    private ArrayList<Double> faceLengths = new ArrayList<>();
    private ArrayList<Double> faceAngles = new ArrayList<>();
    private double edgeAverageLength;
    private double edgeLengthDeviation;
    private double edgeAverageAngle;
    private double edgeAngleDeviation;
    private double faceAverageLength;
    private double faceLengthDeviation;
    private double faceAverageAngle;
    private double faceAngleDeviation;

    public LOFGroup() {
    }

    public LOFGroup(ArrayList<Double> lofs, ArrayList<Double> distances) {
        this.lofs = lofs;
        this.distances = distances;
    }

    public double getEdgeAverageLength() {
        return edgeAverageLength;
    }

    public double getEdgeLengthDeviation() {
        return edgeLengthDeviation;
    }

    public double getEdgeAverageAngle() {
        return edgeAverageAngle;
    }

    public double getEdgeAngleDeviation() {
        return edgeAngleDeviation;
    }

    public void calculateAverageEdgeLength() {
        if (this.lofs.isEmpty() || this.distances.isEmpty()) {
            return;
        }
        for (int i = 1; i < this.lofs.size(); i++) {
            edgeLengths.add(getEdgeLength(this.distances.get(i-1), this.distances.get(i), this.lofs.get(i-1), this.lofs.get(i)));
        }
        this.edgeAverageLength = sumOfElements(edgeLengths) / this.lofs.size();
    }

    public void calculateEdgeLengthDeviation() {
        if(this.lofs.isEmpty() || this.distances.isEmpty() || (this.edgeAverageLength == 0)) {
            return;
        }
        double sum = 0;
        for(int i= 0; i < this.distances.size(); i++) {
            sum += Math.pow(this.distances.get(i) - this.edgeAverageLength, 2);
        }
        this.edgeLengthDeviation = Math.sqrt(sum / 3);
    }

    public void calculateAverageEdgeAngle() {
        if(this.lofs.isEmpty() || this.distances.isEmpty()) {
            return;
        }
        for (int i = 1; i < this.lofs.size(); i++) {
            edgeAngles.add(getEdgeAngle(this.distances.get(i-1), this.distances.get(i), this.lofs.get(i-1), this.lofs.get(i)));
        }
        double x = 0;
        double y = 0;
        for(int i = 0; i < edgeAngles.size(); i++) {
            if(Double.isNaN(edgeAngles.get(i))) {
                this.edgeAverageAngle = 0;
                this.edgeAngleDeviation = 0;
                return;
            }
            x += Math.cos(edgeAngles.get(i));
            y += Math.sin(edgeAngles.get(i));
        }
        x = x / edgeAngles.size();
        y = y / edgeAngles.size();

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

        this.edgeAverageAngle = angle;
        this.edgeAngleDeviation = 1 - r;
    }

    public void calculateAverageFaceLength() {
        if (this.lofs.isEmpty() || this.distances.isEmpty()) {
            return;
        }
        for (int i = 1; i < this.lofs.size(); i++) {
            faceLengths.add(getFaceLength(this.distances.get(i-1), this.distances.get(i), this.lofs.get(i-1), this.lofs.get(i)));
        }
        this.faceAverageLength = sumOfElements(faceLengths) / this.lofs.size();
    }

    public void calculateFaceLengthDeviation() {
        if(this.lofs.isEmpty() || this.distances.isEmpty() || (this.edgeAverageLength == 0)) {
            return;
        }
        double sum = 0;
        for(int i= 0; i < this.distances.size(); i++) {
            sum += Math.pow(this.distances.get(i) - this.faceAverageLength, 2);
        }
        this.faceLengthDeviation = Math.sqrt(sum / 3);
    }

    public void calculateAverageFaceAngle() {
        if(this.lofs.isEmpty() || this.distances.isEmpty()) {
            return;
        }
        for (int i = 1; i < this.lofs.size(); i++) {
            faceAngles.add(getFaceAngle(this.distances.get(i-1), this.distances.get(i), this.lofs.get(i-1), this.lofs.get(i)));
        }
        double x = 0;
        double y = 0;
        for(int i = 0; i < faceAngles.size(); i++) {
            if(Double.isNaN(faceAngles.get(i))) {
                this.faceAverageAngle = 0;
                this.faceAngleDeviation = 0;
                return;
            }
            x += Math.cos(faceAngles.get(i));
            y += Math.sin(faceAngles.get(i));
        }
        x = x / faceAngles.size();
        y = y / faceAngles.size();

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

        this.faceAverageAngle = angle;
        this.faceAngleDeviation = 1 - r;
    }

    private static double getEdgeLength(double d1, double d2, double l1, double l2) {
        double d1squared = Math.pow(d1, 2);
        double d2squared = Math.pow(d2, 2);
        double l1squared = Math.pow(l1, 2);
        double l2squared = Math.pow(l2, 2);
        double numerator = (d2 * (l1squared - d1squared)) - (d1 * (l2squared - d2squared));
        double denominator = (d2 * l1) - (d1 * l2);
        return 0.5 * numerator / denominator;
    }

    private static double getEdgeAngle(double d1, double d2, double l1, double l2) {
        double d1squared = Math.pow(d1, 2);
        double d2squared = Math.pow(d2, 2);
        double l1squared = Math.pow(l1, 2);
        double l2squared = Math.pow(l2, 2);
        double numerator = (l2 * (l1squared - d1squared)) - (l1 * (l2squared - d2squared));
        double denominator = (d2 * (l1squared - d1squared)) - (d1 * (l2squared - d2squared));
        return -(Math.asin(numerator / denominator));
    }

    private static double getFaceLength(double d1, double d2, double l1, double l2) {
        double d1squared = Math.pow(d1, 2);
        double d2squared = Math.pow(d2, 2);
        double l1squared = Math.pow(l1, 2);
        double l2squared = Math.pow(l2, 2);
        double numerator = (d2 * (l1squared -d1squared)) - (d1 * (l2squared - d2squared));
        double denominator = d2 - d1;
        return 0.5 * Math.sqrt(numerator / denominator);
    }

    private static double getFaceAngle(double d1, double d2, double l1, double l2) {
        double d1squared = Math.pow(d1, 2);
        double d2squared = Math.pow(d2, 2);
        double l1squared = Math.pow(l1, 2);
        double l2squared = Math.pow(l2, 2);
        double numerator = (l1squared - d1squared) - (l2squared - d2squared);
        double denominator = 2 * Math.sqrt((d2 - d1) * (d2 * (l1squared - d1squared) - d1 * (l2squared - d2squared)));
        return -Math.asin(numerator / denominator);
    }

    private static double sumOfElements(ArrayList<Double> lengths) {
        double sum = 0;
        for(int i = 0; i < lengths.size(); i++) {
            sum += lengths.get(i);
        }
        return sum;
    }

}

