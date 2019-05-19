package bus;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * 싱글톤 객체 
 * 모든 정보는 프로그램 실행부터 끝까지 한번 읽어들이고 수정이 되면 안되고 
 * 항상 접근이 가능해야 한다. 
 */
public class BusInfoClass{
    private static BusInfoClass busInfo = new BusInfoClass();
    // 전체 경로정보 <RouteName, RouteClass Instance> 형식으로 저장
    private HashMap<String, RouteClass> RouteList = new HashMap<String, RouteClass>();
    // 전체 정류장 정보 <StationID, StationClass Instance> 형식으로 저장
    private HashMap<String, StationClass> StationList = new HashMap<String, StationClass>();
    // 전체 혼잡도 정보 <stationID_RouteName, CongestionClass Instance> 형식으로 저장
    /* -----------이 주석은 나중에 지워도 됨-------------
     * ParsingcongestionClass.java 에 아래와 같은 코드가 있었음
     * int day = super.WhatDay(date);
       super.setTotalDaysInfo(day);
       super.setTotalPeopleInfo(day, totalRide, totalAlight);
     * 파일 정보들을 모두 저장한 후에 혼잡도 계산을 해야하므로 CongestionClass Instance들을 저장할 공간이 필요했음
     * 그래서 BusInfoClass에 저장, 계산해서 나온 CongestionList의 값을 
     * 나중에 StationList의 routeList로 옮길 예정
     */
    private HashMap<String, CongestinoClass> CongestionList = new HashMap<String, CongestinoClass>();
    

    /**
     * 인스턴스를 한개만 생성하기 위함
     * @return BusInfoClass Instance
     */
    public static BusInfoClass getInstance(){
        if(busInfo == null){
            busInfo = new BusInfoClass();
        }
        return busInfo;
    }

    /**
     * Constructor
     * 여러 instance 생성을 막기 위해 private으로 지정
     */
    private BusInfoClass(){

    }

    /**
     * Route hashmap에 정보를 저장하는 함수
     * @param instance : RouteClass instance
     */
    public void setRoute(RouteClass instance){
       // this.RouteList.put(instance.routeId, instance);
       this.RouteList.put(instance.routeName, instance);
    }

    /**
     * Station hashmap에 정보를 저장
     * @param instance : StationClass instance
     */
    public void setStation(StationClass instance){
        this.StationList.put(instance.stationId, instance);
    }

    /**
     * 특정 Route정보를 반환
     * @param routeName : 노선 이름, 9자리 숫자로 이루어짐
     * @return RouteClass : routeID에 해당하는 RouteClass instance를 반환
     */
    public RouteClass getRouteInfo(String routeName){
        return this.RouteList.get(routeName);
    }

    /**
     * 특정 Station정보를 반환
     * @param stationId : 표준 정류장 ID, 
     * @return StationClass : stationID에 해당하는 StationClass instance를 반환
     */
    public StationClass getStationInfo(String stationId){
        return this.StationList.get(stationId);
    }

    /**
     * StationID를 이용해 특정 정류장에 관한 class instance가 생성되어 있는지 검사한다. 
     * @param stationId
     * @return boolean
     */
    public boolean isStationExist(String stationId){
        return this.StationList.containsKey(stationId);
    }

    /**
     * RouteID를 이용해 특정 노선에 관한 class instance가 생성되어 있는지 검사한다.
     * @param routeName
     * @return boolean
     */
    public boolean isRouteExist(String routeName){
        return this.RouteList.containsKey(routeName);
    }
    
    /**
     * Route에 관해 저장된 모든 정보를 반환
     * 왜 만들었는지 확인하고 필요없으면 삭제
     * @return HashMap<String, RouteClass> routeList
     */
    public HashMap<String, RouteClass> getRouteHashMap(){
        return RouteList;
    }

    /**
     * Station에 관하여 저장된 모든 정보를 반환
     * 왜 만들었는지 확인하고 필요없으면 삭제
     * @return HashMap<String, StationClass> stationList
     */
    public HashMap<String, StationClass> getStationHashMap(){
        return StationList;
    }


    /**
     * Congestion hashmap에 정보를 저장
     * @param instance : CongestinoClass instance
     */
    public void setCongestion(CongestinoClass instance){
        this.CongestionList.put(instance.stationID_routeName, instance);
    }

    /**
     * 특정 Congestion정보를 반환
     * @param stationID_routeName : 표준정류장ID_노선이름
     * @return CongestionClass : 표준정류장ID_노선이름에 해당하는 CongestionClass insatance를 반환
     */
    public CongestinoClass getCongestinoClass(String stationID_routeName){
        return this.CongestionList.get(stationID_routeName);
    }
    
    /**
     * stationID_routeName를 이용해 특정 혼잡도에 관한 class instance가 생성되어 있는지 검사한다.
     * @param stationID_routeName
     * @return boolean
     */
    public boolean isCongestionExist(String stationID_routeName){
        return this.CongestionList.containsKey(stationID_routeName);
    }

    /**
     * Congestion에 관하여 저장된 모든 정보를 반환
     * 왜 만들었는지 확인하고 필요없으면 삭제
     * @return HashMap<String, CongestionClass> CongestionList
     */
    public HashMap<String, CongestinoClass> getCongestionHashMap(){
        return CongestionList;
    }

    //hdy
    public void setRoute_Interval(String routeName, ArrayList<Integer> intervals){
        this.RouteList.get(routeName).setInterval(intervals);
    }

    //hdy
    public void setRoute_Time(String routeName, ArrayList<Integer> times){
        this.RouteList.get(routeName).setTime(times);
    }

}
