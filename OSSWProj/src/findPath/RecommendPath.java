package findPath;

import bus.BusInfoClass;
import bus.RouteClass;

import java.util.ArrayList;

public class RecommendPath {

    private int transferNum = 0;
    private ArrayList<String> stationListOnPath = new ArrayList<String>();
    private BusInfoClass businfo = BusInfoClass.getInstance();

    public RecommendPath(){}

    public void getStationListOnPath(ArrayList<String> selectedPath){
        
        this.transferNum = (selectedPath.size() / 4) - 1 ;

        String startSta = "";
        String endSta = "";
        String routeName = "";

        for(int k = 0 ; k <= transferNum ; k++){
            
            startSta = selectedPath.get(4 * k);
            endSta = selectedPath.get(4 * k + 3);
            routeName = selectedPath.get(4 * k + 2);

            RouteClass rta = businfo.getRouteInfo(routeName);
        
            for(int i = 0 ; i < rta.getStationList().size() ; i++){
                if(rta.getStationList().get(i).equals(startSta)){
                    for(int j = i; (!rta.getStationList().get(j).equals(endSta)) ; j++){
                        stationListOnPath.add(rta.getStationList().get(j));
                    }
                }    
            }
        }

        stationListOnPath.add(endSta);

        //확인용 코드, 삭제 예정
        for(int i = 0 ; i < stationListOnPath.size() ; i++){
            System.out.println(stationListOnPath.get(i));
        }
    }

    public void calcTotalCongestionInPath(){

        String minCongestionStop = "";

        //ArrayList<String> tempRouteList = new ArrayList<String>;


        //for(int i = 0 ; i < stationListOnPath.size() ; i++){
        //    businfo.getStationInfo(stationListOnPath.get(i));
        //}




    }
}