package com.hungnv.tourbooking.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "tours_detail")
public class ToursDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id")
    private Tours tours;

    @Column(columnDefinition = "TEXT")
    private String overview;

    @Column(columnDefinition = "TEXT")
    private String childrenPolicy;

    @Column(columnDefinition = "TEXT")
    private String bookingGuide;

    @Column(columnDefinition = "TEXT")
    private String payment;

    @Column(columnDefinition = "TEXT")
    private String cancellationPolicy;

    @Column(columnDefinition = "TEXT")
    private String termsNotes;

    @Column(columnDefinition = "TEXT")
    private String additionalInfo;

    public ToursDetail() {
    }

    public ToursDetail(Long id, Tours tours, String overview, String childrenPolicy, String bookingGuide, String payment, String cancellationPolicy, String termsNotes, String additionalInfo) {
        this.id = id;
        this.tours = tours;
        this.overview = overview;
        this.childrenPolicy = childrenPolicy;
        this.bookingGuide = bookingGuide;
        this.payment = payment;
        this.cancellationPolicy = cancellationPolicy;
        this.termsNotes = termsNotes;
        this.additionalInfo = additionalInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tours getTours() {
        return tours;
    }

    public void setTours(Tours tours) {
        this.tours = tours;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getChildrenPolicy() {
        return childrenPolicy;
    }

    public void setChildrenPolicy(String childrenPolicy) {
        this.childrenPolicy = childrenPolicy;
    }

    public String getBookingGuide() {
        return bookingGuide;
    }

    public void setBookingGuide(String bookingGuide) {
        this.bookingGuide = bookingGuide;
    }

    public String getCancellationPolicy() {
        return cancellationPolicy;
    }

    public void setCancellationPolicy(String cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getTermsNotes() {
        return termsNotes;
    }

    public void setTermsNotes(String termsNotes) {
        this.termsNotes = termsNotes;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public String toString() {
        return "ToursDetail{" +
            "id=" + id +
            ", tours=" + tours +
            ", overview='" + overview + '\'' +
            ", childrenPolicy='" + childrenPolicy + '\'' +
            ", bookingGuide='" + bookingGuide + '\'' +
            ", payment='" + payment + '\'' +
            ", cancellationPolicy='" + cancellationPolicy + '\'' +
            ", termsNotes='" + termsNotes + '\'' +
            ", additionalInfo='" + additionalInfo + '\'' +
            '}';
    }
}
