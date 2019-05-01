package bus;

import java.util.ArrayList;
import java.util.List;

public class RouteClass{
    // public member variable
    public String routeId; // index : bus_route_id, 0번 column
    public String routeName; // index : bus_route_nm, 1번 column
    //public ArrayList<StationClass> stationList = new ArrayList<StationClass>();

    // constructor
    public RouteClass(List<String> infoList, String stationId){
        // routeInfo : id, Name
        this.routeId = infoList.get(0);
        this.routeName = infoList.get(1);
        //this.stationList.add(stationInfo); // 반드시 순서대로 추가해야함
    }

    public void addStationInfo(String stationId){
        //this.stationList.add(stationInfo);
    }
}