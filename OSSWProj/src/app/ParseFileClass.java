package app;

public class ParseFileClass{
    /*
    전반적인 파싱 담당하는 클래스
    xls file open : ReadXlsClass를 사용
    csv file open : 자바 기본 I/O 사용
    csv file splitter(token) 정보 : README.md참고
    모든 정보는 busInfo member variable인 
         1. RouteList
         2. StationList
    에 저장되어야 한다.
    */
    // private member variable
    private BusInfoClass busInfo = BusInfoClass.getInstance();

    // public member variable

    // constructor
    public ParseFileClass(){

    }

}