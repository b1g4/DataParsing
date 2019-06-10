package subway;

import java.util.ArrayList;

/**
 * StationClass
 */
public class StationClass {
    public final String stationName;

    public ArrayList<Double[]> congestionList;
    public ArrayList<String> transferLine;

    public StationClass(String stationName){
        this.stationName = stationName;

        this.congestionList = new ArrayList<Double[]>();
    }

    public void addCongestion(Double[] congestionInTime) {
        this.congestionList.add(congestionInTime);
    }

    public ArrayList<Double[]> getConestionList(){
        return this.congestionList;
    }
}