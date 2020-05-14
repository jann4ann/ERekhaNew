package com.rktechapps.erekhanew.models;

import com.google.gson.annotations.SerializedName;

public class HelpDeskRequestBody {
    @SerializedName("md5")
    private String md5;

    public HelpDeskRequestBody(String md5) {
        this.md5 = md5;
    }
}
