package com.rktechapps.erekhanew.datasources;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Countries {
    @PrimaryKey
    @SerializedName("cy_id")
    int cy_id;
    @SerializedName("cy_name")
    String cy_name;

    public int getCy_id() {
        return cy_id;
    }

    public void setCy_id(int cy_id) {
        this.cy_id = cy_id;
    }

    public String getCy_name() {
        return cy_name;
    }

    public void setCy_name(String cy_name) {
        this.cy_name = cy_name;
    }
}
