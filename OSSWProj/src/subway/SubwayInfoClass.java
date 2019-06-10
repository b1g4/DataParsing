package subway;

import java.util.HashMap;
import java.util.ArrayList;
/**
 * SubwayInfoClass
 */
public class SubwayInfoClass {
    private static SubwayInfoClass subwayInfo = new SubwayInfoClass();

    private static final HashMap<String, Boolean> isCircularSeparator = new HashMap<String, Boolean>(){{
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

    private HashMap<String, LineClass> lineList; // line number, line class instance
    private HashMap<String, StationClass> stationList; // stationName, station class instance

    public void addLineInfo(String lineNum, ArrayList<String> lineInfo){
        if(this.isLineExist(lineNum)){
            
        }
        else {

        }
    }

    public void addStationInfo(String stationName, ArrayList<String> stationInfo){
        if(this.isStationExist(stationName)){

        }
        else{

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
        return this.lineList.containsKey(lineNum);
    }

    public boolean isStationExist(String stationName){
        return this.stationList.containsKey(stationName);
    }
}