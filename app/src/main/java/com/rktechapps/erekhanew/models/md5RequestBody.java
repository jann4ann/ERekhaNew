package com.rktechapps.erekhanew.models;

import com.google.gson.annotations.SerializedName;

public class md5RequestBody {
    @SerializedName("md5")
    private String md5;

    public md5RequestBody(String md5) {
        this.md5 = md5;
    }
}
