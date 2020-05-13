package com.rktechapps.erekhanew.models;

import com.google.gson.annotations.SerializedName;

public class ValidateOtpRequestBody {

        public ValidateOtpRequestBody(String uid, String mobileNumber,String md5) {
            this.uid = uid;
            this.mob = mobileNumber;
            this.md5 = md5;
        }

        @SerializedName("uid")
        String uid;

        @SerializedName("mob")
        String mob;

        @SerializedName("md5")
        String md5;


}
