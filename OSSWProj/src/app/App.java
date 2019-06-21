package app;

import java.util.ArrayList;

// import java.util.ArrayList;

// import bus.*;
// import findPath.RecommendPath;
// import findPath.SearchPath;
import subway.*;

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

        // ParseSubwayClass parseSubway = new ParseSubwayClass();

        // ArrayList<String> input = new ArrayList<String>();
        // input.add("1호선");
        // input.add("서울역");
        // input.add("3호선");
        // input.add("고속터미널");
        // FindSubwayPath searchPath = new FindSubwayPath(input);


        //System.out.println("Used Memory : " + (runtime.totalMemory() - runtime.freeMemory())/mb + "MB");
    }
}