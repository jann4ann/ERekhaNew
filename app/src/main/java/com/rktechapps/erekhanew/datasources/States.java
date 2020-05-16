package com.rktechapps.erekhanew.datasources;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
@Entity
public class States {
    @PrimaryKey
    @SerializedName("SE_id")
    int SE_id;
    @SerializedName("SE_name")
    String SE_name;
    @SerializedName("CY_id")
    int CY_id;

    public int getSE_id() {
        return SE_id;
    }

    public void setSE_id(int SE_id) {
        this.SE_id = SE_id;
    }

    public String getSE_name() {
        return SE_name;
    }

    public void setSE_name(String SE_name) {
        this.SE_name = SE_name;
    }

    public int getCY_id() {
        return CY_id;
    }

    public void setCY_id(int CY_id) {
        this.CY_id = CY_id;
    }
}
