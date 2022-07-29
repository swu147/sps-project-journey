package data;

import java.util.ArrayList;
import java.util.Arrays;

import dev.katsute.onemta.MTA;
import dev.katsute.onemta.bus.Bus;
import dev.katsute.onemta.subway.Subway;

import java.text.ParseException; 
import java.util.ArrayList; 
import java.util.Collections; 
import java.util.Comparator; 
import java.util.HashMap; 
import java.util.LinkedHashMap; 
import java.util.List; 
import java.util.Map.Entry; 
import java.util.Set; 
import java.util.TreeMap;


public final class Station implements java.lang.Comparable<Station>{

    private final double longitude;
    private final double latitude;
    private final String stationName;
    private final String id;
    private final String[] routes;
    private final String type;
    private final Double distance;
  
    public Station(double longitude, double latitude, String stationName, String id, String[] routes, String type, Double distance) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.stationName = stationName;
        this.id = id;
        this.routes = routes;
        this.type = type;
        this.distance = distance;    
    }

    public double getLongitude(){
        return longitude;
    }
    public double getLatitude(){
        return latitude;
    }
    public String getStationName(){
        return stationName;
    }
    public String getId(){
        return id;
    }
    public String[] getLineName(){
        return routes;
    }

    public double getDistance(){
        return distance;
    }

    public int compareTo(Station compareStation) {
        int d1 = (int)(this.distance * 100000);
        int d2 = (int)(compareStation.getDistance() * 100000);
		return d1-d2;
	}

    static double haversine(double lat1, double lon1,
                            double lat2, double lon2)
    {
        // distance between latitudes and longitudes
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
 
        // convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
 
        // apply formulae
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                   Math.pow(Math.sin(dLon / 2), 2) *
                   Math.cos(lat1) *
                   Math.cos(lat2);
        double rad = 6371;
        double c = 2 * Math.asin(Math.sqrt(a));
        return rad * c;
    }


    public static ArrayList<Station> getStations(Double lat, Double lon, Double km_away){
        mtaSingleton singleton = mtaSingleton.INSTANCE.getInstance();
        MTA mta = singleton.getMta();
        ArrayList<Subway.Stop> subwayStops = singleton.getSubwayStops();
        ArrayList<Bus.Stop> busStops = singleton.getBusStops();

        ArrayList<Station> stations = new ArrayList<Station>();

        for(Subway.Stop s : subwayStops){
            Double d = haversine(s.getLatitude(), s.getLongitude(), lat, lon);
            if(d <= km_away){
                Station station = new Station(s.getLongitude(), s.getLatitude(), s.getStopName(), s.getStopID(), s.getRoutes(), "SUBWAY", d);
                stations.add(station);
            }
            
        }

        for(Bus.Stop s : busStops){
            Double d = haversine(s.getLatitude(), s.getLongitude(), lat, lon);
            if(d <= km_away){
                Station station = new Station(s.getLongitude(), s.getLatitude(), s.getStopName(), Integer.toString(s.getStopID()), s.getRoutes(), "BUS", d);
                stations.add(station);
            }
        }

        Collections.sort(stations);

        for(Station s: stations){
            System.out.println(s.getStationName() + " " + s.getDistance() + "\n");
        }

        return stations;
        
    }



}