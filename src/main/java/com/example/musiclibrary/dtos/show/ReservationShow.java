package com.example.musiclibrary.dtos.show;
import com.example.musiclibrary.dtos.ActionDto;
import org.springframework.hateoas.RepresentationModel;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
public class ReservationShow extends RepresentationModel<ReservationShow> {
    private UUID id;
    private LocalDate reservation_date;
    private LocalDate expiry_date;
    private Boolean is_active;
    private List<ActionDto> actions;
    private UserShow user;
    private BookShow book;
    public ReservationShow(UUID id, LocalDate reservation_date, LocalDate expiry_date, Boolean is_active, UserShow user, BookShow book) {
        this.id = id;
        this.reservation_date = reservation_date;
        this.expiry_date = expiry_date;
        this.is_active = is_active;
        this.user = user;
        this.book = book;
    }
    public ReservationShow() {
    }
    public UserShow getUser() {
        return user;
    }
    public void setUser(UserShow user) {
        this.user = user;
    }
    public BookShow getBook() {
        return book;
    }
    public void setBook(BookShow book) {
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
    @Override
    public String toString() {
        return "Rental {\n" +
                "user_id: " + user + ",\n" +
                "book_id: " + book + ",\n" +
                "reservation_date: " + reservation_date + ",\n" +
                "expiry_date: " + expiry_date + ",\n" +
                "is_active: " + is_active + ",\n}";
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<ActionDto> getActions() {
        return actions;
    }

    public void setActions(List<ActionDto> actions) {
        this.actions = actions;
    }
}