package bus;

import java.util.ArrayList;

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
    

    public boolean parsingCongestionInfo_Month(){
        boolean result = true;

        // 모든 정보를 congestion class에 존재하는 변수에 저장
        for(int i=0; i<this.rowNum; i++){
            String date = this.valuesInFile.get(i).get(0);
            String routeName = this.valuesInFile.get(i).get(1);
            String stationId = this.valuesInFile.get(i).get(3);
            Double totalRide = Double.parseDouble(this.valuesInFile.get(i).get(6)); // 승차총승객수
            Double totalAlight = Double.parseDouble(this.valuesInFile.get(i).get(7)); // 하차총승객수

           // System.out.println("date="+date+" routeName="+routeName+" stationId="+stationId+" totalRide="+" totalAlight="+totalAlight);
            String stationID_routeName=stationId+"___"+routeName;
            if(busInfo.isCongestionExist(stationID_routeName)){
                //20180101과 20180102는 중복이 일어남 => 이미 있으면 더하기
               //CongestinoClass temp=busInfo.getCongestinoClass(stationID_routeName);
                int day = busInfo.getCongestinoClass(stationID_routeName).WhatDay(date);
                busInfo.getCongestinoClass(stationID_routeName).setTotalDaysInfo(day);
                busInfo.getCongestinoClass(stationID_routeName).setTotalPeopleInfo(day, totalRide, totalAlight);
                //busInfo.setCongestion(temp);
            }else{ 
                //없으면 새로 추가하면 됨
                CongestinoClass temp=new CongestinoClass(stationID_routeName);
                int day = temp.WhatDay(date);
                temp.setTotalDaysInfo(day);
                temp.setTotalPeopleInfo(day, totalRide, totalAlight);
                busInfo.setCongestion(temp);
            }
        }
        System.out.println("parsinge");

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
            String stationID_routeName=stationId+"___"+routeName;
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