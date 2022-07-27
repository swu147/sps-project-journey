package servlets;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

import javax.servlet.annotation.MultipartConfig;
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

import data.mtaSingleton;

import data.busVehicle;
import data.subwayVehicle;

@WebServlet("/vehicles")
@MultipartConfig(
        fileSizeThreshold   = 1024 * 1024 * 1,  // 1 MB
        maxFileSize         = 1024 * 1024 * 10, // 10 MB
        maxRequestSize      = 1024 * 1024 * 15, // 15 MB
        location            = "D:/Uploads"
)
public class vehiclesServlet  extends HttpServlet{
   
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        mtaSingleton singleton = mtaSingleton.INSTANCE.getInstance();
        MTA mta = singleton.getMta();

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
        else if(Objects.equals(type, "SUBWAY")){
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
