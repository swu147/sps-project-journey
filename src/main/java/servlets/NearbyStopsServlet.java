package servlets;

import com.google.gson.Gson;
import data.Station;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/get-stops")

public final class NearbyStopsServlet extends HttpServlet{

    // @Override
    // public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException {
    

    // //TODO: the library method that takes in user's longitude and latitude then return the data of stations

    // //start an empty list of stations
    // List <Station>listOfStations = new ArrayList<>();
    
    // //TODO: convert the data retrieved from library to Station object and add them to arraylist
    // // for (item i: stationsdata){
    // //     longitude = 
    // //     latitude = 
    // //     stationName = 
    // //     id = 
    // Station station = new Station(longitude, latitude, stationName, id);
    // listOfStations.add(station);
    // }

    //parse the list of stations into JSON array
    // Gson gson = new Gson();
    // String json = gson.toJson(listOfStations);

    // // Send the JSON as the response
    // response.setContentType("application/json;");
    // response.getWriter().println(json);
}


