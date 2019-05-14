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
            long totalRide = Long.parseLong(this.valuesInFile.get(i).get(6)); // 승차총승객수
            long totalAlight = Long.parseLong(this.valuesInFile.get(i).get(7)); // 하차총승객수

            int day = super.WhatDay(date);
            super.setTotalDaysInfo(day);
            super.setTotalPeopleInfo(day, totalRide, totalAlight);
        }
        
        // congestion class에 존재하는 값들을 이용해 혼잡도 계산
        return result;
    }

    public boolean parsingCongestionInfo_Year(){
        boolean result = true;
        for(int i=0; i<this.rowNum; i++){
            String routeName = this.valuesInFile.get(i).get(2);
            String stationId = this.valuesInFile.get(i).get(4);
            long timeRide[] = new long[24];
            long timeAlight[] = new long[24];

            for(int h=0; h<24; h++){
                timeRide[h] = Long.parseLong(this.valuesInFile.get(i).get(8+2*h));
                timeAlight[h] = Long.parseLong(this.valuesInFile.get(i).get(9+2*h));
            }
            for(int h=0; h<24; h++){
                super.setTotalByTimeInfo(h, timeRide[h], timeAlight[h]);
            }
        }
        return result;
    }
}