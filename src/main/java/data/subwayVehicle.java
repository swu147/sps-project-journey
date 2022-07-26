package data;

import dev.katsute.onemta.subway.Subway;
import java.util.Date;

public final class subwayVehicle {
    private final String vehicleID;
    private final String status;
    private final String stopID;
    private final String routeID;
    private boolean express;
    private Date aimedArrivalTime;

    public subwayVehicle(Subway.Vehicle v){
        this.vehicleID = v.getVehicleID();
        this.status = v.getCurrentStatus();
        this.stopID = v.getStopID();
        this.routeID = v.getRouteID();
        this.express = v.isExpress();
        // this.aimedArrivalTime = v.asTripStop().getArrivalTime();
    }
}
