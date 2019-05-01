package bus;

import java.util.ArrayList;
import java.util.HashMap;

public class StationClass{
    // public member variable
    public String stationId; // index : stnd_bsst_id, 4번 column
    public String stationArsNum; // index : bsst_ars_no, 5번 column
    public String stationName; // index : bus_sta_nm, 6번 column
    public String stationX; // index : bus_sta_x, 7번 column
    public String stationY; // index : bus_sta_y, 8번 column
    public HashMap<RouteClass, ArrayList<Integer>> routeList = new HashMap<RouteClass, ArrayList<Integer>>();
    
    // constructor
    public StationClass(ArrayList<String> infoList, String routeId){
        // infoList : id, arsNum, name, x, y 순서로 입력
        this.stationId = infoList.get(0);
        this.stationArsNum = infoList.get(1);
        this.stationName = infoList.get(2);
        this.stationX = infoList.get(3);
        this.stationY = infoList.get(4);
        ArrayList<Integer> dump = new ArrayList<Integer>();
        //this.routeList.put(routeInfo, dump);
    }

    public void addRouteInfo(String routeId){
        ArrayList<Integer> dump = new ArrayList<Integer>();
        //this.routeList.put(routeInfo, dump);
    }
    
    public void addCongestionInfo(String routeId, ArrayList<Integer> congestion){
        //this.routeList.get(routeInfo).clear();
        //this.routeList.get(routeInfo).addAll(congestion);
    }
}