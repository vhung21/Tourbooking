package com.hungnv.tourbooking.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String roomType; // ví dụ: Standard, Deluxe, Suite

    @Column(nullable = false)
    private Double price;

    @Column(length = 500)
    private String description;

    @Column(length = 300)
    private String imageUrl; // nếu bạn chỉ dùng 1 ảnh đại diện

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    @JsonBackReference
    private Hotel hotel;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<RoomDetail> roomDetails;

    public Room() {
    }

    public Room(Long id, String roomType, Double price, String description, String imageUrl, Hotel hotel) {
        this.id = id;
        this.roomType = roomType;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.hotel = hotel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public List<RoomDetail> getRoomDetails() {
        return roomDetails;
    }

    public void setRoomDetails(List<RoomDetail> roomDetails) {
        this.roomDetails = roomDetails;
        if (roomDetails != null) {
            roomDetails.forEach(detail -> detail.setRoom(this));
        }
    }

    @Override
    public String toString() {
        return "Room{" +
            "id=" + id +
            ", roomType='" + roomType + '\'' +
            ", price=" + price +
            ", description='" + description + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", hotel=" + hotel +
            '}';
    }
}
