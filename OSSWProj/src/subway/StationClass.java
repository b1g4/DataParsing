package subway;

import java.util.ArrayList;

/**
 * StationClass
 */
public class StationClass {
    public final String stationName;

    public ArrayList<Double[]> congestionList;
    public ArrayList<String> transferLineNumList;

    boolean isUpper;

    public StationClass(String stationName){
        this.stationName = stationName;
        this.isUpper = true;
        this.congestionList = new ArrayList<Double[]>();
    }

    public void addCongestion(ArrayList<String> congestionInTime) {
        if(isUpper){
            Double[] dArr = new Double[2];
            for(String str : congestionInTime){
                dArr[0] = Double.parseDouble(str);
                this.congestionList.add(dArr);
            }
            Double[] dummy = new Double[2];
            dummy[0] = 0.0;
            dummy[1] = 0.0;
            this.congestionList.add(1, dummy);
            this.congestionList.add(2, dummy);
            this.congestionList.add(3, dummy);
        }
        else{
            for(int i=0; i<this.congestionList.size(); i++){
                if(i == 0){
                    this.congestionList.get(i)[1] = Double.parseDouble(congestionInTime.get(i));
                }
                if(i == 1 || i == 2 || i == 3){
                    continue;
                }
                else{
                    this.congestionList.get(i)[1] = Double.parseDouble(congestionInTime.get(i));
                }
            }
        }
    }

    public ArrayList<Double[]> getConestionList(){
        return this.congestionList;
    }

    public void addTransferLineNum(String lineNum){
        if(lineNum.equals("")){
            return;
        }
        this.transferLineNumList.add(lineNum);
    }

    public ArrayList<String> getTransferLineNumList(){
        return this.transferLineNumList;
    }
}