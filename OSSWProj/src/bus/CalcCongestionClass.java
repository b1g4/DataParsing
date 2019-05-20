package bus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * CalcCongestionClass
 */
public class CalcCongestionClass {
    private Boolean status;
    private BusInfoClass busInfo = BusInfoClass.getInstance();
    private HashMap<String, CongestinoClass> congestionHashMap;
    /**
     * 특정 정류장, 특정노선, 특정요일, 시간별로 승차&하차 인원수 계산
     * <station_route명, HashMap<(0평,1토,2일),Double[][]>
     * Double[][] == 시간대별로 승차, 하차인원수 계산 ==> [0][i]승차, [1][i]하차
     */
    HashMap<String,HashMap<Integer,Double[][]>> dayPassengerNum_getOnOff=new HashMap<String,HashMap<Integer,Double[][]>>();
    /**
     * 특정 정류장, 특정노선, 특정요일, 시간별(24시간)로 재차인원수 계산
     * <station_route명, HashMap<(0평,1토,2일),Double[24]>
     */
    HashMap<String,HashMap<Integer,Double[]>> passengerNum=new HashMap<String,HashMap<Integer,Double[]>>();
    /**
     * 특정 정류장, 특정노선, 특정요일, 시간별(24시간)로 혼잡도 계산
     * -1 : 앉을 수 있음
     * 0 : 좌석은 찼는데 손잡이는 남음
     * 1 : 손잡이 넘음
     * <station_route명, HashMap<(0평,1토,2일),Double[24]>
     */
    HashMap<String,HashMap<Integer,int[]>> finalCongestion=new HashMap<String,HashMap<Integer,int[]>>();
        
    /**
     * constructor for calculate congestion
     */
    public CalcCongestionClass(boolean status){
        if(status){
            this.congestionHashMap=busInfo.getCongestionHashMap();
            if(calc_getOnOff()){
                if(calc_Passenger()){
                    this.status=calc_congestion();
                }else{
                    System.out.println("CalcCongestionClass : 재차인원 계산 실패");
                }
            }else{
                System.out.println("CalcCongestionClass : 승,하차 인원 계산 실패");
            }
        }
    }

    public HashMap<String,HashMap<Integer,Double[]>> getpassengerNum(){
        return this.passengerNum;
    }
    public HashMap<String,HashMap<Integer,int[]>> getfinalCongestion(){
        return this.finalCongestion;
    }


    /**
     * 특정 정류장, 특정 노선, 특정 요일, 시간별로 승차, 하차 인원수 계산
     * @return 수행성공여부
     */
    private boolean calc_getOnOff(){
        boolean status=true;
        Iterator<String> iter=this.congestionHashMap.keySet().iterator();
        while(iter.hasNext()){
            String stationID_routeName=(String)iter.next();

            if(stationID_routeName.equals("103000038___302")){
                System.out.println("test== 103000038___302");
            }


            CongestinoClass tmp=this.congestionHashMap.get(stationID_routeName);

            //0평일, 1토요일, 2일요일
            HashMap<Integer,Double[][]> days=new HashMap<Integer,Double[][]>();
            for(int day=0;day<3;day++){
                //승차, 하차인원수 계산 ==> [0][i]승차, [1][i]하차
                Double[][] gettingOn_Off=tmp.calcGettingOnAndOff(day);
                days.put(day,gettingOn_Off);
            }
            dayPassengerNum_getOnOff.put(stationID_routeName, days);
        }
        return true;
    }

