package com.rktechapps.erekhanew.models;

import androidx.annotation.NonNull;

public class ParticularAdapterModel {

    private int id;
    private String name;
    private int parent_id;

    public ParticularAdapterModel(int id, String name, int parent_id) {
        this.id = id;
        this.name = name;
        this.parent_id = parent_id;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }

    public ParticularAdapterModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }
}
