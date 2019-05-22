package app;

import java.util.ArrayList;

import bus.*;
import findPath.RecommendPath;
import findPath.SearchPath;


public class App {
    public static void main(String[] args) throws Exception {
        Runtime runtime = Runtime.getRuntime();
        int mb = 1024*1024;

        BusInfoClass busInfo = BusInfoClass.getInstance();
        String currentPath = System.getProperty("user.dir");

        // //OSSWProj/20190124기준_서울시_버스노선정보.xls
        // String fileDir1 = currentPath + "\\OSSWProj\\20190124기준_서울시_버스노선정보_new.xls";
        // ParseFileClass parse1 = new ParseFileClass(fileDir1, true, true);
        SearchPath s=new SearchPath(); //노량진에서 중앙대
        ArrayList<ArrayList<String>> str= s.getPathsFromStations(126.9403932611921, 37.51070581104394,126.95678703483273,37.506391727320924);

        ArrayList<String> yhpath=str.get(0);

        RecommendPath recommendPath=new RecommendPath();
        //경로 사이의 모든 정류장 구하기
        //recommendPath.getStationListOnPath(yhpath);
         //가장 통계치가좋은 정류소를 고름
       // boolean hey=recommendPath.calcTotalCongestionInPath();







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

        System.out.println("Used Memory : " + (runtime.totalMemory() - runtime.freeMemory())/mb + "MB");
    }
}