package bus;

import java.util.ArrayList;

/**
 * ParsingCongestionClass
 */
public class ParsingCongestionClass extends CongestinoClass{
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
        for(int i=0; i<this.rowNum; i++){
            String date = this.valuesInFile.get(i).get(0);
            String routeName = this.valuesInFile.get(i).get(1);
            String stationId = this.valuesInFile.get(i).get(3);
            long totalRide = Long.parseLong(this.valuesInFile.get(i).get(6)); // 승차총승객수
            long totalAlight = Long.parseLong(this.valuesInFile.get(i).get(7)); // 하차총승객수

            int day = super.WhatDay(date);
            super.setTotalDaysInfo(day);
            super.setTotalPeopleInfo(day, totalRide, totalAlight);
            
            // super에 저장된 정보를 Station Class에 저장해야함
            // Station에 노선 정보는 
        }
        return result;
    }

    public boolean parsingCongestionInfo_Year(){
        boolean result = true;
        for(int i=0; i<this.rowNum; i++){
            
        }
        return result;
    }
}