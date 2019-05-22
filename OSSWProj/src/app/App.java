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
        
        //OSSWProj/20190124기준_서울시_버스노선정보.xls
        String fileDir1 = currentPath + "\\OSSWProj\\20190124기준_서울시_버스노선정보_new.xls";
        ParseFileClass parse1 = new ParseFileClass(fileDir1, true, true);

        //배차간격
        //OSSWProj/20190124기준_서울시_노선현황_첫차막차배차.xls
        String fileDir1_ = currentPath + "\\OSSWProj\\20190124기준_서울시_노선현황_첫차막차배차_new.xls";
        ParseFileClass parse1_ = new ParseFileClass(fileDir1_, 0);

        //perMonth와 perYear파일 파싱, 저장
        String fileDir2 = currentPath + "\\OSSWProj\\data\\bus";
        ParseFileClass parse2 = new ParseFileClass(fileDir2, false, true);









        SearchPath s=new SearchPath();
        ArrayList<ArrayList<String>> str= s.getPathsFromStations(126.9403932611921, 37.51070581104394,126.95678703483273,37.506391727320924);












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
    
        System.out.println("Used Memory : " + (runtime.totalMemory() - runtime.freeMemory())/mb + "MB");
    }
}