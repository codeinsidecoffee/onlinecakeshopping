
package com.mrlonewolfer.onlinecakeshopping.Model;

import java.util.List;

import com.google.gson.annotations.Expose;


@SuppressWarnings("unused")
public class DashboardInfo {

    @Expose
    private List<List<String>> dashboard;

    public List<List<String>> getDashboard() {
        return dashboard;
    }

    public void setDashboard(List<List<String>> dashboard) {
        this.dashboard = dashboard;
    }

}
