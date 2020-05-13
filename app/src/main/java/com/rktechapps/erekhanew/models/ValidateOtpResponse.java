package com.rktechapps.erekhanew.models;

import com.google.gson.annotations.SerializedName;

public class ValidateOtpResponse {
    @SerializedName("userid")
    String Userid;
    @SerializedName("Name")
    String Name;
    @SerializedName("mobile")
    String Mobile;
    @SerializedName("Policestation")
    String Policestation;
    @SerializedName("Username")
    String Username;
    @SerializedName("Password")
    String Password;

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getPolicestation() {
        return Policestation;
    }

    public void setPolicestation(String policestation) {
        Policestation = policestation;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
