package com.hungnv.tourbooking.dto;

import java.io.Serializable;

public class MonthlyRevenueDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String month;
    private Double revenue;

    public MonthlyRevenueDTO() {}

    public MonthlyRevenueDTO(String month, Double revenue) {
        this.month = month;
        this.revenue = revenue;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }
}
