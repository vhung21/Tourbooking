package com.hungnv.tourbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TourInclusionDTO {
    private String toursInclusionName;
    private Integer toursIncluded;

    public TourInclusionDTO() {
    }

    public String getToursInclusionName() {
        return toursInclusionName;
    }

    public void setToursInclusionName(String toursInclusionName) {
        this.toursInclusionName = toursInclusionName;
    }

    public Integer getToursIncluded() {
        return toursIncluded;
    }

    public void setToursIncluded(Integer toursIncluded) {
        this.toursIncluded = toursIncluded;
    }
}
