package com.hungnv.tourbooking.dto;

public class CreateVnPayPaymentResponse {
    private String paymentUrl;

    public CreateVnPayPaymentResponse(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }
}
