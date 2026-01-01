package com.hungnv.tourbooking.service;

import com.hungnv.tourbooking.domain.*;
import com.hungnv.tourbooking.dto.*;
import com.hungnv.tourbooking.repository.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DashboardService {

    private final TourRepository tourRepository;
    private final HotelRepository hotelRepository;
    private final BookingRepository bookingRepository;
    private final OrderRepository orderRepository;

    public DashboardService(
        TourRepository tourRepository,
        HotelRepository hotelRepository,
        BookingRepository bookingRepository,
        OrderRepository orderRepository
    ) {
        this.tourRepository = tourRepository;
        this.hotelRepository = hotelRepository;
        this.bookingRepository = bookingRepository;
        this.orderRepository = orderRepository;
    }

    public DashboardStatisticsDTO getStatistics() {
        DashboardStatisticsDTO stats = new DashboardStatisticsDTO();

        // Total counts
        stats.setTotalTours(tourRepository.count());
        stats.setTotalHotels(hotelRepository.count());
        stats.setTotalBookings(bookingRepository.count());

        // Total revenue from orders
        List<Order> allOrders = orderRepository.findAll();
        Double totalRevenue = allOrders
            .stream()
            .filter(order -> order.getPaymentStatus() == PaymentStatus.PAID)
            .map(order -> order.getTotalPrice() != null ? order.getTotalPrice().doubleValue() : 0.0)
            .reduce(0.0, Double::sum);
        stats.setTotalRevenue(totalRevenue);

        // Tours by season
        Map<Season, Long> toursBySeason = new HashMap<>();
        for (Season season : Season.values()) {
            long count = tourRepository.findAll().stream().filter(tour -> tour.getSeason() == season).count();
            toursBySeason.put(season, count);
        }
        stats.setToursBySeason(toursBySeason);

        // Monthly revenue (last 12 months)
        stats.setMonthlyRevenue(calculateMonthlyRevenue(allOrders));

        // Top tours by booking count
        stats.setTopTours(getTopTours());

        // Top hotels (simple count for now)
        stats.setTopHotels(getTopHotels());

        // Active tour timelines for Gantt chart
        stats.setActiveTourTimelines(getActiveTourTimelines());

        return stats;
    }

    private List<MonthlyRevenueDTO> calculateMonthlyRevenue(List<Order> orders) {
        List<MonthlyRevenueDTO> monthlyRevenue = new ArrayList<>();
        YearMonth currentMonth = YearMonth.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yyyy");

        for (int i = 11; i >= 0; i--) {
            YearMonth month = currentMonth.minusMonths(i);
            String monthStr = month.format(formatter);

            Double revenue = orders
                .stream()
                .filter(
                    order ->
                        order.getOrderDate() != null &&
                        order.getPaymentStatus() == PaymentStatus.PAID &&
                        YearMonth.from(order.getOrderDate()).equals(month)
                )
                .map(order -> order.getTotalPrice() != null ? order.getTotalPrice().doubleValue() : 0.0)
                .reduce(0.0, Double::sum);

            monthlyRevenue.add(new MonthlyRevenueDTO(monthStr, revenue));
        }

        return monthlyRevenue;
    }

    private List<TourStatDTO> getTopTours() {
        List<Order> allOrders = orderRepository.findAll();

        // Group orders by tourId and calculate stats
        Map<Long, TourStatDTO> tourStatsMap = new HashMap<>();

        for (Order order : allOrders) {
            Long tourId = order.getTourId();
            if (tourId != null) {
                TourStatDTO stat = tourStatsMap.getOrDefault(tourId, new TourStatDTO(tourId, "", 0L, 0.0));
                stat.setBookingCount(stat.getBookingCount() + 1);

                if (order.getPaymentStatus() == PaymentStatus.PAID && order.getTotalPrice() != null) {
                    stat.setRevenue(stat.getRevenue() + order.getTotalPrice().doubleValue());
                }

                tourStatsMap.put(tourId, stat);
            }
        }

        // Get tour names
        for (Map.Entry<Long, TourStatDTO> entry : tourStatsMap.entrySet()) {
            tourRepository
                .findById(entry.getKey())
                .ifPresent(tour -> {
                    entry.getValue().setName(tour.getTourName());
                });
        }

        // Sort by booking count and return top 5
        return tourStatsMap
            .values()
            .stream()
            .sorted((a, b) -> Long.compare(b.getBookingCount(), a.getBookingCount()))
            .limit(5)
            .collect(Collectors.toList());
    }

    private List<HotelStatDTO> getTopHotels() {
        List<Hotel> allHotels = hotelRepository.findAll();

        // For now, just return first 5 hotels (can be enhanced with actual booking
        // data)
        return allHotels.stream().limit(5).map(hotel -> new HotelStatDTO(hotel.getId(), hotel.getName(), 0L)).collect(Collectors.toList());
    }

    private List<TourTimelineDTO> getActiveTourTimelines() {
        LocalDate now = LocalDate.now();

        return tourRepository
            .findAll()
            .stream()
            .filter(tour -> tour.getStartDate() != null && tour.getEndDate() != null)
            .filter(tour -> tour.getEndDate().isAfter(now) || tour.getEndDate().isEqual(now))
            .limit(10) // Limit to 10 tours for readability
            .map(tour -> new TourTimelineDTO(tour.getId(), tour.getTourName(), tour.getStartDate(), tour.getEndDate()))
            .sorted(Comparator.comparing(TourTimelineDTO::getStartDate))
            .collect(Collectors.toList());
    }
}
