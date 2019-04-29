Repository for DataParsing
==========================

-----------------------------------------------

BUS (all route)
---------------

Data per month (2015~2018)
---------------------------
* split by ','
* index : use_day | bus_route_no | bus_route_nm | stnd_bsst_id | bus_ars_no | bus_sta_nm | total 승차 | total 하차 | dump | dump | dump

Data per year (2015~2018)
-------------------------
* split by ';'
* index : use_mon | bus_route_id | bus_route_no | bus_route_nm | stnd_bsst_id | bsst_ars_no | bus_sta_id | bus_sta_nm | (승차 | 하자)(24개[0...23]) | dump | dump
* route id를 API에서 사용하지 못하는 문제가 있음 저장 x

서울시 노선현황 (2019 1월 24일 기준) (xls)
-----------------------------------
* 현재 운행중인 노선정보
* bus_route_id | bus_route_nm
* API에서 사용 가능한 9자리로된 정확한 정보

서울시 버스노선정보 (2019년 1월 24일 기준) (xls)
----------------------------------------
* 현재 운행중인 노선 정보
* 서울시 노선 현황보다 자세한 정보
* bus_route_id | bus_route_nm | turn(정류소 순번) | section_id | stnd_bsst_id | bsst_ars_no | bus_sta_nm | bus_sta_x | bus_sta_y
* 노선정보 저장하는데 필요한 모든 정보를 가지고 있음

----------------------------------------------

SUBWAY
-----------------

Data per month (2015~2018), all line
--------------------------
* split by ','
* index : use_day | route_name | sub_sta_id(4자리) | sub_sta_nm | total 승차 | total 하차 | dump | dump

Data per year (2008~2017_7), 
---------------------------
* split by ','
* use_day | sub_sta_id(4자리) | sub_sta_nm | div(승차, 하차) | time(05~24), 20개
 * 몇호선인지 표시가 안되어있음, per month파일을 활용해서 파싱필요

congestion(csv)
------------
* 2015년 파일 사용
* use_day(평일, 토요일, 일요일) | line_number | div(상, 하행. 내선|외선(2호선)) | sta_nm | sta_id(4자리) | 5~0시 혼잡도(20개)
* 해당 시간 승차인원 / 열차 1량의 승차인원 * 100 (%)

2014
-----
* 2014년 1~8호선 상,하차 인원
* 1234호선(csv)
 * split by ''
 * use_day | line_number | sub_sta_nm | div(승, 하차) | 00~24(24개) | total
* 5678호선(xlsx)
 * line_number | sub_sta_nm | use_day | div(승, 하차) | total | 05~24(20개)

환승역 거리 및 소요시간
----------------------
* 현재 1~4호선 환승노선 거리, 소요시간 정보
* 사람 이동속도를 1.2m/s로 가정하여 소요시간 출력

실시간 도착 역정보
-----------------
* Subway id | sta_id(10자리) | sta_nm 으로 이루어짐
* 이 파일에 있는 sta_id값이 full length일것으로 추측(뒤 4자리가 동일함)
* 서울시 내부에 있는 모든 역에 대한 정보 제공

지하철 혼잡도 및 소요시간
-----------------------
* 자료구조 설계때 직접 파싱하여 만들어논 혼잡도 파일 상,하행 구분되어있음
* 위에 파일 파싱을 마친 후 신뢰할 수 있는지 판다. 
* 파싱수행 x

--------------------------------

Dataset file link
----------------

* https://1drv.ms/f/s!Ap_hDwon6Hz2g4wLh41J1rW57hgKQA
 * 비번필요
 * 수정원할시 톡방에 요청