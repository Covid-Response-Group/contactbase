package org.covid19.contactbase.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class Contact implements Serializable {

    @NotBlank
    private String deviceId;

    @NotNull
    private List<SpatialTemporalStamp> spatialTemporalStamps;

    public Contact() {

    }

    public Contact(@NotBlank String deviceId, @NotNull List<SpatialTemporalStamp> spatialTemporalStamps) {
        this.deviceId = deviceId;
        this.spatialTemporalStamps = spatialTemporalStamps;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public List<SpatialTemporalStamp> getSpatialTemporalStamps() {
        return spatialTemporalStamps;
    }

    public void setSpatialTemporalStamps(List<SpatialTemporalStamp> spatialTemporalStamps) {
        this.spatialTemporalStamps = spatialTemporalStamps;
    }
}
