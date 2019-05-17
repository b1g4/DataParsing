package bus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * CongestinoClass 혼잡도 정보를 계산하고 해당 정보를 StationClass에 저장한다.
 */
public class CongestinoClass {

    public String stationID_routeName;
    /**
     * 한달중에 평일이 며칠이 있는지, 토요일이 며칠이 있는지, 일요일이 며칠이 있는지 세어서 저장
     * 0:평일날짜수, 1:토요일날짜수, 2:일요일날짜수
     */
    private int totalDays[] = new int[3];

    /**
     * 한달중에 평일|토요일|일요일에 사람이 총 몇명이 타고 몇명이 하차했는지 저장
     * [0][0] 평일총승차       [0][1] 평일총하차
     * [1][0] 토요일총승차     [1][1] 토요일총하차
     * [2][0] 일요일총승차     [2][1] 일요일총하차
     */
    private Double totalPeople[][]=new Double[3][2];

    /**
     * 특정시각에 사람이 총 몇명이 타고 몇명이 하차했는지 저장
     * [0][0] 0-1시 총승차  [0][1] 1-2시 총승차  [0][2] 2-3시 총승차  [0][3] 3-4시 총승차   .......
     * [1][0] 0-1시 총하차  [1][1] 1-2시 총하차  [1][2] 2-3시 총하차  [1][3] 3-4시 총하차   .......
     */
    private Double totalByTime[][]=new Double[2][24];

    

    /**
     * 평일0-23시, 토요일 0-23시, 일요일 0-23시 순으로 저장 
     */ 
    private HashMap<String, ArrayList<Double>> congestion = new HashMap<String, ArrayList<Double>>();

    /**
     * default constructor
     */
    public CongestinoClass(){
        this.initCongestion();
    }

    /**
     * congestion변수 초기화
     */
    private void initCongestion(){
        ArrayList<Double> tmpList = new ArrayList<Double>(24);
        congestion.put("평일", tmpList);
        congestion.put("토요일", tmpList);
        congestion.put("일요일", tmpList);
    }

    /**
     * 원하는 시간대를 쪼개 임의로 hh시 mm분에 대한 혼잡도 계산
     * 수식 검사 후 수정 여부 판단.
     * @param day : 평일, 토요일, 일요일 중 입력
     * @param hour : 0~23시
     * @param minute : 0~59분
     * @return hh시 mm분일 때 버스의 혼잡도
     */
    public Double getCongestion(String day, int hour, int minute){
        return (this.congestion.get(day).get(hour+1) - this.congestion.get(day).get(hour))
            * (minute / 60) + this.congestion.get(day).get(hour);
    }  

    /**
     * 재차인원 계산
     * 승차-하차
     */
    public void calcPassengerNum(){
        ArrayList<Double> tmpList = new ArrayList<Double>();
        for(int i =0; i<24; i++){
            tmpList.add(this.totalByTime[0][i] - this.totalByTime[1][i]);
        }
    }

    /**
     * 
     * @param day
     */
    public void setTotalDaysInfo(int day){
        this.totalDays[day] += 1;
    }

    /**
     * 
     * @param day
     * @param ride
     * @param alight
     */
    public void setTotalPeopleInfo(int day, Double ride, Double alight){
        this.totalPeople[day][0] += ride;
        this.totalPeople[day][1] += alight;
    }

    public void setTotalByTimeInfo(int hour, Double ride, Double alight){
        this.totalByTime[0][hour] += ride;
        this.totalByTime[1][hour] += alight;
    }

    /**
     * 해당날짜의 요일을 판별 (평|토|일)
     * @param date yyyyMMdd형식의 날짜 String
     * @return 0(평일) 1(토요일) 2(일요일)
     * @TODO 공휴일을 판별할수 있는 코드를 여기에 삽입
     */
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


    /**
     * 평,토,일 별 시간당 승차,하차인원수를 구한다.
     * @param day 0평일, 1토요일, 2일요일
     */
    public Double[][] calcGettingOnAndOff(int day){
        Double[][] GettingOnAndOff=new Double[2][24];
        //승하차 인원 계산
        for(int time=0;time<24;time++){ //0-23시 
            //승차
            GettingOnAndOff[0][time]=totalByTime[0][time] * (totalPeople[day][0]/totalDays[day]) / (totalPeople[0][0]+totalPeople[1][0]+totalPeople[2][0]);
            //하차
            GettingOnAndOff[1][time]=totalByTime[1][time] * (totalPeople[day][1]/totalDays[day]) / (totalPeople[0][1]+totalPeople[1][1]+totalPeople[2][1]);
        }
        return GettingOnAndOff;        
    }

}