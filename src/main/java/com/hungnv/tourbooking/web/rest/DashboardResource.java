package com.hungnv.tourbooking.web.rest;

import com.hungnv.tourbooking.dto.DashboardStatisticsDTO;
import com.hungnv.tourbooking.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardResource {

    private final DashboardService dashboardService;

    public DashboardResource(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/statistics")
    public ResponseEntity<DashboardStatisticsDTO> getStatistics() {
        DashboardStatisticsDTO statistics = dashboardService.getStatistics();
        return ResponseEntity.ok(statistics);
    }
}
