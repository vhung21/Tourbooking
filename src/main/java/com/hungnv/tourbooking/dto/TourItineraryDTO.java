package com.hungnv.tourbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TourItineraryDTO {
    private String toursItineraryTitle;
    private String toursItineraryDescription;

    public TourItineraryDTO() {
    }

    public String getToursItineraryTitle() {
        return toursItineraryTitle;
    }

    public void setToursItineraryTitle(String toursItineraryTitle) {
        this.toursItineraryTitle = toursItineraryTitle;
    }

    public String getToursItineraryDescription() {
        return toursItineraryDescription;
    }

    public void setToursItineraryDescription(String toursItineraryDescription) {
        this.toursItineraryDescription = toursItineraryDescription;
    }
}
