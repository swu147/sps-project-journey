package data;

import dev.katsute.onemta.subway.Subway;

public final class subwayVehicle {
    private final String vehicleID;
    private final String status;
    private final String stopID;
    private final String routeID;
    private boolean express;

    public subwayVehicle(Subway.Vehicle v){
        this.vehicleID = v.getVehicleID();
        this.status = v.getCurrentStatus();
        this.stopID = v.getStopID();
        this.routeID = v.getRouteID();
        this.express = v.isExpress();
    }
}
