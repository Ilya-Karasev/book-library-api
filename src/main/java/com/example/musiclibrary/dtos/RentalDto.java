package com.example.musiclibrary.dtos;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.RepresentationModel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
public class RentalDto extends RepresentationModel<RentalDto> {
    private UUID id;
    private UserDto user;
    private BookDto book;
    private LocalDate rental_date;
    private LocalDate due_date;
    private LocalDate return_date;
    private Integer extended_times;
    private Boolean is_returned;
    private LocalDateTime created;
    private LocalDateTime modified;
    public RentalDto(UserDto user, BookDto book, LocalDate rental_date, LocalDate due_date, LocalDate return_date, Integer extended_times, Boolean is_returned) {
        this.user = user;
        this.book = book;
        this.rental_date = rental_date;
        this.due_date = due_date;
        this.return_date = return_date;
        this.extended_times = extended_times;
        this.is_returned = is_returned;
    }
    public RentalDto() {
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
    public LocalDate getRental_date() {
        return rental_date;
    }
    public void setRental_date(LocalDate rental_date) {
        this.rental_date = rental_date;
    }
    @NotNull(message = "Укажите дату сдачи книги!")
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
                "user_id: " + user + ",\n" +
                "book_id: " + user + ",\n" +
                "rental_date: " + rental_date + ",\n" +
                "due_date: " + due_date + ",\n" +
                "return_date: " + return_date + ",\n" +
                "extended_times: " + extended_times + ",\n" +
                "is_returned: " + is_returned + ",\n" +
                "created: " + created + ",\n" +
                "modified: " + modified + ",\n}";
    }
}