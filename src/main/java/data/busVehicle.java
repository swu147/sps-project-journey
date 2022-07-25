package data;

import dev.katsute.onemta.bus.Bus;
import java.util.Date;

public final class busVehicle {
    private final Double lon;
    private final Double lat;
    private final String routeID;
    private final Integer stopID;
    private final String stopName;
    private final String destinationName;
    private final Date aimedArrivalTime;
    private final Date expectedArrivalTime;
    private final Integer distanceFromStop;
    private final Integer stopsAway;

    // public final Double getLon(){
    //     return lon;
    // }

    // public final Double getLat(){
    //     return lon;
    // }

    public busVehicle(Bus.Vehicle v){
        this.lon = v.getLongitude();
        this.lat = v.getLatitude();
        this.routeID = v.getRouteID();
        this.stopID = v.getStopID();
        this.stopName = v.getStopName();
        this.destinationName = v.getStopName();
        this.aimedArrivalTime = v.getAimedArrivalTime();
        this.expectedArrivalTime = v.getExpectedArrivalTime();
        this.distanceFromStop = v.getDistanceFromStop();
        this.stopsAway = v.getStopsAway();
    }
}
