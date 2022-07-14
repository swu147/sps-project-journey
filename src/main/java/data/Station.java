package data;

public final class Station {

    private final double longitude;
    private final double latitude;
    private final String stationName;
    private final Integer id;
    // private final String lineName;
    // private final Vehicle[] listOfVehicles;

  public Station(double longitude, double latitude, String stationName, Integer id) {
    this.longitude = longitude;
    this.latitude = latitude;
    this.stationName = stationName;
    this.id = id;
    // this.lineName = lineName;
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
  public Integer getId(){
    return id;
    }
//   public String getLineName(){
//     return lineName;
//     }
//   public Vehicle[] getListOfVehicles(){
//     return listOfVehicles;
//     }

}