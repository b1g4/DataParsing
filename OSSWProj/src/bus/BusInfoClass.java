package bus;

import java.util.Collection;
import java.util.HashMap;

public class BusInfoClass{
    // 싱글톤객체
    // 
    private static BusInfoClass busInfo = new BusInfoClass();

    public static BusInfoClass getInstance(){
        if(busInfo == null){
            busInfo = new BusInfoClass();
        }
        return busInfo;
    }

    // 전체 경로정보 <RouteID, RouteClass Instance> 형식으로 저장
    private HashMap<String, RouteClass> RouteList = new HashMap<String, RouteClass>();
    // 전체 정류장 정보 <StationID, StationClass Instance> 형식으로 저장
    private HashMap<String, StationClass> StationList = new HashMap<String, StationClass>();

    // constructor
    private BusInfoClass(){
        System.out.println("hello");
    }

    // HashMap에 경로 추가
    public void setRoute(RouteClass instance){
        this.RouteList.put(instance.routeId, instance);
    }

    // HashMap에 정류장 추가
    public void setStation(StationClass instance){
        this.StationList.put(instance.stationId, instance);
    }

    // 특정  Route 정보 불러오기 
    // return 값은 Route class instance
    public RouteClass getRouteInfo(String routeId){
        return this.RouteList.get(routeId);
    }

    // 특정 Station 정보 불러오기
    // return 값은 Station class instance
    public StationClass getStationInfo(String stationId){
        return this.StationList.get(stationId);
    }

    // Station ID로 해당 Station Instance가 생성되어있는지 검사
    // false면 Station Instacne생성하고 addStation Call
    // true면 getStationInfo(stationId)를 call하고 해당 Instance에 정보 추가
    public boolean isStationExist(String stationId){
        return this.StationList.containsKey(stationId);
    }

    public boolean isRouteExist(String routeId){
        return this.RouteList.containsKey(routeId);
    }
    
    //RouteList반환
    public HashMap<String, RouteClass> getRouteHashMap(){
            return RouteList;
    }

    public HashMap<String, StationClass> getStationHashMap(){
        return StationList;
}


    //hashmap확인용 메소드 , 삭제예정
    public void hashmapPrint(){
        /*
        Collection<String> keys = RouteList.keySet();
        for(String key : keys){
            System.out.println(key);
        }
        */
        Collection<String> keyss = StationList.keySet();
        for(String key : keyss){
            System.out.println(key);
        }
    }

    
}
