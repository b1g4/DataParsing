package findPath;

import java.util.ArrayList;

/**
 * GetPathCongestion
 */
public class GetPathCongestion {

    public GetPathCongestion(){

    }

    /**
     * 환승이 포함된 경로의 혼잡도를 계산
     * @param path
     * @return
     */
    public int getTransferPathCongestion(ArrayList<String> path){
        int congestion=0;

        int transfer=(int)path.size()/4;//타는 버스 대수 = 환승횟수+1
        for(int i=0;i<transfer;i++){
            //출발 정류소 id, 노선 id, 노선명, 도착정류소 id
            String startStstionId=path.get(i*(transfer)+0);
            //String routeId=path.get(i*(transfer)+1);
            String routeName=path.get(i*(transfer)+2);
            String endStationId=path.get(i*(transfer)+3);
            congestion=getOnePathCongestion(startStstionId,  routeName, endStationId);
        }
        return congestion;
    }
    /**
     * 환승이 없는 경로의 혼잡도를 계산
     * @param startStstionId
     * @param routeName
     * @param endStationId
     * @return
     */
    public int getOnePathCongestion(String startStstionId,String routeName,String endStationId){
        int result=0;












        

        return result;
    }
    
}