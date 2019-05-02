package app;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;

import bus.RouteClass;
import bus.StationClass;

//csv파일 만들기 ,미완성
public class WriteCsvClass {

    public WriteCsvClass(){
        
    }

    public void writeRouteClass(HashMap<String, RouteClass> routelist) throws IOException {
       
        try {

            String currentPath = System.getProperty("user.dir");
            String csvfilename = currentPath + "/routecsv.csv";
            
            BufferedWriter fw;

            fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvfilename), Charset.forName("EUC-KR")));
            
            Collection<RouteClass> values = routelist.values();
       
            for(RouteClass value : values){
            
                fw.write(value.getRouteId()+","+value.getRouteName()+",");

                for(int i = 0 ; i < value.getStationList().size() ; i++){
                    fw.write(value.getStationList().get(i) + ",");
                }

                fw.newLine();
            }
            fw.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

       
    }

    public void writeStationClass(HashMap<String, StationClass> stationlist) throws IOException{

        try {

            String currentPath = System.getProperty("user.dir");
            String csvfilename = currentPath + "/stationcsv.csv";
                        
            BufferedWriter fw;

            fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvfilename), Charset.forName("EUC-KR")));
            
            Collection<StationClass> values = stationlist.values();
            
            //Collection<String> keys = stationlist.
       
            for(StationClass value : values){
            
                fw.write(value.getStationId()+","+value.getStationArsNum() +","+value.getStationName() +","+value.getStationX() +"," + value.getStationY()+","+value.getRouteListHashMap().keySet());

//                fw.write(value.getRouteListHashMap().keySet());
                //이부분을 hashmap읽는 부분으로 바꾸면 됩니다
                //for(int i = 0 ; i < value.getStationList().size() ; i++){
                    //fw.write(value.getStationList().get(i) + ",");
                //}

                fw.newLine();
            }
            fw.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



    }
    
}