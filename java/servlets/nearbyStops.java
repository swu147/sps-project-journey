package com.google.sps.servlets;

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

public final class dataServlet extends HttpServlet{

    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    //TODO: user's location passed from client side
    double userLongitude = ;
    double userLatitude = ;

    //TODO: the library method that takes in user's longitude and latitude then return the data of stations

    //start an empty list of stations
    List <Station>listOfStations = new ArrayList<>();
    
    //TODO: convert the data retrieved from library to Station object and add them to arraylist
    for (item i: stationsdata){
    //TODO: replace the parameters(longitude, latitude, stationName, id, lineName, listOfVehicles) with corresponding properties from the item i from library
    Station station = new Station(longitude, latitude, stationName, id, lineName, listOfVehicles);
    listOfStations.add(station);
    }

    //parse the list of stations into JSON array
    Gson gson = new Gson();
    String json = gson.toJson(listOfStations);

    // Send the JSON as the response
    response.setContentType("application/json;");
    response.getWriter().println(json);
}

}
