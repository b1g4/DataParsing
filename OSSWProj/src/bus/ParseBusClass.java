package bus;

import fileIO.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * ParseBusClass
 * 전반적인 버스와 정류장 정보를 모두 정리 한 후
 * Congestion관련 정보를 정리한다.
 */
public class ParseBusClass {
    // private member variable
    private BusInfoClass busInfo;
    private ArrayList<ArrayList<String>> valuesInFile;
    private int rowNum;
    private int columnNum;

    /**
     * Constructor
     * @param valuesInFile : 파일을 읽고 저장된 정보
     */
    public ParseBusClass(ArrayList<ArrayList<String>> valuesInFile){
        this.busInfo = BusInfoClass.getInstance();
        this.valuesInFile = valuesInFile;
        this.rowNum = this.valuesInFile.size();
        this.columnNum = this.valuesInFile.get(0).size();
    }

    /**
     * BusInfoClass의 Route, Station관련 HashMap에 정보를 추가하는 작업
     * 이 작업이 끝나면 배차간격과 첫차막차시간을 읽고 //hdy
     * Congestion관련 작업이 수행되어야 한다. 
     * @return 모든 파일 입출력 작업이 정상적으로 끝마치면 true
     */
    public boolean parsingRouteStationInfo(){
        for (int i = 0; i < this.rowNum; i++) {

            StationClass sta;
            RouteClass rta;
            String routeName = this.valuesInFile.get(i).get(1);
            String stationId = this.valuesInFile.get(i).get(4);

            if (busInfo.isRouteExist(routeName)) {
                // route instance 존재
                if (busInfo.isStationExist(stationId)) {
                    // route instance 존재, station instance 존재
                    sta = busInfo.getStationInfo(stationId);
                    sta.setRouteInfo(routeName);
                } else {
                    // route instance 존재, station instance 존재 x
                    sta = new StationClass(this.valuesInFile.get(i).subList(4, columnNum),
                            this.valuesInFile.get(i).get(1));
                            //this.valuesInFile.get(i).get(0));
                    busInfo.setStation(sta);

                }
                rta = busInfo.getRouteInfo(routeName);
                rta.setStationInfo(stationId);
            } else {
                // route instance 존재 x
                if (busInfo.isStationExist(stationId)) {
                    // route instance 존재x, station instance 존재
                    rta = new RouteClass(this.valuesInFile.get(i), this.valuesInFile.get(i).get(4));
                    busInfo.setRoute(rta);
                    sta = busInfo.getStationInfo(stationId);
                    sta.setRouteInfo(routeName);
                } else {
                    // route instance 존재 x, station instance 존재 x
                    rta = new RouteClass(this.valuesInFile.get(i), this.valuesInFile.get(i).get(4));
                    sta = new StationClass(this.valuesInFile.get(i).subList(4, columnNum),
                            //this.valuesInFile.get(i).get(0));
                            this.valuesInFile.get(i).get(1));
                    busInfo.setStation(sta);
                    busInfo.setRoute(rta);
                }
            }
        }

        //csv파일 생성확인 코드 , 삭제예정
        //try {
        //    WriteCsvClass tmpc = new WriteCsvClass();
        //    tmpc.writeRouteClass(busInfo.getRouteHashMap());
           
        //    tmpc.writeStationClass(busInfo.getStationHashMap());
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
        return true;
    }

    //hdy
    /**
     * 노선별로 평/토/일 배차간격과 첫차,막차시간을 route에 저장
     * @return 모든 파일 입출력 작업이 정상적으로 끝마치면 true
     */
    public boolean parsing_InternalTime_Info(){
        for (int i = 0; i < this.rowNum; i++) {
            String routeName = this.valuesInFile.get(i).get(1);

            if(busInfo.isRouteExist(routeName)){
                //route instance 존재
                ArrayList<Integer> intervals=new ArrayList<>();
                ArrayList<Integer> times=new ArrayList<>();
                intervals.add(Integer.valueOf(this.valuesInFile.get(i).get(2)));
                intervals.add(Integer.valueOf(this.valuesInFile.get(i).get(3)));
                intervals.add(Integer.valueOf(this.valuesInFile.get(i).get(4)));

                for(int j=5;j<7;j++){
                    String tmp=this.valuesInFile.get(i).get(j);
                    Integer hour=Integer.valueOf(tmp.substring(0, 2));
                    Integer minute=Integer.valueOf(tmp.substring(2));
                    minute=minute+60*hour;
                    times.add(minute);
                }
                busInfo.setRoute_Interval(routeName, intervals);
                busInfo.setRoute_Time(routeName, times);
            }else{
                //route instance 존재 x
                System.out.println("test=="+routeName+"에 해당하는 instance가 존재x");
            }
        }

        //csv파일 생성확인 코드 , 삭제예정
        try {
            WriteCsvClass tmpc = new WriteCsvClass();
            tmpc.writeRouteClass_new(busInfo.getRouteHashMap());
           
            tmpc.writeStationClass(busInfo.getStationHashMap());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    
    
}