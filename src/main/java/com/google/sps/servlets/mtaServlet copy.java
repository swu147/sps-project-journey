package com.google.sps.servlets;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import dev.katsute.onemta.DataResource;
import dev.katsute.onemta.DataResourceType;
import dev.katsute.onemta.MTA;
import dev.katsute.onemta.api_keys;
import dev.katsute.onemta.bus.Bus;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

@WebServlet("/mta")
public class mtaServlet extends HttpServlet{
    MTA mta;
    File bronx = null, brooklyn = null, manhattan = null,
     queens = null, staten_island = null, bus = null, subway = null, lirr = null, mnr = null;

    @Override
   public void init(){

    URL resource = getClass().getResource("/dev/katsute/onemta/google_transit_bronx.zip");
    bronx = new File("/tmp/google_transit_bronx.zip");
    try {
        FileUtils.copyURLToFile(resource, bronx);
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }


    resource = getClass().getResource("/dev/katsute/onemta/google_transit_brooklyn.zip");
    brooklyn = new File("/tmp/google_transit_brooklyn.zip");
    try {
        FileUtils.copyURLToFile(resource, brooklyn);
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    

    resource = getClass().getResource("/dev/katsute/onemta/google_transit_manhattan.zip");
    manhattan = new File("/tmp/google_transit_manhattan.zip");
    try {
        FileUtils.copyURLToFile(resource, manhattan);
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }


    resource = getClass().getResource("/dev/katsute/onemta/google_transit_queens.zip");
    queens = new File("/tmp/google_transit_queens.zip");
    try {
        FileUtils.copyURLToFile(resource, queens);
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }


    resource = getClass().getResource("/dev/katsute/onemta/google_transit_staten_island.zip");
    staten_island = new File("/tmp/google_transit_staten_island.zip");
    try {
        FileUtils.copyURLToFile(resource, staten_island);
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

    resource = getClass().getResource("/dev/katsute/onemta/google_transit_bus_company.zip");
    bus = new File("/tmp/google_transit_bus_company.zip");
    try {
        FileUtils.copyURLToFile(resource, bus);
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

    resource = getClass().getResource("/dev/katsute/onemta/google_transit_subway.zip");
    subway = new File("/tmp/google_transit_subway.zip");
    try {
        FileUtils.copyURLToFile(resource, subway);
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

    resource = getClass().getResource("/dev/katsute/onemta/google_transit_lirr.zip");
    lirr = new File("/tmp/google_transit_lirr.zip");
    try {
        FileUtils.copyURLToFile(resource, lirr);
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    

    resource = getClass().getResource("/dev/katsute/onemta/google_transit_mnr.zip");
    mnr = new File("/tmp/google_transit_mnr.zip");
    try {
        FileUtils.copyURLToFile(resource, mnr);
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    
    mta = MTA.create(
        api_keys.bus,
        api_keys.subway,
        DataResource.create(DataResourceType.Bus_Bronx, bronx),
        DataResource.create(DataResourceType.Bus_Brooklyn, brooklyn),
        DataResource.create(DataResourceType.Bus_Manhattan, manhattan),
        DataResource.create(DataResourceType.Bus_Queens, queens),
        DataResource.create(DataResourceType.Bus_StatenIsland, staten_island),
        DataResource.create(DataResourceType.Bus_Company, bus),
        DataResource.create(DataResourceType.Subway, subway),
        DataResource.create(DataResourceType.LongIslandRailroad, lirr),
        DataResource.create(DataResourceType.MetroNorthRailroad, mnr)
    );
   }
 

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    // Bus.Route M1    = mta.getBusRoute("M1", DataResourceType.Bus_Manhattan);
    String textValue = Jsoup.clean(request.getParameter("text-input"), Safelist.basic());
    Bus.Stop stop   = mta.getBusStop(Integer.parseInt(textValue));
    // stop.get
    response.getWriter().println(stop.getLatitude() + "\t" + stop.getLongitude());
    // response.getWriter().println();
    Bus.Alert[] b = mta.getBusAlerts();

    for(Bus.Alert a : b){
        response.getWriter().println(a.toString());
    }
    }
}
