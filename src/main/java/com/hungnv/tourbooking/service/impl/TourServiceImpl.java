package com.hungnv.tourbooking.service.impl;


import com.hungnv.tourbooking.domain.Tours;
import com.hungnv.tourbooking.domain.ToursDetail;
import com.hungnv.tourbooking.domain.ToursInclusion;
import com.hungnv.tourbooking.domain.ToursItinerary;
import com.hungnv.tourbooking.dto.TourDTO;
import com.hungnv.tourbooking.dto.TourInclusionDTO;
import com.hungnv.tourbooking.dto.TourItineraryDTO;
import com.hungnv.tourbooking.mapper.TourMapper;
import com.hungnv.tourbooking.payload.ResponseObject;
import com.hungnv.tourbooking.repository.TourDetailRepository;
import com.hungnv.tourbooking.repository.TourRepository;
import com.hungnv.tourbooking.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TourServiceImpl implements TourService {
    @Autowired
    TourRepository tourRepository;

    @Autowired
    TourDetailRepository tourDetailRepository;

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
            Tour.setDepartures(tourDTO.getDepartures());
            Tour.setDestination(tourDTO.getDestination());
            Tour.setTransportation(tourDTO.getTransportation());
            Tour.setImageUrl(tourDTO.getImageUrl());
            Tour.setCreatedBy(tourDTO.getCreatedBy());
            Tour.setAverageRating(BigDecimal.valueOf(0.0));
            Tour.setReviewCount(0);

            ToursDetail detail = new ToursDetail();
            if (tourDTO.getDetail() != null) {
                detail.setTours(Tour);
                detail.setOverview(tourDTO.getDetail().getOverview());
                detail.setChildrenPolicy(tourDTO.getDetail().getChildrenPolicy());
                detail.setBookingGuide(tourDTO.getDetail().getBookingGuide());
                detail.setPayment(tourDTO.getDetail().getPayment());
                detail.setCancellationPolicy(tourDTO.getDetail().getCancellationPolicy());
                detail.setTermsNotes(tourDTO.getDetail().getTermsNotes());
                detail.setAdditionalInfo(tourDTO.getDetail().getAdditionalInfo());
            }
            Tour.setDetails(detail);

            List<ToursItinerary> toursItineraries = new ArrayList<>();
            if (tourDTO.getItineraries() != null) {
                for (TourItineraryDTO i : tourDTO.getItineraries()) {
                    ToursItinerary toursItinerary = new ToursItinerary();
                    toursItinerary.setTour(Tour);
                    toursItinerary.setToursItineraryTitle(i.getToursItineraryTitle());
                    toursItinerary.setToursItineraryDescription(i.getToursItineraryDescription());
                    toursItineraries.add(toursItinerary);
                }
            }
            Tour.setItineraries(toursItineraries);

            List<ToursInclusion> toursInclusions = new ArrayList<>();
            if (tourDTO.getInclusions() != null) {
                for (TourInclusionDTO i : tourDTO.getInclusions()) {
                    ToursInclusion toursInclusion = new ToursInclusion();
                    toursInclusion.setTour(Tour);
                    toursInclusion.setToursInclusionName(i.getToursInclusionName());
                    toursInclusion.setToursIncluded(i.getToursIncluded());
                    toursInclusions.add(toursInclusion);
                }
            }
            Tour.setInclusions(toursInclusions);

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
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Tour not found", null));
            }

            Tours tour = existingTour.get();
            tour.setTourName(tourDTO.getTourName());
            tour.setDescription(tourDTO.getDescription());
            tour.setPrice(tourDTO.getPrice());
            tour.setStartDate(tourDTO.getStartDate());
            tour.setEndDate(tourDTO.getEndDate());
            tour.setDepartures(tourDTO.getDepartures());
            tour.setDestination(tourDTO.getDestination());
            tour.setTransportation(tourDTO.getTransportation());
            tour.setImageUrl(tourDTO.getImageUrl());
            tour.setCreatedBy(tourDTO.getCreatedBy());

            ToursDetail detail = tour.getDetails();
            if (detail == null) {
                detail = new ToursDetail();
                detail.setTours(tour);
            }
            if (tourDTO.getDetail() != null) {
                detail.setOverview(tourDTO.getDetail().getOverview());
                detail.setChildrenPolicy(tourDTO.getDetail().getChildrenPolicy());
                detail.setBookingGuide(tourDTO.getDetail().getBookingGuide());
                detail.setPayment(tourDTO.getDetail().getPayment());
                detail.setCancellationPolicy(tourDTO.getDetail().getCancellationPolicy());
                detail.setTermsNotes(tourDTO.getDetail().getTermsNotes());
                detail.setAdditionalInfo(tourDTO.getDetail().getAdditionalInfo());
            }
            tour.setDetails(detail);

            if (tourDTO.getItineraries() != null) {
                tour.getItineraries().clear();
                for (TourItineraryDTO i : tourDTO.getItineraries()) {
                    ToursItinerary toursItinerary = new ToursItinerary();
                    toursItinerary.setTour(tour);
                    toursItinerary.setToursItineraryTitle(i.getToursItineraryTitle());
                    toursItinerary.setToursItineraryDescription(i.getToursItineraryDescription());
                    tour.getItineraries().add(toursItinerary);
                }
            }

            if (tourDTO.getInclusions() != null) {
                tour.getInclusions().clear();
                for (TourInclusionDTO i : tourDTO.getInclusions()) {
                    ToursInclusion toursInclusion = new ToursInclusion();
                    toursInclusion.setTour(tour);
                    toursInclusion.setToursInclusionName(i.getToursInclusionName());
                    toursInclusion.setToursIncluded(i.getToursIncluded());
                    tour.getInclusions().add(toursInclusion);
                }
            }

            tourRepository.save(tour);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("success", "Update tour successfully", tourMapper.mapToDTO(tour)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject("failed", "An error occurred while updating tour", null));
        }
    }

    public ResponseEntity<ResponseObject> deleteTourById(long id){
        if (!tourRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Tour not found", null));
        }
        try {
            tourRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "Deleted tour successfully", null));
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
