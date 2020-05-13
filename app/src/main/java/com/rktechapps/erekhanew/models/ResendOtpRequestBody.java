package com.rktechapps.erekhanew.models;

import com.google.gson.annotations.SerializedName;

public class ResendOtpRequestBody {

    @SerializedName("uid")
    String uid;
    @SerializedName("mob")
    String mob;
    @SerializedName("md5")
    String md5;

    public ResendOtpRequestBody(String uid, String mob, String md5) {
        this.uid = uid;
        this.mob = mob;
        this.md5 = md5;
    }
}
