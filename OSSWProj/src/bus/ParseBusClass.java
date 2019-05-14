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
     * 이 작업이 끝나면 Congestion관련 작업이 수행되어야 한다. 
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
                            this.valuesInFile.get(i).get(0));
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
                            this.valuesInFile.get(i).get(0));
                    busInfo.setStation(sta);
                    busInfo.setRoute(rta);
                }
            }
        }

        //csv파일 생성확인 코드 , 삭제예정
        try {
            WriteCsvClass tmpc = new WriteCsvClass();
            tmpc.writeRouteClass(busInfo.getRouteHashMap());
           
            tmpc.writeStationClass(busInfo.getStationHashMap());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    
    
}