package app;

// import java.util.ArrayList;

// import bus.*;
// import findPath.RecommendPath;
// import findPath.SearchPath;
import subway.*;

public class App {
    public static void main(String[] args) throws Exception {
        Runtime runtime = Runtime.getRuntime();
        int mb = 1024*1024;

        ParseSubwayClass parseSubway = new ParseSubwayClass();

        System.out.println("Used Memory : " + (runtime.totalMemory() - runtime.freeMemory())/mb + "MB");
    }
}