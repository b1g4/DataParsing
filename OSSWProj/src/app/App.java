package app;

import bus.*;

public class App {
    public static void main(String[] args) throws Exception {
        Runtime runtime = Runtime.getRuntime();
        int mb = 1024*1024;

        BusInfoClass busInfo = BusInfoClass.getInstance();
        String currentPath = System.getProperty("user.dir");
        String fileDir = currentPath + "/data/bus/perMonth/2018/BUS_STATION_BOARDING_MONTH_201801.csv";
        ParseFileClass parse1 = new ParseFileClass(fileDir, false, true);

        System.out.println("Used Memory : " + (runtime.totalMemory() - runtime.freeMemory())/mb + "MB");
    }
}