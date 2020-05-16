package com.rktechapps.erekhanew.datasources;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Districts {
    @SerializedName("Di_name")
    private String district_name;
    @PrimaryKey
    @SerializedName("dl_id")
    private Integer district_id;
    @SerializedName("SE_id")
    private String dist_state_id;

    public Districts(String district_name, Integer district_id, String dist_state_id) {
        this.district_name = district_name;
        this.district_id = district_id;
        this.dist_state_id = dist_state_id;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public Integer getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(Integer district_id) {
        this.district_id = district_id;
    }

    public String getDist_state_id() {
        return dist_state_id;
    }

    public void setDist_state_id(String dist_state_id) {
        this.dist_state_id = dist_state_id;
    }
}
