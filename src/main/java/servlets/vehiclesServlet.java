package servlets;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.FileUtils;

import dev.katsute.onemta.DataResource;
import dev.katsute.onemta.DataResourceType;
import dev.katsute.onemta.MTA;
import dev.katsute.onemta.api_keys;
import dev.katsute.onemta.bus.Bus;
import dev.katsute.onemta.subway.Subway;
import dev.katsute.onemta.types.TransitVehicle;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

import data.busVehicle;
import data.subwayVehicle;


@WebServlet("/vehicles")
public class vehiclesServlet  extends HttpServlet{
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

        String textValue = Jsoup.clean(request.getParameter("text-input"), Safelist.basic());
        String[] values = textValue.split(" ");
        String stopID = values[0];
        String type = values[1];
        String routeID = values[2];

        // response.getWriter().println(type.length());
        // System.out.println(type == 0);


        Gson gson = new Gson();
        String res = "";

        if(Objects.equals(type, "BUS")){
            ArrayList<busVehicle> vehicles = new ArrayList<busVehicle>();
            Bus.Vehicle[] b = mta.getBusStop(Integer.valueOf(stopID)).getVehicles();
            // response.getWriter().println(stopID + type);


            for(Bus.Vehicle v : b){
                // response.getWriter().println(routeID + v.getRouteID());
                // response.getWriter().println(Objects.equals(routeID, v.getRouteID()));
                if(Objects.equals(routeID, v.getRouteID())){
                    busVehicle vehicle = new busVehicle(v);
                    vehicles.add(vehicle);
                }

            }
            res = gson.toJson(vehicles);
        }
        else if(Objects.equals(type, "Subway")){
            ArrayList<subwayVehicle> vehicles = new ArrayList<subwayVehicle>();
            Subway.Vehicle[] s = mta.getSubwayStop(stopID).getVehicles();
            // response.getWriter().println(s.length);

            for(Subway.Vehicle v : s){
                if(Objects.equals(routeID, v.getRouteID())){
                    subwayVehicle vehicle = new subwayVehicle(v);
                    vehicles.add(vehicle);
                }
            }
            res = gson.toJson(vehicles);
        }
        response.getWriter().println(res);
    }

}
