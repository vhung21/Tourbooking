package com.hungnv.tourbooking.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "tour_inclusions")
public class ToursInclusion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tour_id", nullable = false)
    private Tours tours;

    @Column(name = "tours_inclusion_name")
    private String toursInclusionName;

    @Column(name = "tours_included")
    private Integer toursIncluded;

    public ToursInclusion() {
    }

    public ToursInclusion(Long id, Tours tours, String toursInclusionName, Integer toursIncluded) {
        this.id = id;
        this.tours = tours;
        this.toursInclusionName = toursInclusionName;
        this.toursIncluded = toursIncluded;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tours getTour() {
        return tours;
    }

    public void setTour(Tours tours) {
        this.tours = tours;
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

    @Override
    public String toString() {
        return "ToursInclusion{" +
            "id=" + id +
            ", tour=" + tours +
            ", toursInclusionName='" + toursInclusionName + '\'' +
            ", toursIncluded=" + toursIncluded +
            '}';
    }
}
