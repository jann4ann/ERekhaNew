package com.rktechapps.erekhanew.models;

import com.google.gson.annotations.SerializedName;

public class FetchPoliceStationRequestBody {
    @SerializedName("md5")
    String md5Info;

    @SerializedName("search")
    String searchQuery;

    public FetchPoliceStationRequestBody(String md5Info, String searchQuery) {
        this.md5Info = md5Info;
        this.searchQuery = searchQuery;
    }
}
