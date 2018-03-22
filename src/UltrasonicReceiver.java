import java.util.ArrayList;
import java.util.Iterator;

public class UltrasonicReceiver {

    private static double SPEED_OF_SOUND = 331.5;   // Speed of sound in meters/second
    private double temp;     // Temperature in degrees Celsius
    private double distance;    // Distance from transmitter; negative is to the left, positive to the right
    private ArrayList<Double> tofReturns = new ArrayList<>();     // Time of flight returns
    private ArrayList<Double> lengths = new ArrayList<>();      // Length of straight-line distances to objects

    public UltrasonicReceiver() {
    }

    public UltrasonicReceiver(double temp) {
        this.temp = temp;
    }

    public UltrasonicReceiver(double temp, double distance) {
        this.temp = temp;
        this.distance = distance;
    }

    public UltrasonicReceiver(double temp, double distance, ArrayList<Double> tofReturns) {
        this.temp = temp;
        this.distance = distance;
        this.tofReturns = tofReturns;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public ArrayList<Double> getLOFS() {
        return lengths;
    }

    public void calculateLOFS() {
        if(!tofReturns.isEmpty()) {
            lengths.clear();
            Iterator itr = this.tofReturns.iterator();
            while (itr.hasNext()) {
                Object time = itr.next();
                lengths.add(((double)time/1000000) * (SPEED_OF_SOUND + 0.6 * this.temp));
            }
        }
    }

    public double calculateLength(double time) {
        return (time/1000000) * (SPEED_OF_SOUND + 0.6 * this.temp);
    }

    public ArrayList<Double> getTofReturns() {
        return tofReturns;
    }

    public void setTofReturns(ArrayList<Double> tofReturns) {
        this.tofReturns = tofReturns;
    }

    public int getNumberofReturns() {
        return tofReturns.size();
    }

    public void setTofReturn(double time) {
        tofReturns.add(time);
    }

    public void clearTofReturns() {
        tofReturns.clear();
    }
}
