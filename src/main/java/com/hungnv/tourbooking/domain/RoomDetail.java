package com.hungnv.tourbooking.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "room_detail")
public class RoomDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String roomDetailTitle;

    @Column()
    private String roomDetailDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    @JsonBackReference
    private Room room;

    public RoomDetail() {
    }

    public RoomDetail(Long id, String roomDetailDescription, String roomDetailTitle, Room room) {
        this.id = id;
        this.roomDetailDescription = roomDetailDescription;
        this.roomDetailTitle = roomDetailTitle;
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomDetailTitle() {
        return roomDetailTitle;
    }

    public void setRoomDetailTitle(String roomDetailTitle) {
        this.roomDetailTitle = roomDetailTitle;
    }

    public String getRoomDetailDescription() {
        return roomDetailDescription;
    }

    public void setRoomDetailDescription(String roomDetailDescription) {
        this.roomDetailDescription = roomDetailDescription;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "RoomDetail{" +
            "id=" + id +
            ", roomDetailTitle='" + roomDetailTitle + '\'' +
            ", roomDetailDescription='" + roomDetailDescription + '\'' +
            ", room=" + room +
            '}';
    }
}
