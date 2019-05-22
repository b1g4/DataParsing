package app;

import java.util.ArrayList;

import bus.*;
import findPath.RecommendPath;


public class App {
    public static void main(String[] args) throws Exception {

        Runtime runtime = Runtime.getRuntime();
        int mb = 1024*1024;

        BusInfoClass busInfo = BusInfoClass.getInstance();
        String currentPath = System.getProperty("user.dir");
        
<<<<<<< HEAD
        // //OSSWProj/20190124기준_서울시_버스노선정보.xls
        // String fileDir1 = currentPath + "\\OSSWProj\\20190124기준_서울시_버스노선정보_new.xls";
        // ParseFileClass parse1 = new ParseFileClass(fileDir1, true, true);

        // //배차간격
        // //OSSWProj/20190124기준_서울시_노선현황_첫차막차배차.xls
        // String fileDir1_ = currentPath + "\\OSSWProj\\20190124기준_서울시_노선현황_첫차막차배차_new.xls";
        // ParseFileClass parse1_ = new ParseFileClass(fileDir1_, 0);

        // //perMonth와 perYear파일 파싱, 저장
        // String fileDir2 = currentPath + "\\OSSWProj\\data\\bus";
        // ParseFileClass parse2 = new ParseFileClass(fileDir2, false, true);
        String routeDir = currentPath + "\\routecsv.csv";
        String stationDir = currentPath + "\\stationcsv.csv";
        String congestionDir = currentPath + "\\congestioncsv.csv";

        System.out.println(stationDir);

        ParseFileClass parseRoute = new ParseFileClass(routeDir, "route", true);
        ParseFileClass parseStation = new ParseFileClass(stationDir, "station", true);
        ParseFileClass parseCongestion = new ParseFileClass(congestionDir, "congestion", true);


=======
        //OSSWProj/20190124기준_서울시_버스노선정보.xls
        String fileDir1 = currentPath + "\\OSSWProj\\20190124기준_서울시_버스노선정보_new.xls";
        ParseFileClass parse1 = new ParseFileClass(fileDir1, true, true);

        //배차간격
        //OSSWProj/20190124기준_서울시_노선현황_첫차막차배차.xls
        //String fileDir1_ = currentPath + "\\OSSWProj\\20190124기준_서울시_노선현황_첫차막차배차_new.xls";
        //ParseFileClass parse1_ = new ParseFileClass(fileDir1_, 0);

        //perMonth와 perYear파일 파싱, 저장
        //String fileDir2 = currentPath + "\\OSSWProj\\data\\bus";
        //ParseFileClass parse2 = new ParseFileClass(fileDir2, false, true);

        ArrayList <String> temp = new ArrayList<String>();
        temp.add("100000367");
        temp.add("100100008");
        temp.add("103");
        temp.add("100000387");
        temp.add("100000387");
        temp.add("123000010");
        temp.add("741");
        temp.add("100000368");
        temp.add("21");
        
        RecommendPath recommendPath = new RecommendPath();
        recommendPath.getStationListOnPath(temp);
>>>>>>> 5f2a7dfa3b8a0a7f1ed5fc2abe010693e197f7f1
    
        System.out.println("Used Memory : " + (runtime.totalMemory() - runtime.freeMemory())/mb + "MB");
    }
}