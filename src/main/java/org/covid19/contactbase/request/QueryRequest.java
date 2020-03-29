package org.covid19.contactbase.request;

import java.io.Serializable;
import java.util.List;

public class QueryRequest implements Serializable {

    private List<String> geoHashes;

    private String fromDateStamp;

    private String toDateStamp;

    public QueryRequest() {

    }

    public QueryRequest(List<String> geoHashes, String fromDateStamp, String toDateStamp) {
        this.geoHashes = geoHashes;
        this.fromDateStamp = fromDateStamp;
        this.toDateStamp = toDateStamp;
    }

    public List<String> getGeoHashes() {
        return geoHashes;
    }

    public void setGeoHashes(List<String> geoHashes) {
        this.geoHashes = geoHashes;
    }

    public String getFromDateStamp() {
        return fromDateStamp;
    }

    public void setFromDateStamp(String fromDateStamp) {
        this.fromDateStamp = fromDateStamp;
    }

    public String getToDateStamp() {
        return toDateStamp;
    }

    public void setToDateStamp(String toDateStamp) {
        this.toDateStamp = toDateStamp;
    }
}
