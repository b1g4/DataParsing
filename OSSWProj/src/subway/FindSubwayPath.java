package subway;
import java.util.ArrayList;
/**
 * FindSubwayPath
 */
public class FindSubwayPath {
    private SubwayInfoClass subwayInfo = SubwayInfoClass.getInstance();
    private ArrayList<StationClass> pathUp = new ArrayList<StationClass>();
    private ArrayList<StationClass> pathDown = new ArrayList<StationClass>();
    private ArrayList<ArrayList<StationClass>> resultPath = new ArrayList<ArrayList<StationClass>>();

    private String start_lineNum;
    private String end_lineNum;
    private String start_stationName;
    private String end_stationName;

    public FindSubwayPath(ArrayList<String> input){
        if(input.size() != 4){
            System.out.println("wrong input");
            return;
        }
        else if(input.size()==4){
            this.start_lineNum = input.get(0);
            this.start_stationName = input.get(1);
            this.end_lineNum = input.get(2);
            this.end_stationName = input.get(3);

            LineClass line = this.subwayInfo.getLineInfo(this.start_lineNum);
            line.setCurrentStation(this.start_stationName);

            // 상행 탐색
            this.search(false, this.subwayInfo.getStationInfo(start_stationName), line, this.pathUp, 0);
            // 하행 탐색
            this.search(true, this.subwayInfo.getStationInfo(end_stationName), line, this.pathDown, 0);
        }
    }

    private void search(final boolean direction, StationClass currentStation, LineClass currentLine, ArrayList<StationClass> path, int transferCount){
        ArrayList<StationClass> tmpList = (ArrayList<StationClass>)path.clone();
        tmpList.add(currentStation);

        String nextStationName = new String();
        // 고려할 사항
        // 0. 현재 station과 line information이 도착지와 같다. ==> 경로탐색 종료. 경로 저장
        // 1. 현재 환승 횟수가 2회. ==> 추가로 환승해야 할 경우 탐색을 종료한다.
        // 2. nextStation이 start_station과 같다 ==> 순환중인 경우이므로 탐색을 종료한다. 
        // 3. 방향에 따라 종점 혹은 기점일 경우 특정 역을 2번 지나치므로 탐색을 종료한다. 

        // !0
        if(currentLine.lineNum != this.end_lineNum){
            if(currentLine.isTransfer(currentStation.stationName)){
                // 현재 역에서의 환승이 가능함.
                if(currentStation.stationName != this.end_stationName){
                    // 현재 역과 도착 역의 이름이 다름 ==> 탐색 필요
                    // 환승 횟수가 0 : 환승 역의 호선 정보 상관없이 가능
                    // 환승 횟수가 1 : 환승했을 때 lineNum이 end_lineNum과 같아야만 환승 가능
                    // 환승 횟수가 2 : 더 이상 환승이 불가능
                    if(transferCount == 0){
                        // search() : 현재 line에서 진행
                        // search() : 환승 가능한 line에서 진행
                    }
                    else if(transferCount == 1){
                        // search()
                        // if() 
                            // true : search
                        // else
                            // return;
                    }
                    else if(transferCount == 2){
                        return;
                    }

                }
                else if(currentStation.stationName == this.end_stationName){
                    // 현재 역과 도착역의 이름이 같음==> 도착
                    // 이미 추가된 경로인지 검사
                    if(this.resultPath.contains(tmpList)) return;

                    // 아니면 경로 추가 후 종료
                    this.resultPath.add(tmpList);
                    return;
                }
            }
            else{
                // 현재 역에서의 환승이 불가능함.
                // 다음 역으로 진행
                // search()
            }
        }
        // 0
        else if(currentLine.lineNum == this.end_lineNum){
            // 더 이상 환승하는 경로 필요없음
            if(currentStation.stationName != this.end_stationName){
                // 다시 탐색 필요
                // 2. next station이 start_stationName과 같다(방향에 따라 검사). ==> 순환중 = 더이상 진행하지 않는다.
                // 3. 방향에 따른 종점 및 기점 여부 검사
            }
            else if(currentStation.stationName == this.end_stationName){
                // 조건에 부합하는 경로
                // 이미 추가된 경로인지 검사
                if(this.resultPath.contains(tmpList)) return;

                // 아니면 경로 추가 후 종료
                this.resultPath.add(tmpList);
                return;
            }
        }
    }
}