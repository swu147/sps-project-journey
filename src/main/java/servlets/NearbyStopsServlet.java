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
import data.mtaSingleton;

import javax.servlet.annotation.MultipartConfig;

@WebServlet("/stops")
@MultipartConfig
public class NearbyStopsServlet  extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        mtaSingleton singleton = mtaSingleton.INSTANCE.getInstance();
        MTA mta = singleton.getMta();

        // System.out.println(singleton.getInfo()); //Initial class info

        // mtaSingleton singleton1 = mtaSingleton.INSTANCE.getInstance();
        // singleton1.setInfo();

        // System.out.println(singleton.getInfo()); //New class info
        // System.out.println(singleton1.getInfo()); //New class info

        String textValue = Jsoup.clean(request.getParameter("text-input"), Safelist.basic());
        String[] values = textValue.split(" ");
        Double lat = Double.parseDouble(values[0]);
        Double lon = Double.parseDouble(values[1]);
        Double km_away = 0.5;

        ArrayList<Station> Stations = Station.getStations(lat, lon, km_away);
        // ArrayList<Subway.Stop> subwayStops = mta.getNearSubwayStops( lat, lon);
        // ArrayList<Bus.Stop> busStops = mta.getNearBusStops( lat, lon);

        // ArrayList <Station>Stations = new ArrayList<>();
        // // ArrayList <Station>subwayStations = new ArrayList<>();

        // for(Bus.Stop s : busStops){
        //     Station station = new Station(s.getLongitude(), s.getLatitude(), s.getStopName(), Integer.toString(s.getStopID()), s.getRoutes(), "BUS");
        //     Stations.add(station);
        // }

        // for(Subway.Stop s : subwayStops){
        //     Station station = new Station(s.getLongitude(), s.getLatitude(), s.getStopName(), s.getStopID(), s.getRoutes(), "SUBWAY");
        //     Stations.add(station);
        // }

        Gson gson = new Gson();
        //String busJson = gson.toJson(busStations);
        String Json = gson.toJson(Stations);
    
        response.getWriter().println(Json);
    }
}
