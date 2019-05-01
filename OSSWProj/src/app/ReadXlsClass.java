import java.io.*;
import 

public class ReadXlsClass{
    private int rowNum;
    private int columnNum;
    private String fileName;

    public ReadXlsClass(String fileName){
        this.rowNum = -1;
        this.columnNum = -1;
        this.fileName = fileName;
    }

    private boolean readFile(){
        try{
            FileInputStream fis = new FileInputStream(this.fileName);
        }
        catch(IOException e){
            System.out.println("파일 입출력 오류" + e);
            return false;
        }
        
        return true;
    }
}