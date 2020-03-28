package org.covid19.contactbase.model;

import java.io.Serializable;

public class SpatialTemporalStamp implements Serializable {

    private Long distance;

    private String geohash;

    private String hourStamp;

    public SpatialTemporalStamp() {

    }

    public SpatialTemporalStamp(Long distance, String geohash, String hourStamp) {
        this.distance = distance;
        this.geohash = geohash;
        this.hourStamp = hourStamp;
    }

    public static SpatialTemporalStamp fromString(String stampString) {
        String[] components = stampString.split(":");

        SpatialTemporalStamp spatialTemporalStamp = new SpatialTemporalStamp();

        if (components.length == 3) {
            spatialTemporalStamp.setGeohash(components[0]);
            spatialTemporalStamp.setDistance(Long.parseLong(components[1]));
            spatialTemporalStamp.setHourStamp(components[2]);
        }

        return spatialTemporalStamp;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public String getGeohash() {
        return geohash;
    }

    public void setGeohash(String geohash) {
        this.geohash = geohash;
    }

    public String getHourStamp() {
        return hourStamp;
    }

    public void setHourStamp(String hourStamp) {
        this.hourStamp = hourStamp;
    }

    public String toString() {
        return geohash + ":" + distance + ":" + hourStamp;
    }
}
