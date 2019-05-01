package app;

import java.io.*;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.*;

public class ReadXlsClass{
    private int rowNum;
    private int columnNum;
    private String fileName;
    private ArrayList<ArrayList<String>> xlsFileValues = new ArrayList<ArrayList<String>>();

    public ReadXlsClass(String fileName, boolean show){
        this.rowNum = -1;
        this.columnNum = -1;
        this.fileName = fileName;
        boolean result = readFile();
        if(result){
            if(show){
                this.showAllData();
            }
            else{
                System.out.println("read file end");
            }
        }
        else{
            System.out.println("read file filed, check file directory");
        }
    }

    private boolean readFile(){
        FileInputStream fis;
        HSSFWorkbook workbook;
        try{
            fis = new FileInputStream(this.fileName);
            workbook = new HSSFWorkbook(fis);
        }
        catch(IOException e){
            System.out.println("파일 입출력 오류 " + e);
            return false;
        }
        
        HSSFSheet sheet = workbook.getSheetAt(0);
        this.rowNum = sheet.getPhysicalNumberOfRows();
        for(int rowIndex = 1; rowIndex < this.rowNum; rowIndex++){
            HSSFRow row = sheet.getRow(rowIndex);
            if(row != null){
                this.columnNum = row.getPhysicalNumberOfCells();
                ArrayList<String> cellInfo = new ArrayList<String>();
                for(int columnIndex = 0; columnIndex < this.columnNum; columnIndex++){
                    HSSFCell cell = row.getCell(columnIndex);
                    String value = "";
                    if(cell == null){
                        // cell 이 빈값
                    }
                    else {
                        switch(cell.getCellType()){
                        case FORMULA:
                            value=cell.getCellFormula();
                            break;
                        case NUMERIC:
                            value=cell.getNumericCellValue()+"";
                            break;
                        case STRING:
                            value=cell.getStringCellValue()+"";
                            break;
                        case BOOLEAN:
                            value=cell.getBooleanCellValue()+"";
                            break;
                        case ERROR:
                            value=cell.getErrorCellValue()+"";
                            break;
                        case BLANK:
                            break;
                        case _NONE:
                            break;
                        }
                    }
                    // 파일 정보 저장
                    cellInfo.add(value);
                }
                this.xlsFileValues.add(cellInfo);
            }   
        }
        try{
            workbook.close();
        }
        catch(IOException e){
            System.out.println("파일 입출력 오류 " + e);
        }
        return true;
    }

    public void showAllData(){

    }
    
    public ArrayList<ArrayList<String>> getXlsData(){
        return this.xlsFileValues;
    }
}