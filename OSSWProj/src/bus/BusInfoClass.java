package bus;

import java.util.Collection;
import java.util.HashMap;
/**
 * 싱글톤 객체 
 * 모든 정보는 프로그램 실행부터 끝까지 한번 읽어들이고 수정이 되면 안되고 
 * 항상 접근이 가능해야 한다. 
 */
public class BusInfoClass{
    private static BusInfoClass busInfo = new BusInfoClass();
    // 전체 경로정보 <RouteID, RouteClass Instance> 형식으로 저장
    private HashMap<String, RouteClass> RouteList = new HashMap<String, RouteClass>();
    // 전체 정류장 정보 <StationID, StationClass Instance> 형식으로 저장
    private HashMap<String, StationClass> StationList = new HashMap<String, StationClass>();

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
        this.RouteList.put(instance.routeId, instance);
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
     * @param routeId : 표준 노선 ID, 9자리 숫자로 이루어짐
     * @return RouteClass : routeID에 해당하는 RouteClass instance를 반환
     */
    public RouteClass getRouteInfo(String routeId){
        return this.RouteList.get(routeId);
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
     * @param routeId
     * @return boolean
     */
    public boolean isRouteExist(String routeId){
        return this.RouteList.containsKey(routeId);
    }
    /*
    public String getRouteIdByRouteName(String RouteName){
        Collection<RouteClass> valueList =  this.RouteList.values();
    }
    */
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
    
}
