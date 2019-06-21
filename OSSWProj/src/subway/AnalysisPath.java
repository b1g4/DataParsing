package subway;

import java.util.ArrayList;
import java.util.Calendar;
/**
 * AnalysisPath
 */
public class AnalysisPath {
    private SubwayInfoClass subwayInfo = SubwayInfoClass.getInstance();
    private ArrayList<Integer> time = new ArrayList<Integer>();
    private ArrayList<String> totalTime = new ArrayList<String>();
    private ArrayList<ArrayList<String>> totalCongestion = new ArrayList<ArrayList<String>>();

    private final int currentHour;
    private final int currentMinute;
    
    public AnalysisPath(ArrayList<ArrayList<StationClass>> resultPath, ArrayList<ArrayList<String>> transferInfoList){
        Calendar cal = Calendar.getInstance();

        currentHour = cal.get(Calendar.HOUR_OF_DAY);
        currentMinute = cal.get(Calendar.MINUTE);

        for(int i=0; i<resultPath.size(); i++){
            this.calc_time(resultPath.get(i), transferInfoList.get(i));
            System.out.println(i);
            this.calc_congestion(resultPath.get(i), transferInfoList.get(i));
        }
    }

    private void calc_time(ArrayList<StationClass> list, ArrayList<String> transferInfo){
        int hour = this.currentHour;
        int minute = this.currentMinute;

        LineClass lineInfo;
        String currentLineNum, nextLineNum;

        int i_direction = -1;

        // 현재 정류장 : list index 0
        // 다음 정류장 : list index 1 일때 
        // 다음 정류장까지 걸리는 시간은 list[1]에 있는 시간임.
        currentLineNum = transferInfo.get(0);
        lineInfo = this.subwayInfo.getLineInfo(currentLineNum);
        lineInfo.setCurrentStation(list.get(0).stationName);
        for(int i=0; i<list.size()-1; i++){
            nextLineNum = transferInfo.get(i+1);
            if(!(currentLineNum.equals(nextLineNum))){
                lineInfo = this.subwayInfo.getLineInfo(nextLineNum);
                lineInfo.setCurrentStation(list.get(i).stationName);
                i_direction = -1;
            }
            if(i_direction == -1){
                if(lineInfo.moveNext(1)){
                    if(lineInfo.getCurrentStationName().equals(list.get(i+1).stationName)){
                        // lower
                        i_direction = 1;
                    }
                    else{
                        // upper
                        i_direction = 0;
                    }
                }
            }
            System.out.println(list.get(i+1).stationName);
            this.time.add(lineInfo.getDuration(list.get(i+1).stationName, i_direction));
            
        }
        int sum = 0;
        for(int i : time){
            sum += i;
        }
        this.totalTime.add(String.valueOf(sum));
    }

    private void calc_congestion(ArrayList<StationClass> list, ArrayList<String> transferInfo){


        this.time.clear();
    }

    public ArrayList<String> getTotalTime(){
        return this.totalTime;
    }

    public ArrayList<ArrayList<String>> getTotalCongestion(){
        return this.totalCongestion;
    }
}