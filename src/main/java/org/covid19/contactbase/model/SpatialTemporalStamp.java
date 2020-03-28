package org.covid19.contactbase.model;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class SpatialTemporalStamp implements Serializable {

    @NotBlank
    private String geohash;

    @NotBlank
    private String dateStamp;

    public SpatialTemporalStamp() {

    }

    public SpatialTemporalStamp(@NotBlank String geohash, @NotBlank String dateStamp) {
        this.geohash = geohash;
        this.dateStamp = dateStamp;
    }

    public String getGeohash() {
        return geohash;
    }

    public void setGeohash(String geohash) {
        this.geohash = geohash;
    }

    public String getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(String dateStamp) {
        this.dateStamp = dateStamp;
    }

    @Override
    public String toString() {
        return this.geohash + "/" + this.dateStamp;
    }
}
