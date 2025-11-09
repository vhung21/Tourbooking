package com.hungnv.tourbooking.repository;

import com.hungnv.tourbooking.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoomRepository extends JpaRepository<Room, Long> {
}
