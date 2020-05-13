package com.rktechapps.erekhanew.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchPoliceStationResponse {
    @SerializedName("feach_Police_station_array")
    List<PoliceStation> policeStations;

    public List<PoliceStation> getPoliceStations() {
        return policeStations;
    }

    public static class PoliceStation
    {
        @SerializedName("ps_id")
        String psId;

        @SerializedName("ps_name")
        String psName;

        public String getPsId() {
            return psId;
        }

        public String getPsName() {
            return psName;
        }
    }
}
