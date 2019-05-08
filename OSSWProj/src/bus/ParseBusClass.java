package bus;

import fileIO.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * ParseBusClass
 */
public class ParseBusClass {
    // private member variable
    private BusInfoClass busInfo;
    private ArrayList<ArrayList<String>> valuesInFile;
    private int rowNum;
    private int columnNum;

    // constructor
    public ParseBusClass(ArrayList<ArrayList<String>> valuesInFile){
        this.busInfo = BusInfoClass.getInstance();
        this.valuesInFile = valuesInFile;
        this.rowNum = this.valuesInFile.size();
        this.columnNum = this.valuesInFile.get(0).size();
    }

    // public member method
    public boolean parsingRouteStationInfo(){
        for (int i = 0; i < this.rowNum; i++) {

            StationClass sta;
            RouteClass rta;
            String routeId = this.valuesInFile.get(i).get(0);
            String stationId = this.valuesInFile.get(i).get(4);

            if (busInfo.isRouteExist(routeId)) {
                // route instance 존재
                if (busInfo.isStationExist(stationId)) {
                    // route instance 존재, station instance 존재
                    sta = busInfo.getStationInfo(stationId);
                    sta.setRouteInfo(routeId);
                } else {
                    // route instance 존재, station instance 존재 x
                    sta = new StationClass(this.valuesInFile.get(i).subList(4, columnNum),
                            this.valuesInFile.get(i).get(0));
                    busInfo.setStation(sta);

                }
                rta = busInfo.getRouteInfo(routeId);
                rta.setStationInfo(stationId);
            } else {
                // route instance 존재 x
                if (busInfo.isStationExist(stationId)) {
                    // route instance 존재x, station instance 존재
                    rta = new RouteClass(this.valuesInFile.get(i), this.valuesInFile.get(i).get(4));
                    busInfo.setRoute(rta);
                    sta = busInfo.getStationInfo(stationId);
                    sta.setRouteInfo(routeId);
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