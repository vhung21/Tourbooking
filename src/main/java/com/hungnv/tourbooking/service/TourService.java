package com.hungnv.tourbooking.service;

import com.hungnv.tourbooking.dto.TourDTO;
import com.hungnv.tourbooking.payload.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface TourService {
    ResponseEntity<ResponseObject> findAll();

    ResponseEntity<ResponseObject> findById(long id);

    ResponseEntity<ResponseObject> creatTour(TourDTO tourDTO);

    ResponseEntity<ResponseObject> updateTour(TourDTO tourDTO);

    ResponseEntity<ResponseObject> deleteProductById(long id);

    ResponseEntity<ResponseObject> searchTours(String keyword);

    ResponseEntity<ResponseObject> getTopTours();
}
