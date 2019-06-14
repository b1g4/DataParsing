package subway;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * LineClass
 */
public class LineClass {
    public final String lineNum;
    private ArrayList<String> stationList;
    private ArrayList<Boolean> transferInfoList;

    
    // 순환노선인지 아닌지 판단.
    private final boolean isCircular;

    // 상행 다음정거장까지 시간(내선)
    private ArrayList<Integer> timeToUpperStation;

    // 하행 다음정거장까지 시간(외선)
    private ArrayList<Integer> timeToLowerStation;

    // 현재 가르키고 있는 정류장 정보
    private int index;

    public LineClass(String lineNum, boolean isCircular){
        this.transferInfoList = new ArrayList<Boolean>();
        this.timeToUpperStation = new ArrayList<Integer>();
        this.timeToLowerStation = new ArrayList<Integer>();
        this.stationList = new ArrayList<String>();        

        this.index = 0;
        this.lineNum = lineNum;
        this.isCircular = isCircular;
    }

    public void addStationInfo(String stationName){
        boolean chk = false;
        if(stationName.contains("(")){
            chk = true;
            stationName = stationName.split("\\(")[0];
        }
        else {
            chk = false;
        }

        if(!this.isStationExist(stationName)){
            this.transferInfoList.add(chk);
            this.stationList.add(stationName);
        }        
    }

    public void addDuration(String time, int direction){
        int i_time = Integer.parseInt(time);
        if(direction == 0){
            this.timeToUpperStation.add(i_time);
        }
        else if(direction == 1){
            this.timeToLowerStation.add(i_time);
        }
        else{

        }
    }
    public void setCurrentStation(String stationName){
        this.index = this.stationList.indexOf(stationName);
    }

    public ArrayList<String> getStationList(){
        return this.stationList;
    }

    public boolean isStationExist(String stationName){
        return this.stationList.contains(stationName);
    }

    public boolean isCircular(){
        return this.isCircular;
    }

    public boolean isFirst(String stationName){
        if(this.stationList.indexOf(stationName) == 0){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isLast(String stationName){
        if(this.stationList.indexOf(stationName) == this.stationList.size()-1){
            return true;
        }
        else{
            return false;
        }
    }

    public String getCurrentStationName(){
        return this.stationList.get(index);
    }

    public boolean isTransfer(String stationName){
        int indexNum = this.stationList.indexOf(stationName);
        return this.transferInfoList.get(indexNum);
    }

    public boolean moveNext(int direction){
        if(direction == 0){
            if(this.index == this.stationList.size()-1){
                if(this.isCircular){
                    index = 0;
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                index++;
                return true;
            }
        }
        else if(direction == 1){
            if(this.index == 0){
                if(this.isCircular){
                    index = this.stationList.size()-1;
                    return true;
                }
                else {
                    return false;
                }
            }
            else{
                index--;
                return true;
            }
        }
        else{
            return false;
        }
    }
}