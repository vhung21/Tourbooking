package com.hungnv.tourbooking.web.rest;

import com.hungnv.tourbooking.domain.Location;
import com.hungnv.tourbooking.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location")
public class LocationResource {
    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<?> searchLocation(
        @RequestParam(name = "keyword", required = false) String keyword) {
        try {
            List<Location> points = locationService.searchLocation(keyword);
            return ResponseEntity.ok().body(points);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error searching departure points");
        }
    }

    @PostMapping
    public ResponseEntity<Location> createDeparturePoint(@RequestBody Location location) {
        try {
            Location savedPoint = locationService.createLocation(location);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPoint);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
