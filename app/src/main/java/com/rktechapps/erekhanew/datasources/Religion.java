package com.rktechapps.erekhanew.datasources;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Religion {
    @SerializedName("religion_id")
    @PrimaryKey
    private Integer religion_id;

   @SerializedName("religion_name")
   private String religion_name;

}
