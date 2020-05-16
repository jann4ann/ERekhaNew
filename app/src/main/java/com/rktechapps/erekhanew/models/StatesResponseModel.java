package com.rktechapps.erekhanew.models;

import com.google.gson.annotations.SerializedName;
import com.rktechapps.erekhanew.datasources.States;

import java.util.List;

public class StatesResponseModel {

    @SerializedName("fetch_state_array")
    private List<States> statesList;

    public List<States> getStatesList() {
        return statesList;
    }
}
