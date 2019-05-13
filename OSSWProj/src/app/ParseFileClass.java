package app;

import java.util.ArrayList;
import bus.*;
import fileIO.*;

/**
 * 파싱관련 class관리 및 method호출하는 class
 * 버스관련 파싱은 Bus package의 ParseBusClass가 담당
 * 지하철관련 파싱은 Subway package의 ParsSubwayClass가 담당
 * 
 */
public class ParseFileClass {
    
    // private member variable
    private ArrayList<ArrayList<String>> valuesInFile;
    private String fileDir;
    private boolean result;

    // public member variable

    /**
     * constructor
     * @param fileDir : 파싱하려는 파일 경로
     * @param isXls : 파일 확장자가 Xls인지 아닌지 검사. 읽는 방식이 다름
     * @param isBus : bus와 subway에 대한 정보를 모두 파싱해야 하므로 둘중 어느 파일인지 검사
     */
    public ParseFileClass(String fileDir, boolean isXls, boolean isBus) {
        if (isBus) {
            this.fileDir = fileDir;
            this.valuesInFile = new ArrayList<ArrayList<String>>();

            if (isXls) {
                this.result = this.readXls(true);

                if (this.result) {
                    ParseBusClass parse = new ParseBusClass(this.valuesInFile);
                    result = parse.parsingRouteStationInfo();
                }
            } else {
                // csv 파싱하는 method 호출
                this.result = this.readCSV(true);
                
            }
        } else {

        }
    }

    /**
     * 모든 정보를 정리한 후 사용되는 생성자
     * 실제 어플리케이션 구동시 사용됨
     * @param fileDir : 정리된 파일의 위치
     * @param isBus : 버스에 관한 파일에 대한 정보면 true
     */
    public ParseFileClass(String fileDir, boolean isBus) {
        // 최종 파일 생성 후 사용되는 생성자
        this.fileDir = fileDir;
        this.valuesInFile = new ArrayList<ArrayList<String>>();

        // 추가 구현 필요
    }

    /**
     * isXls가 true일 시 호출되는 함수
     * xls파일을 읽고 해당 내용을 valuesInFile에 저장한다. 
     * @param isShow : true면 읽은 내용을 콘솔에 출력
     * @return 파일을 정상적으로 읽어들였을 시 true 반환
     */
    private boolean readXls(boolean isShow) {
        ReadXlsClass readxls = new ReadXlsClass(this.fileDir, isShow);
        boolean result = true;
        result = readxls.readFile();
        if (result) {
            this.valuesInFile = readxls.getXlsData();
            return result;
        } else {
            System.out.println("파일 읽기 실패");
            return result;
        }
    }

    /**
     * isXls가 false일 시 호출되는 함수
     * csv파일을 읽고 해당 내용을 valuesInFile에 저장한다.
     * @param isShow : true면 읽은 내용을 콘솔에 출력
     * @return 파일을 정상적으로 읽어들였을 시 true 반환
     */
    private boolean readCSV(boolean isShow){
        ReadCSVClass readCSV =new ReadCSVClass(this.fileDir, isShow);
        boolean result = true;
        result = readCSV.readFile();
        if(result){
            this.valuesInFile = readCSV.getCsvData();
            return result;
        }
        else{
            System.out.println("파일 읽기 실패" + this.fileDir);
            return result;
        }
    }
}