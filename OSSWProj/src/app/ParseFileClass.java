package app;

import java.util.ArrayList;
import java.util.List;
import bus.*;

public class ParseFileClass{
    /*
    모든 파싱관련 내용은 이 클래스에 작성되어야 한다 
    xls file open : ReadXlsClass를 사용
    csv file open : 자바 기본 I/O 사용
    csv file splitter(token) 정보 : README.md참고
    모든 정보는 busInfo member variable인 
         1. RouteList
         2. StationList
    에 저장되어야 한다.
    */

    // private member variable
    private BusInfoClass busInfo;
    private ArrayList<ArrayList<String>> valuesInFile;
    private String fileDir;
    private int rowNum;
    private int columnNum;
    private boolean result;

    // public member variable
    
    // constructor
    public ParseFileClass(String fileDir, boolean isXls, boolean isBus){
        // isXls : xls파일이면 true, csv 파일이면 false
        // 수집한 data 분류 및 최종 파일 생성을 위해 사용되는 생성자
        if(isBus){
            this.busInfo = BusInfoClass.getInstance();
            this.fileDir =fileDir;
            this.valuesInFile = new ArrayList<ArrayList<String>>();
            if(isXls){
                result = this.readXls(true);
                if(result){
                    StationClass sta = new StationClass(this.valuesInFile.get(0).subList(4,8), this.valuesInFile.get(0).get(0));
                    this.parsingRouteStationInfo();
                }
            }
            else{
                // csv 파싱하는 method 호출
                // 추가 구현 필요
            }
        }
        else{

        }
    }
    public ParseFileClass(String fileDir, boolean isBus){
        // 최종 파일 생성 후 사용되는 생성자
        this.busInfo = BusInfoClass.getInstance();
        this.fileDir = fileDir;
        this.valuesInFile = new ArrayList<ArrayList<String>>();
        
        // 추가 구현 필요
    }

    private boolean readXls(boolean isShow){
        ReadXlsClass readxls = new ReadXlsClass(this.fileDir, isShow);
        boolean result = true;
        result = readxls.readFile();
        if(result){
            this.valuesInFile = readxls.getXlsData();
            this.rowNum = this.valuesInFile.size();
            this.columnNum = this.valuesInFile.get(0).size();
            return result;
        }
        else{
            System.out.println("파일 읽기 실패");
            return result;
        }
    }

    private boolean parsingRouteStationInfo(){
        
        for(int i=0;i<this.rowNum<i++){
            StationClass sta = new StationClass(this.valuesInFile.get(i).subList(4,8), this.valuesInFile.get(i).get(0));
            RouteClass rta = new RouteClass(this.valuesInFile.get(i),this.valuesInFile.get(i).get(4));
            

        //hhhhhhhhhhhhhhhhhhhhhhhhhhhhhh
        
        }


        return true;
    }

}