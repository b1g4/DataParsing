package app;

import bus.*;

public class App {
    public static void main(String[] args) throws Exception {
        Runtime runtime = Runtime.getRuntime();
        int mb = 1024*1024;

        BusInfoClass busInfo = BusInfoClass.getInstance();
        String currentPath = System.getProperty("user.dir");
        //String fileDir = currentPath + "/data/bus/perMonth/2018/BUS_STATION_BOARDING_MONTH_201801.csv";
        //ParseFileClass parse1 = new ParseFileClass(fileDir, false, true);

        String fileDir2 = currentPath + "/team-project-team5/OSSWProj/20190124기준_서울시_버스노선정보.xls";
        ParseFileClass parse2 = new ParseFileClass(fileDir2, true, true);


        System.out.println("Used Memory : " + (runtime.totalMemory() - runtime.freeMemory())/mb + "MB");
    }
}