package org.covid19.contactbase.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Contact implements Serializable {

    @NotBlank
    private String deviceId;

    // SpatialTemporalStamp has a format of L7Geohash:Distance:HourStamp
    // Eg. gbsuv7z:12:2020032712
    @NotNull
    private String[] spatialTemporalStamps;

    public Contact() {

    }

    public Contact(String deviceId, String[] spatialTemporalStamps) {
        this.deviceId = deviceId;
        this.spatialTemporalStamps = spatialTemporalStamps;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String[] getSpatialTemporalStamps() {
        return spatialTemporalStamps;
    }

    public void setSpatialTemporalStamps(String[] spatialTemporalStamps) {
        this.spatialTemporalStamps = spatialTemporalStamps;
    }
}
