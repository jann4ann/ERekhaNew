package com.rktechapps.erekhanew.models;

import com.google.gson.annotations.SerializedName;

public class ResendOtpResponse {
    @SerializedName("userid")
    String userid;
    @SerializedName("Mobile")
    String Mobile;
    @SerializedName("OTP")
    String OTP;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }
}
