package com.rktechapps.erekhanew.models;

import com.google.gson.annotations.SerializedName;

public class CountsResponseModel {
    @SerializedName("counts")
    private int count;

    public int getCount() {
        return count;
    }
}
