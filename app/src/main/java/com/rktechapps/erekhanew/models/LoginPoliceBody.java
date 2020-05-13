package com.rktechapps.erekhanew.models;

import com.google.gson.annotations.SerializedName;

public class LoginPoliceBody {


    @SerializedName("username")
    String username;
    @SerializedName("password")
    String password;
    @SerializedName("md5")
    String md5;
    public LoginPoliceBody(String username, String password, String md5) {
        this.username = username;
        this.password = password;
        this.md5 = md5;
    }



}
