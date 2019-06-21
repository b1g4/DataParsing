package subway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * StationClass
 */
public class StationClass {
    public final String stationName;

    public HashMap<String, ArrayList<Double[]>> congestionList;
    public ArrayList<String> transferLineNumList;

    private int isUpper;

    public StationClass(String stationName){
        this.stationName = stationName;
        this.isUpper = 0;
        this.congestionList = new HashMap<String, ArrayList<Double[]>>();
        this.transferLineNumList = new ArrayList<String>();
    }

    public void addCongestion(String lineNum, List<String> list) {
        System.out.println(list);
        if(this.isUpper == 0){
            ArrayList<Double[]> tmpList = new ArrayList<Double[]>();
            for(String str : list){
                Double[] dArr = new Double[2];
                dArr[0] = Double.parseDouble(str);
                tmpList.add(dArr);
            }
            Double[] dummy = new Double[2];
            dummy[0] = 0.0;
            dummy[1] = 0.0;
            tmpList.add(1, dummy);
            tmpList.add(2, dummy);
            tmpList.add(3, dummy);
            this.congestionList.put(lineNum, tmpList);
        }
        else if(this.isUpper == 1){
            for(int i=0; i<this.congestionList.get(lineNum).size(); i++){
                if(i == 0){
                    this.congestionList.get(lineNum).get(i)[1] = Double.parseDouble(list.get(i));
                }
                else if(i == 1 || i == 2 || i == 3){
                    continue;
                }
                else{
                    this.congestionList.get(lineNum).get(i)[1] = Double.parseDouble(list.get(i-3));
                }
            }
        }
    }

    public void setDirection(int isUpper){
        this.isUpper = isUpper;
    }

    public HashMap<String, ArrayList<Double[]>> getConestionList(){
        return this.congestionList;
    }

    public void addTransferLineNum(String lineNum){
        if(this.transferLineNumList.contains(lineNum)){
            return;
        }
        this.transferLineNumList.add(lineNum);
    }

    public ArrayList<String> getTransferLineNumList(){
        return this.transferLineNumList;
    }
}