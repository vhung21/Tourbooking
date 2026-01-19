package com.hungnv.tourbooking.dto;

import com.hungnv.tourbooking.domain.Season;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardStatisticsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long totalTours;
    private Long totalHotels;
    private Long totalBookings;
    private Double totalRevenue;
    private Map<Season, Long> toursBySeason;
    private List<MonthlyRevenueDTO> monthlyRevenue;
    private List<TourStatDTO> topTours;
    private List<HotelStatDTO> topHotels;
    private List<TourTimelineDTO> activeTourTimelines;

    public DashboardStatisticsDTO() {
        this.toursBySeason = new HashMap<>();
    }

    public Long getTotalTours() {
        return totalTours;
    }

    public void setTotalTours(Long totalTours) {
        this.totalTours = totalTours;
    }

    public Long getTotalHotels() {
        return totalHotels;
    }

    public void setTotalHotels(Long totalHotels) {
        this.totalHotels = totalHotels;
    }

    public Long getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(Long totalBookings) {
        this.totalBookings = totalBookings;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Map<Season, Long> getToursBySeason() {
        return toursBySeason;
    }

    public void setToursBySeason(Map<Season, Long> toursBySeason) {
        this.toursBySeason = toursBySeason;
    }

    public List<MonthlyRevenueDTO> getMonthlyRevenue() {
        return monthlyRevenue;
    }

    public void setMonthlyRevenue(List<MonthlyRevenueDTO> monthlyRevenue) {
        this.monthlyRevenue = monthlyRevenue;
    }

    public List<TourStatDTO> getTopTours() {
        return topTours;
    }

    public void setTopTours(List<TourStatDTO> topTours) {
        this.topTours = topTours;
    }

    public List<HotelStatDTO> getTopHotels() {
        return topHotels;
    }

    public void setTopHotels(List<HotelStatDTO> topHotels) {
        this.topHotels = topHotels;
    }

    public List<TourTimelineDTO> getActiveTourTimelines() {
        return activeTourTimelines;
    }

    public void setActiveTourTimelines(List<TourTimelineDTO> activeTourTimelines) {
        this.activeTourTimelines = activeTourTimelines;
    }
}
