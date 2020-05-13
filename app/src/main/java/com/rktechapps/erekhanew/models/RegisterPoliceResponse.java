package com.rktechapps.erekhanew.models;

import com.google.gson.annotations.SerializedName;

public class RegisterPoliceResponse {
    @SerializedName("userid")
    int user_id;

   /* @SerializedName("Mobile")
    String mobileNumber;*/

    @SerializedName("status")
    int status;

    @SerializedName("OTP")
    String otp;


    public int getUser_id() {
        return user_id;
    }

    /*public String getMobileNumber() {
        return mobileNumber;
    }*/

    public int getStatus() {
        return status;
    }

    public String getOtp() {
        return otp;
    }
}
