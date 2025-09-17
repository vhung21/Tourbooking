package com.hungnv.tourbooking.service;

import com.hungnv.tourbooking.domain.Location;
import com.hungnv.tourbooking.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;

    public List<Location> searchLocation(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return List.of();
        }
        return locationRepository.findActiveByNameContainingIgnoreCase(keyword);
    }

    public Location createLocation(Location location) {
        location.setActive(true);
        return locationRepository.save(location);
    }
}
