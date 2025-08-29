package com.hungnv.tourbooking.mapper;

import com.hungnv.tourbooking.domain.Tours;
import com.hungnv.tourbooking.dto.TourDTO;
import org.springframework.stereotype.Component;

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
        tourDTO.setLocation(Tour.getLocation());
        tourDTO.setTransportation(Tour.getTransportation());
        tourDTO.setImageUrl(Tour.getImageUrl());
        tourDTO.setCreatedBy(Tour.getCreatedBy());
        tourDTO.setAverageRating(Tour.getAverageRating());
        tourDTO.setReviewCount(Tour.getReviewCount());
        return tourDTO;
    }
}
