package com.hungnv.tourbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TourDetailDTO {
    private String overview;
    private String childrenPolicy;
    private String bookingGuide;
    private String payment;
    private String cancellationPolicy;
    private String termsNotes;
    private String additionalInfo;

    public TourDetailDTO() {
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

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getCancellationPolicy() {
        return cancellationPolicy;
    }

    public void setCancellationPolicy(String cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getTermsNotes() {
        return termsNotes;
    }

    public void setTermsNotes(String termsNotes) {
        this.termsNotes = termsNotes;
    }
}
