package app;

import java.util.ArrayList;

import bus.*;
import findPath.RecommendPath;
import findPath.SearchPath;


public class App {
    public static void main(String[] args) throws Exception {
        Runtime runtime = Runtime.getRuntime();
        int mb = 1024*1024;







        //routecsv.csv, stationcsv.csv, congestioncsv.csv 를 만드는 코드
        /*
        BusInfoClass busInfo = BusInfoClass.getInstance();
        String currentPath = System.getProperty("user.dir");

        String fileDir1 = currentPath + "\\OSSWProj\\20190124기준_서울시_버스노선정보_new.xls";
        ParseFileClass parse1 = new ParseFileClass(fileDir1, true, true);

        String fileDir2 = currentPath + "\\OSSWProj\\20190124기준_서울시_노선현황_첫차막차배차_new.xls";
        ParseFileClass parse2 = new ParseFileClass(fileDir2, 0);

        String fileDir3 = currentPath + "\\OSSWProj\\data\\bus";
        ParseFileClass parse3= new ParseFileClass(fileDir3, false, true);
        */






       //routecsv.csv, stationcsv.csv, congestioncsv.csv 를 읽는 코드
       /*
       BusInfoClass busInfo = BusInfoClass.getInstance();
       String currentPath = System.getProperty("user.dir");

       String routecsvDir = currentPath + "\\routecsv.csv";
       ParseFileClass parse_route= new ParseFileClass(routecsvDir,"route", true);

       String stationcsvDir= currentPath+"\\stationcsv.csv";
       ParseFileClass parse_station= new ParseFileClass(stationcsvDir,"station", true);

       String congestioncsvDir= currentPath+"\\congestioncsv.csv";
       ParseFileClass parse_congestion= new ParseFileClass(congestioncsvDir,"congestion", true);
        */




        //파일3개를 미리 읽고 정보를 저장한 상태로 client를 기다려야 한다
        TCPServer tcpServer=new TCPServer();
        tcpServer.setTCPSocket();

       
       
       



       //test code
/*

       System.out.println("\n====================================================================");
       System.out.println("================노량진에서 중앙대까지의 경로를 찾기 시작===============");
       System.out.println("====================================================================\n");
       SearchPath s=new SearchPath(); //노량진에서 중앙대
       ArrayList<ArrayList<String>> str= s.getPathsFromStations(126.9403932611921, 37.51070581104394,126.95678703483273,37.506391727320924);


       System.out.println("\n====================================================================");
       System.out.println("=====================혼잡도를 바탕으로 추천한 경로===================");
       System.out.println("====================================================================\n");
       for(int i=0;i<str.size();i++){
           ArrayList<String> substr=str.get(i);
           for(int j=0;j<substr.size();j++){
               System.out.print(substr.get(j)+"   ");
           }
           System.out.println("");
       }

       RecommendPath recommendPath=new RecommendPath();

       System.out.println("\n====================================================================");
       System.out.println("=====================경로 사이의 모든 정류장 구하기===================");
       System.out.println("====================================================================\n");
       recommendPath.getStationListOnPath(str.get(0));//경로 사이의 모든 정류장 구하기

       System.out.println("\n====================================================================");
       System.out.println("=================가장 통계혼잡도가 좋은 정류소를 고름==================");
       System.out.println("====================================================================\n");
       boolean hey=recommendPath.calcTotalCongestionInPath(); //가장 통계치가좋은 정류소를 고름

       System.out.println("\n====================================================================");
       System.out.println("===========================환승을 제안할 경로들=========================");
       System.out.println("====================================================================\n");
       */

        //System.out.println("Used Memory : " + (runtime.totalMemory() - runtime.freeMemory())/mb + "MB");
    }
}