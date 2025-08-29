package com.hungnv.tourbooking.service.impl;


import com.hungnv.tourbooking.domain.Tours;
import com.hungnv.tourbooking.dto.TourDTO;
import com.hungnv.tourbooking.mapper.TourMapper;
import com.hungnv.tourbooking.payload.ResponseObject;
import com.hungnv.tourbooking.repository.TourRepository;
import com.hungnv.tourbooking.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TourServiceImpl implements TourService {
    @Autowired
    TourRepository tourRepository;

    @Autowired
    TourMapper tourMapper;

    public ResponseEntity<ResponseObject> findAll(){
        try{
            List<Tours> Tours = tourRepository.findAll();
            List<TourDTO> tourDTOS = new ArrayList<>();
            for (Tours Tour : Tours) {
                tourDTOS.add(tourMapper.mapToDTO(Tour));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "Get all tour successfully", tourDTOS));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject("failed", "An error occurred while fetching tour list", null));
        }
    }

    public ResponseEntity<ResponseObject> getTopTours(){
        try{
            List<Tours> Tours = tourRepository.findTopToursByWeightedScore(PageRequest.of(0, 9));
            List<TourDTO> tourDTOS = new ArrayList<>();
            for (Tours Tour : Tours) {
                tourDTOS.add(tourMapper.mapToDTO(Tour));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "Get all tour successfully", tourDTOS));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject("failed", "An error occurred while fetching tour list", null));
        }
    }

    public ResponseEntity<ResponseObject> findById(long id){
        try{
            Optional<Tours> Tour = tourRepository.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "Get tour by id successfully", tourMapper.mapToDTO(Tour.get())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject("failed", "An error occurred while fetching tour by id", id));
        }
    }

    public ResponseEntity<ResponseObject> creatTour(TourDTO tourDTO){
        try{
            if(tourRepository.existsByTourName(tourDTO.getTourName()))
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("failed", "Tour name already exists", null)
                );
            }

            Tours Tour = new Tours();
            Tour.setTourName(tourDTO.getTourName());
            Tour.setDescription(tourDTO.getDescription());
            Tour.setPrice(tourDTO.getPrice());
            Tour.setStartDate(tourDTO.getStartDate());
            Tour.setEndDate(tourDTO.getEndDate());
            Tour.setLocation(tourDTO.getLocation());
            Tour.setLocation(tourDTO.getLocation());
            Tour.setTransportation(tourDTO.getTransportation());
            Tour.setImageUrl(tourDTO.getImageUrl());
            Tour.setCreatedBy(tourDTO.getCreatedBy());
            Tour.setAverageRating(0.0);
            Tour.setReviewCount(0);
            tourRepository.save(Tour);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("success", "Create tour successfully", tourMapper.mapToDTO(Tour)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject("failed", "An error occurred while creating tour", null));
        }
    }

    public ResponseEntity<ResponseObject> updateTour(TourDTO tourDTO){
        try{
            Optional<Tours> existingTour = tourRepository.findById(tourDTO.getId());
            if(existingTour.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("failed", "Tour not found", null));
            }
            if(tourRepository.existsByTourName(tourDTO.getTourName()))
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("failed", "Tour name already exists", null)
                );
            }
            Tours Tour = existingTour.get();
            Tour.setTourName(tourDTO.getTourName());
            Tour.setDescription(tourDTO.getDescription());
            Tour.setPrice(tourDTO.getPrice());
            Tour.setStartDate(tourDTO.getStartDate());
            Tour.setEndDate(tourDTO.getEndDate());
            Tour.setLocation(tourDTO.getLocation());
            Tour.setLocation(tourDTO.getLocation());
            Tour.setTransportation(tourDTO.getTransportation());
            Tour.setImageUrl(tourDTO.getImageUrl());
            Tour.setCreatedBy(tourDTO.getCreatedBy());
            Tour.setAverageRating(0.0);
            Tour.setReviewCount(0);
            tourRepository.save(Tour);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("success", "Update tour successfully", tourMapper.mapToDTO(Tour)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject("failed", "An error occurred while updating tour", null));
        }
    }

    public ResponseEntity<ResponseObject> deleteProductById(long id){
        try {
            Optional<Tours> existingTour = tourRepository.findById(id);
            if(existingTour.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("failed", "Tour not found", null));
            }
            tourRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "Deleted tour successfully", existingTour.get()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject("failed", "An error occurred while deleting tour", null));
        }
    }

    public ResponseEntity<ResponseObject> searchTours(String keyword) {
        try{
            List<Tours> Tours = tourRepository.findByTourNameContainingIgnoreCase(keyword);
            List<TourDTO> tourDTOS = new ArrayList<>();
            for (Tours Tour : Tours) {
                tourDTOS.add(tourMapper.mapToDTO(Tour));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "Search tour successfully", tourDTOS));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject("failed", "An error occurred while search tour", null));
        }
    }
}
