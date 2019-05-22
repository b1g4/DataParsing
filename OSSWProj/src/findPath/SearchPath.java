package findPath;

import java.util.ArrayList;

import api.SearchRoute;
import api.getStaionsByPosListClass;
import api.parsing_getStationByPosList;
import bus.BusInfoClass;

/**
 * SearchPath
 */
public class SearchPath {
    //출발점, 도착점을 좌표로 받음, 이때 좌표계는 wgs84
    private Double startX;
    private Double startY;
    private Double endX;
    private Double endY;
    /**
     * 저장공간
     * 나온 경로들을 {출발 정류소 id, 노선 id, 노선명, 도착정류소 id, 소요시간, ****혼잡도****} 순으로 2차원 삽입
     * ex) {100000384, 100100112, 721, 100000165, 23} = {광화문, 100100112노선, 721번버스, 숭인동, 23분소요, 혼잡도 3}
     * ex) {100000367 100100008 103 100000387 100000387 123000010 741 100000368 21} = 103번버스~환승~741번버스, 21분 소요, 혼잡도 7
     */
    ArrayList<ArrayList<String>> resultList=new ArrayList<>();

    /**
     * constructor
     * 길을 찾기위한 출발지와 도착지의 좌표를 설정
     * @param startX 출발점 X (wgs84)
     * @param startY 출발점 Y (wgs84)
     * @param endX 도착점 X (wgs84)
     * @param endY 도착점 Y (wgs84)
     */
    public SearchPath(Double startX,Double startY, Double endX, Double endY){
        this.startX=startX;
        this.startY=startY;
        this.endX=endX;
        this.endY=endY;
    }
   
    /**
     * 
     * @return 혼잡도를 계산해서 낮은 우선순위 별로 경로를 반환해 준다.
     */
    public ArrayList<ArrayList<String>> getPaths(){

        //출발지와 도착지 주변에 있는 정류장 목록을 반환
        parsing_getStationByPosList tmp = new parsing_getStationByPosList();
        String servicekey = "aIcQiHW9KYc8CCUdYfRbOMwvGGXVSzqB2vAHYDK6W4tYYiUd1rkhIIPi3BlOLOGNfgYEfkQpprT05jQcu4xp3g%3D%3D";
        double radius=700;
        ArrayList<getStaionsByPosListClass> start = tmp.sendUrltoAPI(servicekey,this.startX,this.startY,radius);
        ArrayList<getStaionsByPosListClass> end = tmp.sendUrltoAPI(servicekey, this.endX,this.endY,radius);

        //길찾기 api 호출
        SearchRoute searchRoute=new SearchRoute();
        for(getStaionsByPosListClass s : start) {
            for(getStaionsByPosListClass e : end) {
                searchRoute.searchRouteByAPI(s.getgpsX(), s.getgpsY(), e.getgpsX(), e.getgpsY());
                ArrayList<ArrayList<String>> paths=searchRoute.getapiRouteLists();
                //경로 목록 저장
                addPaths(paths);
            }
        }

        //위에서 부터 몇개만 밑에 보여줌
        //새로 list를 만들던지
        return resultList;
    }

    /**
     * 경로 목록들을 파싱한다. 
     * 경로 1개 당
     * -혼잡도를 계산한다. 
     * -앞뒤 정류장을 고려한 새로운 경로도 혼잡도를 계산한다.
     * -중복이 있는지 검사해서 resultList 리스트에 추가를 한다.
     * ----------환승횟수랑 시간은.........?
     * @param paths
     */
    private void addPaths(ArrayList<ArrayList<String>> paths){
        BusInfoClass busInfo=BusInfoClass.getInstance();
        GetPathCongestion getPathC=new GetPathCongestion();

       // {출발 정류소 id, 노선 id, 노선명, 도착정류소 id, 소요시간}
       for(ArrayList<String> onePath : paths){

            double time=Double.parseDouble(onePath.get(onePath.size()-1)); //소요시간=====******************
            int PathCongestion=getPathC.getTransferPathCongestion(onePath);

            String[][] BMA_stationID=get_BMA_stationID(onePath.get(0),onePath.get(2),onePath.get(onePath.size()-3),onePath.get(onePath.size()-2));
            int[][] BMA_congestionss=get_BMA_congestion(BMA_stationID[][]);
                      
            //congestionss.add(busInfo.getCongestinoClass(routeName).혼잡도.get(sta));

            
            //나온 경로들을 모두 저장(중복 제거)
            // 0 버스진행방향과 반대인 이전 정류장
            // 1 현재 정류장
            // 2 버스진행방향과 같은 이후 정류장
            for(int s=0;s<3;s++){
                for(int e=0;e<3;e++){
                    ArrayList<String> pushPath=onePath;
                    pushPath.set(0, BMA_stationID[0][s]);//출발정류장id 변경
                    pushPath.set(pushPath.size()-2,BMA_stationID[1][e]);//도착정류장id 변경

                    //혼잡도 추가
                    if(s!=1)//시작점 현재 정류장이 아닐때
                        PathCongestion=PathCongestion+BMA_congestionss[0][s];
                    if(e!=1)//도착점 현재 정류장이 아닐때
                        PathCongestion=PathCongestion+BMA_congestionss[1][e];
                    pushPath.add(String.valueOf(PathCongestion));

                    //this.resultList에 경로 추가
                    if(isExistResultList(pushPath)){
                        this.resultList.add(pushPath);
                    }
                }
            }
       }
    }  

