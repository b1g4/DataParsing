package subway;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * FindSubwayPath
 */
public class FindSubwayPath {
    private SubwayInfoClass subwayInfo = SubwayInfoClass.getInstance();
    private ArrayList<StationClass> pathUp = new ArrayList<StationClass>();
    private ArrayList<StationClass> pathDown = new ArrayList<StationClass>();
    private ArrayList<ArrayList<StationClass>> resultPath = new ArrayList<ArrayList<StationClass>>();

    private ArrayList<String> transferInfo = new ArrayList<String>();
    private ArrayList<ArrayList<String>> transferInfoList = new ArrayList<ArrayList<String>>();

    private final String start_lineNum;
    private final String end_lineNum;
    private final String start_stationName;
    private final String end_stationName;

    public FindSubwayPath(ArrayList<String> input){
        if(input.size() != 4){
            System.out.println("wrong input");
            this.start_lineNum = "";
            this.start_stationName = "";
            this.end_lineNum = "";
            this.end_stationName = "";
            return;
        }
        else if(input.size()==4){
            this.start_lineNum = input.get(0);
            this.start_stationName = input.get(1);
            this.end_lineNum = input.get(2);
            this.end_stationName = input.get(3);
            LineClass line = this.subwayInfo.getLineInfo(this.start_lineNum);
            line.setCurrentStation(this.start_stationName);
            if(line.isCircular()){
                line.setFirst(this.start_stationName);
            }
            // 상행 탐색
            this.search_all(false, this.subwayInfo.getStationInfo(start_stationName), line, this.pathUp, this.transferInfo, 0);
            // 하행 탐색

            line.setCurrentStation(this.start_stationName);
            this.search_all(true, this.subwayInfo.getStationInfo(start_stationName), line, this.pathDown, this.transferInfo, 0);

            AnalysisPath path = new AnalysisPath(resultPath, transferInfoList);
            
        }
        else{
            this.start_lineNum = "";
            this.start_stationName = "";
            this.end_lineNum = "";
            this.end_stationName = "";
            return;
        }
    }

