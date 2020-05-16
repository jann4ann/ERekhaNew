package com.rktechapps.erekhanew.models;

import com.google.gson.annotations.SerializedName;
import com.rktechapps.erekhanew.datasources.Districts;

import java.util.List;

public class DistrictsResponseModel {

    @SerializedName("fetch_District_array")
    private List<Districts> districts_list;

    public List<Districts> getDistricts_list() {
        return districts_list;
    }

}
