package com.rktechapps.erekhanew.models;

import com.google.gson.annotations.SerializedName;

import java.security.PrivateKey;

public class DashboardRequestBody {

    @SerializedName("uid")
    private String uid;
    @SerializedName("md5")
    private String md5;

    public DashboardRequestBody(String uid, String md5) {
        this.uid = uid;
        this.md5 = md5;
    }
}
