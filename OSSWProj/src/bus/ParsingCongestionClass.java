package bus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * ParsingCongestionClass
 */
public class ParsingCongestionClass{
    private ArrayList<ArrayList<String>> valuesInFile = new ArrayList<ArrayList<String>>();
    private int rowNum;
    private int columnNum;
    private BusInfoClass busInfo = BusInfoClass.getInstance();
    
    /**
     * constructor
     */
    public ParsingCongestionClass(ArrayList<ArrayList<String>> valuesInFile){
        this.valuesInFile = valuesInFile;
        this.rowNum = this.valuesInFile.size();
        this.columnNum = this.valuesInFile.get(0).size();
    }
    /**
     * constructor for calculate congestion
     */
    public ParsingCongestionClass(boolean status){
        if(status){
            calculateCongetionInfo();
        }
    }

    /**
     * busInfo의 CongestionList를 돌면서 재차인원과 혼잡도를 구해서 StationList에 값을 저장
     */
    private void calculateCongetionInfo(){
        HashMap<String, CongestinoClass> congestionHashMap=busInfo.getCongestionHashMap();

        //특정 정류장, 특정 노선, 특정 요일, 시간별로 승차, 하차 인원수 계산
        //<station_route , HashMap>
        //HashMap == <(평,토,일) , Double[][]>
        //Double[][] == 시간대별로 승차, 하차인원수 계산 ==> [0][i]승차, [1][i]하차
        HashMap<String,HashMap<Integer,Double[][]>> day_getOn_getOff=new HashMap<String,HashMap<Integer,Double[][]>>();
        Iterator<String> iter=congestionHashMap.keySet().iterator();
        while(iter.hasNext()){
            String stationID_routeName=(String)iter.next();
            CongestinoClass tmp=congestionHashMap.get(stationID_routeName);

            //0평일, 1토요일, 2일요일
            HashMap<Integer,Double[][]> days=new HashMap<Integer,Double[][]>();
            for(int day=0;day<3;day++){
                //승차, 하차인원수 계산 ==> [0][i]승차, [1][i]하차
                Double[][] gettingOn_Off=tmp.calcGettingOnAndOff(day);
                days.put(day,gettingOn_Off);
            }
            day_getOn_getOff.put(stationID_routeName, days);
        }

        //특정 정류장, 특정 노선 - 특정 요일 - 시간별(24시간*60분)로  재차인원 계산
        HashMap<String,HashMap<Integer,Double[]>> passengerNum=new HashMap<String,HashMap<Integer,Double[]>>();
        Iterator<String> iter2=congestionHashMap.keySet().iterator();
        while(iter2.hasNext()){
            String stationID_routeName=(String)iter2.next();
            CongestinoClass tmp=congestionHashMap.get(stationID_routeName);

            //0평일, 1토요일, 2일요일별로 저장
            HashMap<Integer,Double[]> days=new HashMap<Integer,Double[]>();
            for(int day=0;day<3;day++){
                // 이전정류장의 버스재차인원 + 승차인원 - 하차인원
                Double[] minutes=new Double[1440];
                for(int i=0;i<1440;i++){
                    double before_passenger=getBeforePassengerNum(congestionHashMap,i);
                    minutes[i]=before_passenger 
                                +day_getOn_getOff.get(stationID_routeName).get(day)[0][i]
                                -day_getOn_getOff.get(stationID_routeName).get(day)[1][i];
                }
                days.put(day,minutes);
            }
            passengerNum.put(stationID_routeName,days);
        }
    }

    //재차인원 구하는 거어어어어어 재귀함수 써야하하ㅏ아아아맘
    private Double getBeforePassengerNum(HashMap<String, CongestinoClass> congestionHashMap, int minute){
        Double value=0.0;









        
        return value;
    }

    public boolean parsingCongestionInfo_Month(){
        boolean result = true;

        // 모든 정보를 congestion class에 존재하는 변수에 저장
        for(int i=0; i<this.rowNum; i++){
            String date = this.valuesInFile.get(i).get(0);
            String routeName = this.valuesInFile.get(i).get(1);
            String stationId = this.valuesInFile.get(i).get(3);
            Double totalRide = Double.parseDouble(this.valuesInFile.get(i).get(6)); // 승차총승객수
            Double totalAlight = Double.parseDouble(this.valuesInFile.get(i).get(7)); // 하차총승객수

            String stationID_routeName=stationId+routeName;
            if(busInfo.isCongestionExist(stationID_routeName)){
                //이미 있으면 그 해당 노선에다가 더하기/...?
                //201801, 201802 등등 파일을 읽으면 중복이 일어날거임
                //이거처리는 나중에
            }else{
                //없으면 새로 추가하면 됨
                CongestinoClass temp=new CongestinoClass();
                int day = temp.WhatDay(date);
                temp.setTotalDaysInfo(day);
                temp.setTotalPeopleInfo(day, totalRide, totalAlight);
            }
        }
        // congestion class에 존재하는 값들을 이용해 혼잡도 계산==>year읽어야 가능한데..?
        return result;
    }

    public boolean parsingCongestionInfo_Year(){
        boolean result = true;
        for(int i=0; i<this.rowNum; i++){
            String routeName = this.valuesInFile.get(i).get(2);
            String stationId = this.valuesInFile.get(i).get(4);
            Double timeRide[] = new Double[24];
            Double timeAlight[] = new Double[24];

            for(int h=0; h<24; h++){
                timeRide[h] = Double.parseDouble(this.valuesInFile.get(i).get(8+2*h));
                timeAlight[h] = Double.parseDouble(this.valuesInFile.get(i).get(9+2*h));
            }
            String stationID_routeName=stationId+routeName;
            if(busInfo.isCongestionExist(stationID_routeName)){
                //정보 추가후 congestionList에 다시 삽입
                CongestinoClass tmp=busInfo.getCongestinoClass(stationID_routeName);
                for(int h=0; h<24; h++){
                    tmp.setTotalByTimeInfo(h, timeRide[h], timeAlight[h]);
                }
                busInfo.setCongestion(tmp);
            }else{
                System.out.println("perMonth에는 없는 노선&정류장에 대한 정보가 perYear에는 있다!! 파일오류발생");
                return false;
            }
        }
        return result;
    }
}