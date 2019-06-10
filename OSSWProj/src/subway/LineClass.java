package subway;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * LineClass
 */
public class LineClass {
    public final String lineNum;
    private ArrayList<String> stationList;
    private ArrayList<Boolean> isTransfer;

    
    // 순환노선인지 아닌지 판단.
    public final boolean isCircular;

    // 상행 다음정거장까지 시간(내선)
    private ArrayList<Integer> timeToUpperStation;

    // 하행 다음정거장까지 시간(외선)
    private ArrayList<Integer> timeToLowerStation;

    // 현재 가르키고 있는 정류장 정보
    private int index;

    public LineClass(String lineNum, boolean isCircular){
        this.isTransfer = new ArrayList<Boolean>();
        this.timeToUpperStation = new ArrayList<Integer>();
        this.timeToLowerStation = new ArrayList<Integer>();
        this.stationList = new ArrayList<String>();        

        this.index = 0;
        this.lineNum = lineNum;
        this.isCircular = isCircular;
    }

    public void addStationInfo(String stationName){
        if(!this.isStationExist(stationName)){
            if(stationName.contains("(")){
                this.isTransfer.add(true);
            }
            else {
                this.isTransfer.add(false);
            }

            this.stationList.add(stationName);
        }        
    }

    public void addDuration(int time, int direction){
        if(direction == 0){
            this.timeToUpperStation.add(time);
        }
        else if(direction == 1){
            this.timeToLowerStation.add(time);
        }
        else{

        }
    }

    public ArrayList<String> getStationList(){
        return this.stationList;
    }

    public boolean isStationExist(String stationName){
        return this.stationList.contains(stationName);
    }

    public String getCurretnStationName(){
        return this.stationList.get(index);
    }

    

    public ArrayList<Integer> getDurationList(int direction){
        if(direction == 0){
            return this.timeToUpperStation;
        }
        else if(direction == 1){
            return this.timeToLowerStation;
        }
        else{
            return new ArrayList<Integer>();
        }
    }
}