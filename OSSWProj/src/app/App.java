package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

import bus.*;

public class App {
    public static void main(String[] args) throws Exception {

        
        Runtime runtime = Runtime.getRuntime();
        int mb = 1024*1024;

        BusInfoClass busInfo = BusInfoClass.getInstance();
        String currentPath = System.getProperty("user.dir");
        
        //OSSWProj/20190124기준_서울시_버스노선정보.xls
        String fileDir1 = currentPath + "/OSSWProj/20190124기준_서울시_버스노선정보.xls";
        ParseFileClass parse1 = new ParseFileClass(fileDir1, true, true);

        //배차간격
        //OSSWProj/20190124기준_서울시_노선현황_첫차막차배차.xls
        //String fileDir1_ = currentPath + "/OSSWProj/20190124기준_서울시_노선현황_첫차막차배차.xls";
        //ParseFileClass parse1_ = new ParseFileClass(fileDir1_, 0);

        //perMonth와 perYear파일 파싱, 저장
        String fileDir2 = currentPath + "\\OSSWProj\\data\\bus";
        ParseFileClass parse2 = new ParseFileClass(fileDir2, false, true);
        

        System.out.println("Used Memory : " + (runtime.totalMemory() - runtime.freeMemory())/mb + "MB");
    }
}