    /**
     * 특정 정류장, 특정 노선 특정 요일 시간별로(24시간*60분) 재차인원수 계산
     * @return
     */
    private boolean calc_Passenger(){
        boolean status=true;

        Iterator<String> iter=this.congestionHashMap.keySet().iterator();
        while(iter.hasNext()){
            String stationID_routeName=(String)iter.next();

            if(busInfo.isRouteExist(stationID_routeName.substring(stationID_routeName.indexOf("___")+3))){

                if(stationID_routeName.equals("103000038___302")){
                    System.out.println("test== 103000038___302");
                }

                //0평일, 1토요일, 2일요일별로 저장
                HashMap<Integer,Double[]> days=new HashMap<Integer,Double[]>();
                for(int day=0;day<3;day++){

                    // 이전정류장의 버스재차인원 + 승차인원 - 하차인원
                    Double[] hours=new Double[24];
                    for(int i=0;i<24;i++){
                            hours[i]=calc_Passenger_subFunction(day,i,stationID_routeName);   
                    }
                    days.put(day,hours);
                }
                passengerNum.put(stationID_routeName,days);
                if(stationID_routeName.equals("119900009___동작01")){
                    System.out.println("test== 119900009___동작01");
                    for(int i=0;i<24;i++){
                        System.out.println("test== 119900009___동작01 : 평일 값"+days.get(0)[i]);
                    }
                }
                //System.out.println("test==calc_Passenger() : stationID_routeName="+stationID_routeName );

            }else{
                System.out.println("test==calc_Passenger() : stationID_routeName="+stationID_routeName+"이 routelist에 없음");
            }
        }
        return status;
    }


 
    private Double calc_Passenger_subFunction(int day,int nowHour, String stationID_routeName){

        Double result=0.0;

        String station_route=stationID_routeName;
        String busStationId=station_route.substring(0,station_route.indexOf("___"));
        String busRouteName=station_route.substring(station_route.indexOf("___")+3);

        int busMinute=nowHour*60+1440;
        boolean status=true;

        //busMinute에 현재 정류장busS_R의 값이 0이면 끝!
        while(status){
            if(this.congestionHashMap.containsKey(station_route)){
                //항상 사람이 탈 경우를 대비해 첫차시간을 고려함
                if(busMinute >  getFirstBusTime(busRouteName, busStationId)){
                    //busMinute 시간에 승하차 인원이 없다면 0 반환
                    //예) busMinute=3시7분 ==> 시간대=3-4시 ==> 승차=0,하차=0이면 승객이 없으므로 False
                    if(this.congestionHashMap.get(station_route).IsPassengerExist((int)(busMinute%1440)/60)){
                        
                        if(result==31.849249)
                            System.out.println("test==");
                        
                        Double getonn=getOnPassenger(day,(int)busMinute/60,busMinute%60,station_route);
                        Double getoff=getOffPassenger(day,(int)busMinute/60,busMinute%60,station_route);
                        result=result+getonn-getoff;
                    
                        //그다음 정류장을 찾아서....
                        String before_stationID=getBeforeStation(busRouteName,busStationId);//a->b->c->.....y->z->a->b...
                        int timeInterval=getTimeInterval(busRouteName,busStationId,before_stationID);
                        
                        busMinute=busMinute-timeInterval;
                        busStationId=before_stationID;
                        station_route=busStationId+"___"+busRouteName;
                    }else{
                        //System.out.println("test==calc_Passenger_subFunction() : congestionHashMap에 "+station_route+"에 "+busMinute+"분에 승객이 없음");
                        status=false;
                    }
                }else{
                    //System.out.println("test==calc_Passenger_subFunction() : "+station_route+" 가 첫차시간을 지남");
                    status=false;
                }
            }else{
                System.out.println("test==calc_Passenger_subFunction() : congestionHashMap에 "+station_route+" 가 없음");
                status=false;
            }
        }
        return result;
    }


    //hdy
    private int getFirstBusTime(String routeName, String stationID){

        //노선별로 첫차시간을 구해야 함!!!
        //Integer time=busInfo.getRouteInfo(routeName).getTime().get(0);
        //int val=time.intValue();
        
       // return val; //4시라고 생ㅇ각했을때!

       return 240;

    }

    /**
     * 노선에서 이전 정류장의 stationID를 반환
     * 시점의 이전 정류장은 종점을 말한다 ==> % 사용
     * @param routeName 
     * @param stationID 
     */
    private String getBeforeStation(String routeName, String stationID){

        String result="";
        RouteClass route=busInfo.getRouteInfo(routeName);
        int idx=route.stationList.indexOf(stationID);
        result=route.stationList.get((idx+route.stationList.size()-1)%route.stationList.size());
        while(!this.congestionHashMap.containsKey(result+"___"+routeName)){
            idx--;
            result=route.stationList.get((idx+route.stationList.size()-1)%route.stationList.size());
        }
     return result;
    }

    /**
     * stationID정류장과 before_stationID정류장 사이의 시간간격을 반환
     * @param routeName
     * @param stationID
     * @param before_stationID
     * @return
     */
    private int getTimeInterval(String routeName, String stationID,String before_stationID){
        int result=10;//여기도 임의로 테스트용으로 5분간격이라고 한거임!

        //희수 코드와 합쳐야 함

        return result;
    }

    
    /**
     * 원하는 시간대를 쪼개 임의로 hh시 mm분에 대한 승차인원 계산
     * 수식 검사 후 수정 여부 판단.
     * @param day : 평일, 토요일, 일요일 중 입력
     * @param hour : 0~23시
     * @param minute : 0~59분
     * @return hh시 mm분일 때 버스의 승차인원
     */
    private Double getOnPassenger(int day, int hour, int minute,String stationID_routeName){
        if(!this.dayPassengerNum_getOnOff.containsKey(stationID_routeName)){
            System.out.println("getOnPassenger : "+stationID_routeName+" not exist");
            return 0.0;
        }        

        Double a=this.dayPassengerNum_getOnOff.get(stationID_routeName).get(day)[0][(hour+1)%24];
        Double b=this.dayPassengerNum_getOnOff.get(stationID_routeName).get(day)[0][(hour)%24];
        Double c=(a-b)*minute/60+b;
        return c;
        //return (this.dayPassengerNum_getOnOff.get(stationID_routeName).get(day)[0][(hour+1)%24]
        //        - this.dayPassengerNum_getOnOff.get(stationID_routeName).get(day)[0][(hour)%24])
        //        * (minute/60) 
        //        +this.dayPassengerNum_getOnOff.get(stationID_routeName).get(day)[0][(hour)%24];
     } 

