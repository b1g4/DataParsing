package app;

import java.util.ArrayList;

public class StationClass{
    // public member variable
    public String stnd_bsst_id;
    public String bsst_ars_no;
    public String bus_sta_nm;
    public ArrayList<RouteClass> routeList = new ArrayList<RouteClass>();

    // constructor
    public StationClass(){

    }
}