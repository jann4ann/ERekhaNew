package com.rktechapps.erekhanew.models;

import com.google.gson.annotations.SerializedName;
import com.rktechapps.erekhanew.datasources.Religion;

import java.util.List;

public class ReligionResponseModel {
    @SerializedName("fetch_religion_caste_array")
    private List<Religion> religion_list;
}
