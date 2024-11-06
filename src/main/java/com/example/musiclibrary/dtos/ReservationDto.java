package com.example.musiclibrary.dtos;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
public class ReservationDto {
    private UUID id;
    private UserDto user;
    private BookDto book;
    private LocalDate reservation_date;
    private LocalDate expiry_date;
    private Boolean is_active;
    private LocalDateTime created;
    private LocalDateTime modified;
    public ReservationDto(UserDto user, BookDto book, LocalDate reservation_date, LocalDate expiry_date, Boolean is_active) {
        this.user = user;
        this.book = book;
        this.reservation_date = reservation_date;
        this.expiry_date = expiry_date;
        this.is_active = is_active;
    }
    public ReservationDto() {
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public UserDto getUser() {
        return user;
    }
    public void setUser(UserDto user) {
        this.user = user;
    }
    public BookDto getBook() {
        return book;
    }
    public void setBook(BookDto book) {
        this.book = book;
    }
    public LocalDate getReservation_date() {
        return reservation_date;
    }
    public void setReservation_date(LocalDate reservation_date) {
        this.reservation_date = reservation_date;
    }
    public LocalDate getExpiry_date() {
        return expiry_date;
    }
    public void setExpiry_date(LocalDate expiry_date) {
        this.expiry_date = expiry_date;
    }
    public Boolean getIs_active() {
        return is_active;
    }
    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }
    public LocalDateTime getCreated() {
        return created;
    }
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
    public LocalDateTime getModified() {
        return modified;
    }
    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }
    @Override
    public String toString() {
        return "Rental {\n" +
                "rental_id: " + id + ",\n" +
                "user_by: " + user + ",\n" +
                "book_by: " + user + ",\n" +
                "reservation_date: " + reservation_date + ",\n" +
                "expiry_date: " + expiry_date + ",\n" +
                "is_active: " + is_active + ",\n" +
                "created: " + created + ",\n" +
                "modified: " + modified + ",\n}";
    }
}