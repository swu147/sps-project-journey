package data;

public final class Station {

    private final double longitude;
    private final double latitude;
    private final String stationName;
    private final String id;
    private final String[] routes;
    private final String type;
    // private final Vehicle[] listOfVehicles;

  public Station(double longitude, double latitude, String stationName, String id, String[] routes, String type) {
    this.longitude = longitude;
    this.latitude = latitude;
    this.stationName = stationName;
    this.id = id;
    this.routes = routes;
    this.type = type;
    // this.listOfVehicles = listOfVehicles;
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
//   public Vehicle[] getListOfVehicles(){
//     return listOfVehicles;
//     }

}