     /**
     * 원하는 시간대를 쪼개 임의로 hh시 mm분에 대한 하차인원 계산
     * 수식 검사 후 수정 여부 판단.
     * @param day : 평일, 토요일, 일요일 중 입력
     * @param hour : 0~23시
     * @param minute : 0~59분
     * @return hh시 mm분일 때 버스의 하차인원
     */
    private Double getOffPassenger(int day, int hour, int minute,String stationID_routeName){
        if(!this.dayPassengerNum_getOnOff.containsKey(stationID_routeName)){
            System.out.println("getOffPassenger : "+stationID_routeName+" not exist");
            return 0.0;
        }  

        HashMap<Integer,Double[][]> hi=this.dayPassengerNum_getOnOff.get(stationID_routeName);
        Double[][] hello=hi.get(day);
        Double a=hello[1][(hour+1)%24];
        Double b=hello[1][(hour)%24];
        Double c=(a-b)*minute/60+b;
        return c;
         //return (this.dayPassengerNum_getOnOff.get(stationID_routeName).get(day)[1][(hour+1)%24]
         //       - this.dayPassengerNum_getOnOff.get(stationID_routeName).get(day)[1][(hour)%24])
         //       * (minute/60) 
         //       +this.dayPassengerNum_getOnOff.get(stationID_routeName).get(day)[1][(hour)%24];
     } 

    private boolean calc_congestion(){
        boolean status=true;
        Iterator<String> iter=this.congestionHashMap.keySet().iterator();
        while(iter.hasNext()){
            String stationID_routeName=(String)iter.next();
            if(busInfo.isRouteExist(stationID_routeName.substring(stationID_routeName.indexOf("___")+3))){
                ArrayList<Integer> chair_handle=getBusType(stationID_routeName);

                //0평일, 1토요일, 2일요일별로 저장
                HashMap<Integer,int[]> days=new HashMap<Integer,int[]>();
                for(int day=0;day<3;day++){
    
                    // 24시간*60분 별로 저장
                   int[] minutes=new int[24];
                    for(int i=0;i<24;i++){
                        double num=passengerNum.get(stationID_routeName).get(day)[i];
                        //좌석에 앉을 수 있으면 -1
                        //손잡이를 잡을 수 있으면 0
                        //둘다 불가능이면 1
                        if(num < chair_handle.get(0)){
                            minutes[i]=-1;
                        }else if(num < chair_handle.get(0)+chair_handle.get(1)){
                            minutes[i]=0;
                        }else{
                            minutes[i]=1;
                        }
                    }
                    days.put(day,minutes);
                }
                finalCongestion.put(stationID_routeName,days);
            }else{ 
                System.out.println("test==calc_congestion() : stationID_routeName="+stationID_routeName+"이 routelist에 없음");
            }            
        }
        return status;
    }

    /**
     * 
     * @param routeName
     * @return 버스 종류 
     * 광역버스(빨강) - 숫자4자리, 9로 시작                  45/0 ex)구글링+뇌피셜
     * 지선버스(초록) - 숫자4자리           좌석20 손잡이17  ex)5511번 버스 조사
     * 간선버스(파랑) - 숫자3자리           좌석24 손잡이24  ex)151번 버스 조사
     * 순환버스(노랑) - 숫자2자리                           20/17 ex)구글링+뇌피셜
     * 마을버스         한글로 시작+숫자    좌석20 손잡이17  ex)동작01번 버스 조사
     * N버스            N으로 시작+숫자                     24/24 ex)구글링+뇌피셜
     */
    private ArrayList<Integer> getBusType(String stationID_routeName){
        String routeName=stationID_routeName.substring(stationID_routeName.indexOf("___")+3);
        
        Pattern pD4_9=Pattern.compile("^9[0-9]{3}");
        Pattern pD4=Pattern.compile("^[0-9]{4}");
        Pattern pD3=Pattern.compile("^[0-9]{3}");
        Pattern pD2=Pattern.compile("^[0-9]{2}");
        Pattern pHD=Pattern.compile("^[가-힣]*[0-9]{2}");
        Pattern pN=Pattern.compile("^N");

        ArrayList<Integer> result=new ArrayList<>();
        if(pD4_9.matcher(routeName).find()){
            System.out.println("test== "+stationID_routeName+" 광역");
            result.add(45);
            result.add(0);

        }else if(pD4.matcher(routeName).find()){
            System.out.println("test== "+stationID_routeName+" 지선");
            result.add(20);
            result.add(17);

        }else if(pD3.matcher(routeName).find()){
            System.out.println("test== "+stationID_routeName+" 간선");
            result.add(24);
            result.add(24);

        }else if(pD2.matcher(routeName).find()){
            System.out.println("test== "+stationID_routeName+" 순환");
            result.add(20);
            result.add(17);

        }else if(pHD.matcher(routeName).find()){
            System.out.println("test== "+stationID_routeName+" 마을");
            result.add(20);
            result.add(17);

        }else if(pN.matcher(routeName).find()){
            System.out.println("test== "+stationID_routeName+" N심야");
            result.add(24);
            result.add(24);

        }else{
            System.out.println("test== "+stationID_routeName+" 알수없는 형식의 버스번호입니다.");
            result.add(-1);
            result.add(-1);
        }
        return result;
    }
}