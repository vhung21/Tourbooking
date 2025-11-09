package com.hungnv.tourbooking.service;

import com.hungnv.tourbooking.domain.Hotel;
import com.hungnv.tourbooking.domain.Room;
import com.hungnv.tourbooking.domain.RoomDetail;
import com.hungnv.tourbooking.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public Hotel createHotel(Hotel hotel) {
        if (hotel.getRooms() != null) {
            for (Room room : hotel.getRooms()) {
                room.setHotel(hotel);
                if (room.getRoomDetails() != null) {
                    for (RoomDetail detail : room.getRoomDetails()) {
                        detail.setRoom(room);
                    }
                }
            }
        }
        return hotelRepository.save(hotel);
    }

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Optional<Hotel> getHotelById(Long id) {
        return hotelRepository.findById(id);
    }

    public Hotel updateHotel(Long id, Hotel hotelDetails) {
        Hotel existingHotel = hotelRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Hotel not found"));

        existingHotel.setName(hotelDetails.getName());
        existingHotel.setAddress(hotelDetails.getAddress());
        existingHotel.setCity(hotelDetails.getCity());
        existingHotel.setRating(hotelDetails.getRating());
        existingHotel.setImageUrl(hotelDetails.getImageUrl());
        existingHotel.setPhone(hotelDetails.getPhone());
        existingHotel.setDescription(hotelDetails.getDescription());
        existingHotel.setRooms(hotelDetails.getRooms());

        if (hotelDetails.getRooms() != null) {
            existingHotel.setRooms(hotelDetails.getRooms());
            for (Room room : existingHotel.getRooms()) {
                room.setHotel(existingHotel);
                if (room.getRoomDetails() != null) {
                    for (RoomDetail detail : room.getRoomDetails()) {
                        detail.setRoom(room);
                    }
                }
            }
        }

        return hotelRepository.save(existingHotel);
    }

    public void deleteHotel(Long id) {
        if (!hotelRepository.existsById(id)) {
            throw new RuntimeException("Hotel not found");
        }
        hotelRepository.deleteById(id);
    }
}
