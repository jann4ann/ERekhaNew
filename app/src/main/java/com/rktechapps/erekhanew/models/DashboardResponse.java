package com.rktechapps.erekhanew.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashboardResponse {
    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private List<DashboardData> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DashboardData> getData() {
        return data;
    }

    public void setData(List<DashboardData> data) {
        this.data = data;
    }

    public class DashboardData{
        @SerializedName("Staff")
        String Staff;
        @SerializedName("GuestLabour")
        Integer GuestLabour;
        @SerializedName("Sponsers")
        Integer Sponsers;
        @SerializedName("Owners")
        Integer Owners;
        @SerializedName("Property")
        Integer Property;
        @SerializedName("PendingGeoTag")
        Integer PendingGeoTag;

        public String getStaff() {
            return Staff;
        }

        public void setStaff(String staff) {
            Staff = staff;
        }

        public Integer getGuestLabour() {
            return GuestLabour;
        }

        public void setGuestLabour(Integer guestLabour) {
            GuestLabour = guestLabour;
        }

        public Integer getSponsers() {
            return Sponsers;
        }

        public void setSponsers(Integer sponsers) {
            Sponsers = sponsers;
        }

        public Integer getOwners() {
            return Owners;
        }

        public void setOwners(Integer owners) {
            Owners = owners;
        }

        public Integer getProperty() {
            return Property;
        }

        public void setProperty(Integer property) {
            Property = property;
        }

        public Integer getPendingGeoTag() {
            return PendingGeoTag;
        }

        public void setPendingGeoTag(Integer pendingGeoTag) {
            PendingGeoTag = pendingGeoTag;
        }
    }


}
