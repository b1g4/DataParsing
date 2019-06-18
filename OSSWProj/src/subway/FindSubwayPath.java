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
            this.search_all(false, this.subwayInfo.getStationInfo(start_stationName), line, this.pathUp, 0);
            // 하행 탐색

            line.setCurrentStation(this.start_stationName);
            this.search_all(true, this.subwayInfo.getStationInfo(start_stationName), line, this.pathDown, 0);

            for(int i=0; i<this.resultPath.size(); i++){
                //System.out.println(this.resultPath.get(i));
            }
        }
        else{
            this.start_lineNum = "";
            this.start_stationName = "";
            this.end_lineNum = "";
            this.end_stationName = "";
            return;
        }
    }

    private void search_all(final boolean direction, StationClass currentStation, LineClass currentLine, ArrayList<StationClass> path, int transferCount){
        ArrayList<StationClass> tmpList = (ArrayList<StationClass>)path.clone();
        tmpList.add(currentStation);

        // check path finding process
        // for debug
        System.out.println(currentLine.lineNum + " : " + direction);
        for(int i=0; i<tmpList.size(); i++){
            System.out.print(tmpList.get(i).stationName + " ");
        }
        System.out.println("");

        StationClass next_station;
        int i_direction = direction ? 1 : 0;

        if(this.end_stationName.equals(currentStation.stationName)){
        // 종료조건 1. 현재 정류장이 도착지와 같다면 현재까지 탐색한 경로를 저장하고 종료한다.
            if(this.resultPath.contains(tmpList)){ return; }
            
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
                    search_all(direction, next_station, next_line, tmpList, transferCount+1);
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
                    search_all(!direction, next_station, next_line, tmpList, transferCount+1);
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
        search_all(direction, next_station, currentLine, tmpList, transferCount);
    }

    // 수정 필요
    // private void search_shortest(final boolean direction, StationClass currentStation, LineClass currentLine, ArrayList<StationClass> path, int transferCount){
    //     ArrayList<StationClass> tmpList = (ArrayList<StationClass>)path.clone();
    //     tmpList.add(currentStation);

    //     System.out.println(currentLine.lineNum + " : " + direction);
    //     for(int i=0; i<tmpList.size(); i++){
    //         System.out.print(tmpList.get(i).stationName + " ");
    //     }
    //     System.out.println("");

    //     int i_direction = direction ? 1 : 0;
    //     // 고려할 사항
    //     // 0. 현재 station과 line information이 도착지와 같다. ==> 경로탐색 종료. 경로 저장
    //     // 1. 현재 환승 횟수가 2회. ==> 추가로 환승해야 할 경우 탐색을 종료한다.
    //     // 2. nextStation이 start_station과 같다 ==> 순환중인 경우이므로 탐색을 종료한다. 
    //     // 3. 방향에 따라 종점 혹은 기점일 경우 특정 역을 2번 지나치므로 탐색을 종료한다. 

    //     // !0
    //     StationClass next_station;
    //     LineClass next_line;
    //     if(!currentLine.lineNum.equals(this.end_lineNum)){
    //         if(currentLine.isTransfer(currentStation.stationName)){
    //             // 현재 역에서의 환승이 가능함.
    //             if(!currentStation.stationName.equals(this.end_stationName)){
    //                 // 현재 역과 도착 역의 이름이 다름 ==> 탐색 필요
    //                 // 환승 횟수가 0 : 환승 역의 호선 정보 상관없이 가능
    //                 // 환승 횟수가 1 : 환승했을 때 lineNum이 end_lineNum과 같아야만 환승 가능
    //                 // 환승 횟수가 2 : 더 이상 환승이 불가능
                    
    //                 ArrayList<String> transferList;
    //                 if(transferCount == 0){
    //                     if(currentLine.moveNext(i_direction)){
    //                         // 더 이상 이동 가능
    //                         next_station = this.subwayInfo.getStationInfo(currentLine.getCurrentStationName());
    //                         if(currentLine.isCircular()){
    //                             if(currentLine.isFirst(next_station.stationName)){
    //                                 return;
    //                             }
    //                         }
    //                         // 현재 탐색중인 방향으로 다음 정류장 탐색
    //                         search_shortest(direction, next_station, currentLine, tmpList, transferCount);
    //                         transferList= currentStation.getTransferLineNumList();
    //                         for(int i=1; i<transferList.size(); i++){
    //                             next_line = this.subwayInfo.getLineInfo(transferList.get(i));
    //                             next_line.setCurrentStation(currentStation.stationName);
    //                             if(next_line.moveNext(i_direction)){
    //                                 next_station = this.subwayInfo.getStationInfo(next_line.getCurrentStationName());
    //                                 // 환승하여 새로 이동하는 호선에서 양방향으로 탐색 시작
    //                                 if(next_line.isCircular()){
    //                                     if(next_line.isFirst(next_station.stationName) || next_line.isLast(next_station.stationName)){
    //                                         return;
    //                                     }
    //                                 }
    //                                 search_shortest(direction, next_station, next_line, tmpList, transferCount+1);
    //                             }
    //                             else{
    //                                 return;
    //                             }
    //                             if(next_line.moveNext(1- i_direction)){
    //                                 next_station = this.subwayInfo.getStationInfo(next_line.getCurrentStationName());
    //                                 if(next_line.isCircular()){
    //                                     if(next_line.isFirst(next_station.stationName) || next_line.isLast(next_station.stationName)){
    //                                         return;
    //                                     }
    //                                 }
    //                                 search_shortest(!direction, next_station, next_line, tmpList, transferCount+1);
    //                             }
    //                             else{
    //                                 return;
    //                             }
    //                         }
    //                     }
    //                     else{
    //                         // 더 이상 이동 불가(종점 혹은 기점)
    //                         return;
    //                     }
    //                 }
    //                 else if(transferCount == 1){
    //                     // search_shortest()
    //                     if(currentLine.moveNext(i_direction)) {
    //                         next_station = this.subwayInfo.getStationInfo(currentLine.getCurrentStationName());
    //                         if(currentLine.isCircular()){
    //                             if(currentLine.isFirst(next_station.stationName) || currentLine.isLast(next_station.stationName)){
    //                                 return;
    //                             }
    //                         }
    //                         search_shortest(direction, next_station, currentLine, tmpList, transferCount);
    //                     }
    //                     transferList = currentStation.getTransferLineNumList();
    //                     for(int i=1; i<transferList.size(); i++){
    //                         if(end_lineNum != transferList.get(i)){
    //                             return;
    //                         }
    //                         else{
    //                             next_line = this.subwayInfo.getLineInfo(transferList.get(i));
    //                             next_line.setCurrentStation(currentStation.stationName);
    //                             if(next_line.moveNext(i_direction)){
    //                                 next_station = this.subwayInfo.getStationInfo(next_line.getCurrentStationName());
    //                                 if(next_line.isCircular()){
    //                                     if(next_line.isFirst(next_station.stationName) || next_line.isLast(next_station.stationName)){
    //                                         return;
    //                                     }
    //                                 }
    //                                 search_shortest(direction, next_station, next_line, tmpList, transferCount+1);
    //                             }
    //                             else{
    //                                 return ;
    //                             }

    //                             if(next_line.moveNext(1-i_direction)){
    //                                 next_station = this.subwayInfo.getStationInfo(next_line.getCurrentStationName());
    //                                 if(next_line.isCircular()){
    //                                     if(next_line.isFirst(next_station.stationName) || next_line.isLast(next_station.stationName)){
    //                                         return;
    //                                     }
    //                                 }
    //                                 search_shortest(direction, next_station, next_line, tmpList, transferCount+1);
    //                             }
    //                             else{
    //                                 return;
    //                             }
    //                         }
    //                     }
    //                 }
    //                 else if(transferCount == 2){
    //                     // 더이상 탐색이 불필요.
    //                     //  아마 실행되지 않을 부분
    //                     return;
    //                 }

    //             }
    //             else if(currentStation.stationName.equals(this.end_stationName)){
    //                 // 현재 역과 도착역의 이름이 같음==> 도착
    //                 // 이미 추가된 경로인지 검사
    //                 if(this.resultPath.contains(tmpList)) return;

    //                 // 아니면 경로 추가 후 종료
    //                 this.resultPath.add(tmpList);
    //                 return;
    //             }
    //         }
    //         else{
    //             // 현재 역에서의 환승이 불가능함.
    //             // 다음 역으로 진행
    //             // search_shortest()
    //             if(currentLine.moveNext(i_direction)){
    //                 next_station = this.subwayInfo.getStationInfo(currentLine.getCurrentStationName());
    //                 if(currentLine.isCircular()){
    //                     if(currentLine.isFirst(next_station.stationName) || currentLine.isLast(next_station.stationName)){
    //                         return;
    //                     }
    //                 }
    //                 search_shortest(direction, next_station, currentLine, tmpList, transferCount);
    //             }
    //             else{
    //                 return;
    //             }
    //         }
    //     }
    //     // 0
    //     else if(currentLine.lineNum.equals(this.end_lineNum)){
    //         // 더 이상 환승하는 경로 필요없음
    //         if(!currentStation.stationName.equals(this.end_stationName)){
    //             // 다시 탐색 필요
    //             // search_shortest(), 현재 방향으로 계속 진행. 종점이나 기점일 경우 종료되도록 작성.
    //             if(currentLine.moveNext(i_direction)){
    //                 next_station = this.subwayInfo.getStationInfo(currentLine.getCurrentStationName());
    //                 if(currentLine.isCircular()){
    //                     if(currentLine.isFirst(next_station.stationName) || currentLine.isLast(next_station.stationName)){
    //                         return;
    //                     }
    //                 }
    //                 search_shortest(direction, next_station, currentLine, tmpList, transferCount);
    //             }
    //             else{
    //                 return;
    //             }
    //         }
    //         else if(currentStation.stationName.equals(this.end_stationName)){
    //             // 조건에 부합하는 경로
    //             // 이미 추가된 경로인지 검사
    //             if(this.resultPath.contains(tmpList)) return;

    //             // 아니면 경로 추가 후 종료
    //             this.resultPath.add(tmpList);
    //             return;
    //         }
    //     }
    // }
}