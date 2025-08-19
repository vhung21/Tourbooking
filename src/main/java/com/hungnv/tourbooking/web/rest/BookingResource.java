package com.hungnv.tourbooking.web.rest;

import com.hungnv.tourbooking.domain.Booking;
import com.hungnv.tourbooking.domain.BookingStatus;
import com.hungnv.tourbooking.domain.TourBookingDetail;
import com.hungnv.tourbooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingResource {
    @Autowired
    BookingService bookingService;

    @PostMapping
    public Booking createBooking(@RequestBody TourBookingDetail tourBookingDetail) {
        return bookingService.createTourBooking(tourBookingDetail);
    }

    @GetMapping("/{id}")
    public Booking getBooking(@PathVariable Long id) {
        return bookingService.getBooking(id);
    }

    @GetMapping("/customer/{customerId}")
    public List<Booking> getBookingsByCustomer(@PathVariable Long customerId) {
        return bookingService.getBookingsByUser(customerId);
    }

    @PutMapping("/{id}/status")
    public Booking updateStatus(@PathVariable Long id, @RequestParam BookingStatus status) {
        return bookingService.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
    }
}
