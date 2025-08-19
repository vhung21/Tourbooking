package com.hungnv.tourbooking.service;

import com.hungnv.tourbooking.domain.Booking;
import com.hungnv.tourbooking.domain.BookingStatus;
import com.hungnv.tourbooking.domain.TourBookingDetail;

import java.util.List;

public interface BookingService {
    Booking createTourBooking(TourBookingDetail tourBookingDetail);

    Booking getBooking(Long id);

    List<Booking> getBookingsByUser(Long customerId);

    Booking updateStatus(Long id, BookingStatus status);

    void cancelBooking(Long id);
}
