public class Calculations {

    private final double SPEED_OF_SOUND = 331.5; // Meters per second
    private double temp; // In degrees Celsius
    private double time1;
    private double time2;
    private double distance1;
    private double distance2;
    private double length1;
    private double length2;
    private double length;
    private double theta;

    public void Calculations() {}

    public void Caluclations(double temp) {
        this.temp = temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getDistance(double temp, double time1, double time2, double distance1, double distance2) {
        this.temp = temp;
        this.time1 = time1;
        this.time2 = time2;
        this.distance1 = distance1;
        this.distance2 = distance2;

        this.length1 = (time1/1000000) * (SPEED_OF_SOUND + 0.6 * this.temp);
        this.length2 = (time2/1000000) * (SPEED_OF_SOUND + 0.6 * this.temp);

        this.length = 0.5 * ((this.distance2 * (Math.pow(length1,2)-Math.pow(this.distance1,2)))-(distance1 * (Math.pow(length2,2) -
                Math.pow(this.distance2,2)))) / ((this.distance2 * length1) - (this.distance1 * length2));

        return this.length;
    }

    public double getAngle(double temp, double time1, double time2, double distance1, double distance2) {
        this.temp = temp;
        this.time1 = time1;
        this.time2 = time2;
        this.distance1 = distance1;
        this.distance2 = distance2;

        this.length1 = (time1/1000000) * (SPEED_OF_SOUND + 0.6 * this.temp);
        this.length2 = (time2/1000000) * (SPEED_OF_SOUND + 0.6 * this.temp);

        double d1 = this.distance1;
        double d2 = this.distance2;
        double l1 = this.length1;
        double l2 = this.length2;
        double d1squared = Math.pow(this.distance1,2);
        double d2squared = Math.pow(this.distance2,2);
        double l1squared = Math.pow(this.length1,2);
        double l2squared = Math.pow(this.length2,2);
        this.theta = -Math.asin(((l2 * (l1squared-d1squared)) - (l1 * (l2squared-d2squared)))/
                ((d2 * (l1squared - d1squared))-(d1 * (l2squared-d2squared))));


        return Math.toDegrees(this.theta);
    }

    public double getAverage(double d1, double d2, double d3, double d4){
        double average = (d1 + d2 + d3 + d4) /4;
        return average;
    }

    public double getStandardDeviation(double d1, double d2, double d3, double d4, double average) {
        double sum = Math.pow((d1 - average),2) + Math.pow((d2 - average),2) + Math.pow((d3 - average),2) + Math.pow((d4 - average),2);
        double sigma = Math.sqrt(sum/3);
        return sigma;
    }


}
