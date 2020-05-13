package com.rktechapps.erekhanew.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class RegisterPoliceRequestBody {
    @SerializedName("lo")
    List<PoliceMen> lo;

    public RegisterPoliceRequestBody(List<PoliceMen> lo) {
        this.lo = lo;
    }

    public List<PoliceMen> getLo() {
        return lo;
    }

    public static class PoliceMen
    {
        @SerializedName("Name")
        String Name;
        @SerializedName("Mobile")
        String Mobile;
        @SerializedName("Penno")
        String Penno;
        @SerializedName("Address")
        String Address;
        @SerializedName("Emailid")
        String Emailid;
        @SerializedName("Password")
        String Password;
        @SerializedName("PoliceStation")
        Integer PoliceStation;
        @SerializedName("PoliceDistrict")
        Integer PoliceDistrict;

        public String getName() {
            return Name;
        }

        public String getMobile() {
            return Mobile;
        }

        public String getPenno() {
            return Penno;
        }

        public String getAddress() {
            return Address;
        }

        public String getEmailid() {
            return Emailid;
        }

        public String getPassword() {
            return Password;
        }

        public Integer getPoliceStation() {
            return PoliceStation;
        }

        public Integer getPoliceDistrict() {
            return PoliceDistrict;
        }

        public PoliceMen(String Name, String Mob, String PenNo, String address,
                         String MailId, String password, Integer PoliceStation, Integer PoliceDistrict) {
            this.Name = Name;
            this.Mobile = Mob;
            this.Penno = PenNo;
            this.Address = address;
            this.Emailid = MailId;
            this.Password = password;
            this.PoliceStation = PoliceStation;
            this.PoliceDistrict = PoliceDistrict;
        }
    }

}