    /**
     * 기존에 있는 경로인지 검사
     * @param path
     * @return
     */
    private Boolean isExistResultList(ArrayList<String> path){
        //this.resultList.contains(path); //==>되나?
        for(int i=0;i<this.resultList.size();i++){
            if(this.resultList.equals(path))
                return true;
        }
        return false;
    }

    /**
     * before middle after 정류장id를 반환
     * [0][0]start이전정류장, [0][1]start정류장, [0][2]start이후정류장
     * [1][0]end이전정류장, [1][1]end정류장, [1][2]end이후정류장
     * @param startStationId 시작정류장
     * @param StartRouteName 시작노선명
     * @param endStationId 종료정류장
     * @param EndRouteName 종료노선명
     * @return
     */
    private String[][] get_BMA_stationID(String startStationId, String StartRouteName, String endStationId, String EndRouteName){
        String[][] result={{"","",""},{"","",""}};

        int startStationIdx=BusInfoClass.getInstance().getRouteInfo(StartRouteName).stationList.indexOf(startStationId);
        int endStationIdx=BusInfoClass.getInstance().getRouteInfo(EndRouteName).stationList.indexOf(endStationId);

        int startStationSize=BusInfoClass.getInstance().getRouteInfo(StartRouteName).stationList.size();
        int endStationSize=BusInfoClass.getInstance().getRouteInfo(EndRouteName).stationList.size();

        //이전정류장
        result[0][0]=BusInfoClass.getInstance().getRouteInfo(StartRouteName).stationList.get((startStationIdx-1+startStationSize)%startStationSize);
        result[1][0]=BusInfoClass.getInstance().getRouteInfo(EndRouteName).stationList.get((endStationIdx-1+endStationSize)%endStationSize);

        //현재정류장
        result[0][1]=startStationId;
        result[1][1]=endStationId;

        //이후정류장
        result[0][2]=BusInfoClass.getInstance().getRouteInfo(StartRouteName).stationList.get((startStationIdx+1)%startStationSize);
        result[1][2]=BusInfoClass.getInstance().getRouteInfo(EndRouteName).stationList.get((endStationIdx+1)%endStationSize);

        return result;
    }
    
    /**
     * before middle after 혼잡도를 반환
     * [0][0]start이전, [0][1]start, [0][2]start이후,
     * [1][0]end이전, [1][1]end, [1][2]end이후
     * @param startStationId
     * @param StartRouteName
     * @param endStationId
     * @param EndRouteName
     * @return
     */
    private int[][] get_BMA_congestion(String[][] BMA_stationID){
        int[][] result={{0,0,0},{0,0,0}};
        for(int i=0;i<2;i++){
            for(int j=0;j<3;j++){
                result=BusInfoClass.getInstance.혼잡도리스트.get(스타트라우트네임).혼잡도리스트.get(BMA_stationID[i][j])
            }
        }


        return result;
    }

    /**
     * /**
     * 해당날짜의 요일을 판별 (평|토|일)
     * @param date yyyyMMdd형식의 날짜 String
     * @return 0(평일) 1(토요일) 2(일요일)
     * @TODO 공휴일을 판별할수 있는 코드를 여기에 삽입
     
    public int WhatDay(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date nDate;
        try {
            nDate = dateFormat.parse(date);
            Calendar cal = Calendar.getInstance() ;
            cal.setTime(nDate);
            int dayNum = cal.get(Calendar.DAY_OF_WEEK);
            if(dayNum == 2 | dayNum == 3|dayNum == 4|dayNum == 5 | dayNum == 6)
                return 0;//평일
            else if(dayNum==7)
                return 1;//토요일
            else if(dayNum==1)
                return 2; //일요일                
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    } 
     */
    
}