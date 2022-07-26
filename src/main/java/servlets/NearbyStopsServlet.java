package servlets;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;

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

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

import data.Station;

import javax.servlet.annotation.MultipartConfig;

@WebServlet("/stops")
@MultipartConfig
public class NearbyStopsServlet  extends HttpServlet{
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
        Double lat = Double.parseDouble(values[0]);
        Double lon = Double.parseDouble(values[1]);
        ArrayList<Subway.Stop> subwayStops = mta.getNearSubwayStops( lat, lon);
        ArrayList<Bus.Stop> busStops = mta.getNearBusStops( lat, lon);

        ArrayList <Station>Stations = new ArrayList<>();
        // ArrayList <Station>subwayStations = new ArrayList<>();

        for(Bus.Stop s : busStops){
            Station station = new Station(s.getLongitude(), s.getLatitude(), s.getStopName(), Integer.toString(s.getStopID()), s.getRoutes(), "BUS");
            Stations.add(station);
        }

        for(Subway.Stop s : subwayStops){
            Station station = new Station(s.getLongitude(), s.getLatitude(), s.getStopName(), s.getStopID(), s.getRoutes(), "SUBWAY");
            Stations.add(station);
        }

        Gson gson = new Gson();
        //String busJson = gson.toJson(busStations);
        String Json = gson.toJson(Stations);
    
        response.getWriter().println(Json);
    }
}