    private void search_all(final boolean direction, StationClass currentStation, LineClass currentLine, ArrayList<StationClass> path, ArrayList<String> transferInfo, int transferCount){
        ArrayList<StationClass> tmpList = (ArrayList<StationClass>)path.clone();
        tmpList.add(currentStation);

        ArrayList<String> tmpList2 = (ArrayList<String>)transferInfo.clone();
        tmpList2.add(currentLine.lineNum);
        // check path finding process
        // for debug
        // System.out.println(currentLine.lineNum + " : " + direction);
        // for(int i=0; i<tmpList.size(); i++){
        //     System.out.print(tmpList.get(i).stationName + " ");
        // }
        // System.out.println("");

        StationClass next_station;
        int i_direction = direction ? 1 : 0;

        if(this.end_stationName.equals(currentStation.stationName)){
        // 종료조건 1. 현재 정류장이 도착지와 같다면 현재까지 탐색한 경로를 저장하고 종료한다.
            if(this.resultPath.contains(tmpList)){ 
                //this.transferInfoList.add(tmpMap);
                return;
            }
            this.transferInfoList.add(tmpList2);
            this.resultPath.add(tmpList);
            return;
        }
        
        // 환승조건
        // 환승을 진행하더라도 현재 가고있는 방향으로는 탐색이 필요함. 
        if(currentLine.isTransfer(currentStation.stationName)){
            // 현재 정류장이 환승 가능한 정류장일 때
            LineClass next_line;

            // 환승 가능한 모든 노선에 대해 탐색 시작
            ArrayList<String> transferLineList = currentStation.getTransferLineNumList();
            for(int i=0; i<transferLineList.size(); i++){
                if(transferLineList.get(i).equals(currentLine.lineNum)){
                    continue;
                }
                next_line = this.subwayInfo.getLineInfo(transferLineList.get(i));
                next_line.setCurrentStation(currentStation.stationName);
                if(next_line.isCircular()){
                    next_line.setFirst(currentStation.stationName);
                }
                if(next_line.moveNext(i_direction)){
                    // 현재 방향의 다음 정류장이 기점/종점이 아님
                    if(transferCount == 0){
                        // 별다른 조건 없이 환승 가능한 모든 방향으로 환승 진행
                        // 환승 진행시 양방향 모두 검사 필요
                        next_station = this.subwayInfo.getStationInfo(next_line.getCurrentStationName());
                    }
                    else if(transferCount == 1){
                        if(next_line.lineNum.equals(this.end_lineNum)){
                            // 다음 노선이 목적지 노선과 같을때만 환승을 진행
                            next_station = this.subwayInfo.getStationInfo(next_line.getCurrentStationName());
                        }
                        else{
                            continue;
                        }
                    }
                    else{
                        continue;
                    }
                    if(next_line.isCircular()){
                        if(next_line.isFirst(next_station.stationName)){
                            continue;
                        }
                    }
                    if(currentStation.stationName.equals(this.start_stationName)){
                        tmpList2.remove(0);
                        tmpList2.add(next_line.lineNum);
                        search_all(direction, next_station, next_line, tmpList, tmpList2, transferCount);
                    }
                    else{
                        //tmpMap.put(currentStation.stationName, next_line.lineNum);
                        search_all(direction, next_station, next_line, tmpList, tmpList2, transferCount+1);
                    }
                }
                else{
                    // 기점/ 종점
                    // 여기서 탐색이 종료되면 안됨. 종료될 경우 환승가능한 정류장에서 더이상 다음 정류장으로 진행이 안됨.
                    continue;
                }
                next_line.setCurrentStation(currentStation.stationName);
                if(next_line.moveNext(1 - i_direction)){
                    // 반대 방향의 다음 정류장이 기점/종점이 아님
                    if(transferCount == 0){
                        // 별다른 조건 없이 환승 가능한 모든 방향으로 환승 진행
                        // 환승 진행시 양방향 모두 검사 필요
                        next_station = this.subwayInfo.getStationInfo(next_line.getCurrentStationName());
                    }
                    else if(transferCount == 1){
                        if(next_line.lineNum.equals(this.end_lineNum)){
                            next_station = this.subwayInfo.getStationInfo(next_line.getCurrentStationName());
                        }
                        else{
                            continue;
                        }
                    }
                    else{
                        continue;
                    }
                    if(next_line.isCircular()){
                        if(next_line.isFirst(next_station.stationName)){
                            continue;
                        }
                    }
                    if(currentStation.stationName.equals(this.start_stationName)){
                        tmpList2.remove(0);
                        tmpList2.add(next_line.lineNum);
                        search_all(!direction, next_station, next_line, tmpList, tmpList2, transferCount);
                    }
                    else{
                        //tmpMap.put(currentStation.stationName, next_line.lineNum);
                        search_all(!direction, next_station, next_line, tmpList, tmpList2, transferCount+1);
                    }
                }
                else{
                    // 기점/ 종점
                    // 여기서 탐색이 종료되면 안됨. 종료될 경우 환승가능한 정류장에서 더이상 다음 정류장으로 진행이 안됨.
                    continue;
                }
            }
        }

        // 방향에 따른 기점 혹은 종점 탐색.
        // next_station 초기화 진행
        currentLine.setCurrentStation(currentStation.stationName);
        if(currentLine.moveNext(i_direction)){
            // 진행중인 방향의 다음 정류장이 기점/종점이 아님
            next_station = this.subwayInfo.getStationInfo(currentLine.getCurrentStationName());
        }
        else{
            // 진행중인 방향의 다음 정류장이 기점/종점임
            return;
        }
        
        // 순환노선 검색 후 순환 방지
        if(currentLine.isCircular()){
            if(currentLine.isFirst(next_station.stationName)){
                return;
            }
        }

        // 다음 경로 탐색
        if(currentStation.stationName.equals(this.start_stationName)){
            //tmpMap.put(this.start_stationName, this.start_lineNum);
        }
        search_all(direction, next_station, currentLine, tmpList, tmpList2, transferCount);
    }

    private void calc_time(ArrayList<StationClass> path){

    }
}