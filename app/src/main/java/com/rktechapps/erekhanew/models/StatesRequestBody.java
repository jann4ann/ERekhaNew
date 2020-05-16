package com.rktechapps.erekhanew.models;

import android.content.Intent;

import com.google.gson.annotations.SerializedName;

public class StatesRequestBody {

    @SerializedName("md5")
    private String md5;
    @SerializedName("page")
    private Integer page;

    public StatesRequestBody(String md5, Integer page) {
        this.md5 = md5;
        this.page = page;
    }
}
