package fileIO;

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

/**
 * 원본 데이터를 읽은 후 가공된 정보를 CSV형태로 저장하기 위해 만든 클래스
 */
public class WriteCsvClass {

    /**
     * 생성자
     */
    public WriteCsvClass(){
        
    }

    /**
     * 
     * @param routelist
     * @throws IOException
     */
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

    /**
     * 
     * @param stationlist
     * @throws IOException
     */
    public void writeStationClass(HashMap<String, StationClass> stationlist) throws IOException{
        try {
            String currentPath = System.getProperty("user.dir");
            String csvfilename = currentPath + "/stationcsv.csv";
                        
            BufferedWriter fw;
            fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvfilename), Charset.forName("EUC-KR")));
            
            Collection<StationClass> values = stationlist.values();
            for(StationClass value : values){
                fw.write(value.getStationId()+","+value.getStationArsNum() +","+value.getStationName() +","+value.getStationX() +"," 
                + value.getStationY()+",");              
                Collection<String> keys = value.getRouteListHashMap().keySet();
                for(String key : keys){
                    fw.write(key + ",");
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
}