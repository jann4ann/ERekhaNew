package com.rktechapps.erekhanew.models;

import com.google.gson.annotations.SerializedName;

public class LoginPoliceResponse {


    @SerializedName("status")
    int status;
    @SerializedName("userid")
    String userid;
    @SerializedName("Name")
    String Name;
    @SerializedName("mobile")
    String mobile;
    @SerializedName("Policestation")
    String Policestation;
    @SerializedName("Pen_No")
    String Pen_No;
    @SerializedName("District")
    String District;
    @SerializedName("PoliceStationId")
    String PoliceStationId;
    @SerializedName("DistrictId")
    String DistrictId;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPolicestation() {
        return Policestation;
    }

    public void setPolicestation(String policestation) {
        Policestation = policestation;
    }

    public String getPen_No() {
        return Pen_No;
    }

    public void setPen_No(String pen_No) {
        Pen_No = pen_No;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getPoliceStationId() {
        return PoliceStationId;
    }

    public void setPoliceStationId(String policeStationId) {
        PoliceStationId = policeStationId;
    }

    public String getDistrictId() {
        return DistrictId;
    }

    public void setDistrictId(String districtId) {
        DistrictId = districtId;
    }
}
