package com.rktechapps.erekhanew.models;

import android.content.Intent;

public class DashboardModel {
    Integer count;
    String category;

    public DashboardModel(Integer count, String category) {
        this.count = count;
        this.category = category;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
