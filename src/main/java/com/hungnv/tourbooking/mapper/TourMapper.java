package com.hungnv.tourbooking.mapper;

import com.hungnv.tourbooking.domain.Tours;
import com.hungnv.tourbooking.domain.ToursDetail;
import com.hungnv.tourbooking.dto.TourDTO;
import com.hungnv.tourbooking.dto.TourDetailDTO;
import com.hungnv.tourbooking.dto.TourInclusionDTO;
import com.hungnv.tourbooking.dto.TourItineraryDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TourMapper {
    public TourDTO mapToDTO(Tours Tour) {
        TourDTO tourDTO = new TourDTO();
        tourDTO.setId(Tour.getId());
        tourDTO.setTourName(Tour.getTourName());
        tourDTO.setDescription(Tour.getDescription());
        tourDTO.setPrice(Tour.getPrice());
        tourDTO.setStartDate(Tour.getStartDate());
        tourDTO.setEndDate(Tour.getEndDate());
        tourDTO.setDepartures(Tour.getDepartures());
        tourDTO.setDestination(Tour.getDestination());
        tourDTO.setTransportation(Tour.getTransportation());
        tourDTO.setImageUrl(Tour.getImageUrl());
        tourDTO.setCreatedBy(Tour.getCreatedBy());
        tourDTO.setAverageRating(Tour.getAverageRating());
        tourDTO.setReviewCount(Tour.getReviewCount());
        tourDTO.setViewCount(Tour.getViewCount());
        tourDTO.setSeason(Tour.getSeason());

        if (Tour.getDetails() != null) {
            ToursDetail detail = Tour.getDetails();

            TourDetailDTO detailDTO = new TourDetailDTO();
            detailDTO.setOverview(detail.getOverview());
            detailDTO.setChildrenPolicy(detail.getChildrenPolicy());
            detailDTO.setBookingGuide(detail.getBookingGuide());
            detailDTO.setPayment(detail.getPayment());
            detailDTO.setCancellationPolicy(detail.getCancellationPolicy());
            detailDTO.setTermsNotes(detail.getTermsNotes());
            detailDTO.setAdditionalInfo(detail.getAdditionalInfo());

            tourDTO.setDetail(detailDTO);
        } else {
            tourDTO.setDetail(null);
        }

        if (Tour.getItineraries() != null && !Tour.getItineraries().isEmpty()) {
            List<TourItineraryDTO> itineraryDTOs = Tour.getItineraries().stream().map(it -> {
                TourItineraryDTO dto = new TourItineraryDTO();
                dto.setToursItineraryTitle(it.getToursItineraryTitle());
                dto.setToursItineraryDescription(it.getToursItineraryDescription());
                return dto;
            }).collect(Collectors.toList());

            tourDTO.setItineraries(itineraryDTOs);
        } else {
            tourDTO.setItineraries(new ArrayList<>());
        }

        if (Tour.getInclusions() != null && !Tour.getInclusions().isEmpty()) {
            List<TourInclusionDTO> inclusionDTOs = Tour.getInclusions().stream().map(inc -> {
                TourInclusionDTO dto = new TourInclusionDTO();
                dto.setToursInclusionName(inc.getToursInclusionName());
                dto.setToursIncluded(inc.getToursIncluded());
                return dto;
            }).collect(Collectors.toList());

            tourDTO.setInclusions(inclusionDTOs);
        } else {
            tourDTO.setInclusions(new ArrayList<>());
        }
        return tourDTO;
    }
}
