package com.hungnv.tourbooking.domain;

import jakarta.persistence.*;

@Entity
public class ToursItinerary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tour_id", nullable = false)
    private Tours tours;

    @Column(name = "tours_itinerary_title")
    private String toursItineraryTitle;

    @Column(columnDefinition = "TEXT", name = "tours_itinerary_description")
    private String toursItineraryDescription;

    public ToursItinerary() {
    }

    public ToursItinerary(Long id, Tours tours, String toursItineraryTitle, String toursItineraryDescription) {
        this.id = id;
        this.tours = tours;
        this.toursItineraryTitle = toursItineraryTitle;
        this.toursItineraryDescription = toursItineraryDescription;
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

    @Override
    public String toString() {
        return "ToursItinerary{" +
            "id=" + id +
            ", tour=" + tours +
            ", toursItineraryTitle='" + toursItineraryTitle + '\'' +
            ", toursItineraryDescription='" + toursItineraryDescription + '\'' +
            '}';
    }
}
