package com.hungnv.tourbooking.web.rest;

import com.hungnv.tourbooking.dto.TourDTO;
import com.hungnv.tourbooking.payload.ResponseObject;
import com.hungnv.tourbooking.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tours")
public class TourResource {
    @Autowired
    TourService tourService;

    @GetMapping
    public ResponseEntity<ResponseObject> getAllTours() {
        return tourService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getTour(@PathVariable Long id) {
        return tourService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createTour(@RequestBody TourDTO tourDTO) {
        return tourService.creatTour(tourDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateTour(@PathVariable Long id, @RequestBody TourDTO tourDTO) {
        tourDTO.setId(id);
        return tourService.updateTour(tourDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteTour(@PathVariable Long id) {
        return tourService.deleteProductById(id);
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseObject> searchTours(@RequestParam String keyword) {
        return tourService.searchTours(keyword);
    }

    @GetMapping("/getTopTours")
    public ResponseEntity<ResponseObject> getTopTours() {
        return tourService.getTopTours();
    }
}
