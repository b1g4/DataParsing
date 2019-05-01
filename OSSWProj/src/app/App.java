package app;

import bus.*;

public class App {
    public static void main(String[] args) throws Exception {
        Runtime runtime = Runtime.getRuntime();
        int mb = 1024*1024;

        BusInfoClass busInfo = BusInfoClass.getInstance();
        String currentPath = System.getProperty("user.dir");
        String fileDir = currentPath + "/data/bus/20190124기준_서울시_버스노선정보.xls";
        //System.out.println(System.getProperty("user.dir"));
        ParseFileClass parse1 = new ParseFileClass(fileDir, false, true);

        System.out.println("Used Memory : " + (runtime.totalMemory() - runtime.freeMemory())/mb + "MB");
    }
}