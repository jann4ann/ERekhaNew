package com.rktechapps.erekhanew.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HelpDeskResponse{
        @SerializedName("status")
        private Integer status;

        @SerializedName("Data")
        private List<HelpDeskData> data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<HelpDeskData> getData() {
        return data;
    }

    public void setData(List<HelpDeskData> data) {
        this.data = data;
    }

    public class HelpDeskData{

            @SerializedName("Name")
            private String name;
            @SerializedName("Mobile")
            private String mobile;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
