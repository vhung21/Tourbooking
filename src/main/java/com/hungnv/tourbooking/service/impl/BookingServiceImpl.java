package com.hungnv.tourbooking.service.impl;

import com.hungnv.tourbooking.domain.*;
import com.hungnv.tourbooking.repository.*;
import com.hungnv.tourbooking.security.SecurityUtils;
import com.hungnv.tourbooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TourBookingDetailRepository tourBookingDetailRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Booking createTourBooking(TourBookingDetail tourBookingDetail) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findOneByLogin(authentication.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));

        Customer customer = customerRepository.findByUserId(user.getId())
            .orElseGet(() -> {
                Customer newCustomer = new Customer();
                newCustomer.setUser(user);
                newCustomer.setFullName(user.getFirstName() + " " + user.getLastName());
                return customerRepository.save(newCustomer);
            });

        Tours tours = tourRepository.findById(tourBookingDetail.getTours().getId())
            .orElseThrow(() -> new RuntimeException("Tour not found"));

        Booking booking = new Booking();
        booking.setCreatedAt(LocalDateTime.now());
        booking.setStatus(BookingStatus.valueOf("PENDING"));
        booking.setBookingType("TOUR");
        BigDecimal adultPrice = tours.getPrice()
            .multiply(BigDecimal.valueOf(tourBookingDetail.getNumberOfAdults()));
        BigDecimal childPrice = tours.getPrice()
            .multiply(BigDecimal.valueOf(tourBookingDetail.getNumberOfChildren()));
        BigDecimal totalPrice = adultPrice.add(childPrice);
        booking.setTotalPrice(totalPrice);
        booking.setCustomer(customer);

        TourBookingDetail detail = new TourBookingDetail();
        detail.setTours(tours);
        detail.setBooking(booking);
        detail.setNumberOfAdults(tourBookingDetail.getNumberOfAdults());
        detail.setNumberOfChildren(tourBookingDetail.getNumberOfChildren());


        booking.getTourBookingDetails().add(detail);
        return bookingRepository.save(booking);
    }

    public Booking getBooking(Long id) {
        return bookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    public List<Booking> getBookingsByUser(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    public Booking updateStatus(Long id, BookingStatus status) {
        Booking booking = getBooking(id);
        booking.setStatus(status);
        if (status == BookingStatus.PAID) {
            booking.setPaidAt(LocalDateTime.now());
        }
        return bookingRepository.save(booking);
    }

    public void cancelBooking(Long id) {
        updateStatus(id, BookingStatus.CANCELED);
    }
}
