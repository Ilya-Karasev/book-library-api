package com.example.musiclibrary.dtos.show;
import com.example.musiclibrary.dtos.ActionDto;
import com.example.musiclibrary.dtos.BookDto;
import com.example.musiclibrary.dtos.UserDto;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

public class RentalShow extends RepresentationModel<RentalShow> {
    private UUID id;
    private LocalDate rental_date;
    private LocalDate due_date;
    private LocalDate return_date;
    private Integer extended_times;
    private Boolean is_returned;
    private List<ActionDto> actions;
    private UserShow user;
    private BookShow book;
    public RentalShow(UUID id, LocalDate rental_date, LocalDate due_date, LocalDate return_date, Integer extended_times, Boolean is_returned, UserShow user, BookShow book) {
        this.id = id;
        this.rental_date = rental_date;
        this.due_date = due_date;
        this.return_date = return_date;
        this.extended_times = extended_times;
        this.is_returned = is_returned;
        this.user = user;
        this.book = book;
    }
    public RentalShow() {}
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
    public LocalDate getRental_date() {
        return rental_date;
    }
    public void setRental_date(LocalDate rental_date) {
        this.rental_date = rental_date;
    }
    public LocalDate getDue_date() {
        return due_date;
    }
    public void setDue_date(LocalDate due_date) {
        this.due_date = due_date;
    }
    public LocalDate getReturn_date() {
        return return_date;
    }
    public void setReturn_date(LocalDate return_date) {
        this.return_date = return_date;
    }
    public Integer getExtended_times() {
        return extended_times;
    }
    public void setExtended_times(Integer extended_times) {
        this.extended_times = extended_times;
    }
    public Boolean getIs_returned() {
        return is_returned;
    }
    public void setIs_returned(Boolean is_returned) {
        this.is_returned = is_returned;
    }
    @Override
    public String toString() {
        return "Rental {\n" +
                "user_id: " + user + ",\n" +
                "book_id: " + book + ",\n" +
                "rental_date: " + rental_date + ",\n" +
                "due_date: " + due_date + ",\n" +
                "return_date: " + return_date + ",\n" +
                "extended_times: " + extended_times + ",\n" +
                "is_returned: " + is_returned + ",\n}";
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