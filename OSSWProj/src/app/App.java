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

        ParseSubwayClass parseSubway = new ParseSubwayClass();
        ArrayList<String> input = new ArrayList<String>();
        input.add("1호선");
        input.add("서울역");
        input.add("3호선");
        input.add("고속터미널");
        FindSubwayPath searchPath = new FindSubwayPath(input);

        System.out.println("Used Memory : " + (runtime.totalMemory() - runtime.freeMemory())/mb + "MB");
    }
}