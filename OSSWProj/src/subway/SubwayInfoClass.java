package subway;

import java.util.HashMap;
import java.util.ArrayList;
/**
 * SubwayInfoClass
 */
public class SubwayInfoClass {
    private static SubwayInfoClass subwayInfo = new SubwayInfoClass();

    private static final HashMap<String, Boolean> isCircular = new HashMap<String, Boolean>(){{
        put("상선", false);
        put("하선", false);
        put("외선", true);
        put("내선", true);
    }};

    private static final HashMap<String, Integer> direction = new HashMap<String, Integer>(){{
		put("상선", 0);
		put("내선", 0);
		put("외선", 1);
		put("하선", 1);
	}};


    public static SubwayInfoClass getInstance(){
        if(subwayInfo == null){
            subwayInfo = new SubwayInfoClass();
        }
        return subwayInfo;
    }

    private SubwayInfoClass(){

    }

    private HashMap<String, LineClass> lineList; // line number, line class instance
    private HashMap<String, StationClass> stationList; // stationName, station class instance

    public void addLineInfo(String lineNum, ArrayList<String> lineInfo){
        // lineInfo : direction, stationName, durationTime
        if(this.isLineExist(lineNum)){
            // 이미 추가된 노선 정보가 존재
            this.lineList.get(lineNum).addStationInfo(lineInfo.get(1));
            this.lineList.get(lineNum).addDuration(lineInfo.get(2), direction.get(lineInfo.get(0)));
        }
        else {
            // 처음 추가되는 노선정보
            LineClass line = new LineClass(lineNum, isCircular.get(lineInfo.get(0)));
            line.addStationInfo(lineInfo.get(1));
            line.addDuration(lineInfo.get(2), direction.get(lineInfo.get(0)));
            this.lineList.put(lineNum, line);
        }
    }

    public void addStationInfo(String stationName, ArrayList<String> stationInfo){
        // stationInfo : stationName, transferLineNumber, congestionList
        if(this.isStationExist(stationName)){
            // 이미 추가된 정류장 정보가 존재
            this.stationList.get(stationName).addTransferLineNum(stationInfo.get(1));
            this.stationList.get(stationName).addCongestion((ArrayList<String>)stationInfo.subList(2, stationInfo.size()));
        }
        else{
            // 처음 추가되는 정류장 정보
            StationClass station = new StationClass(stationName);
            station.addTransferLineNum(stationInfo.get(1));
            station.addCongestion((ArrayList<String>)stationInfo.subList(2, stationInfo.size()));
            this.stationList.put(stationName, station);
        }        
    }

    public LineClass getLineInfo(String lineNum){
        return this.lineList.get(lineNum);
    }

    public StationClass getStationInfo(String stationName){
        return this.stationList.get(stationName);
    }

    public HashMap<String, LineClass> getLineListInfo(){
        return this.lineList;
    }

    public HashMap<String, StationClass> getStationListInfo(){
        return this.stationList;
    }

    public boolean isLineExist(String lineNum){
        if(this.lineList.isEmpty())
            return false;
        return this.lineList.containsKey(lineNum);
    }

    public boolean isStationExist(String stationName){
        if(this.stationList.isEmpty())
            return false;
        return this.stationList.containsKey(stationName);
    }
}