package com.example.musiclibrary.dtos;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
public class BookDto {
    private UUID id;
    private UserDto user;
    private String title;
    private String author;
    private String publisher;
    private Integer publication_year;
    private String genre;
    private Integer available_copies;
    private Integer total_copies;
    private String description;
    private LocalDateTime created;
    private LocalDateTime modified;
    public BookDto(UserDto user, String title, String author, String publisher, Integer publication_year, String genre, Integer available_copies, Integer total_copies, String description) {
        this.user = user;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publication_year = publication_year;
        this.genre = genre;
        this.available_copies = available_copies;
        this.total_copies = total_copies;
        this.description = description;
    }
    public BookDto() {
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
    @NotNull(message = "Введите название книги!")
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    @NotNull(message = "Введите автора книги!")
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    @NotNull(message = "Введите издателя книги!")
    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    @NotNull(message = "Введите год издания книги!")
    public Integer getPublication_year() {
        return publication_year;
    }
    public void setPublication_year(Integer publication_year) {
        this.publication_year = publication_year;
    }
    @NotNull(message = "Введите жанр книги!")
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    @NotNull(message = "Введите количество доступных экземпляров!")
    public Integer getAvailable_copies() {
        return available_copies;
    }
    public void setAvailable_copies(Integer available_copies) {
        this.available_copies = available_copies;
    }
    @NotNull(message = "Введите общее количество экземпляров!")
    public Integer getTotal_copies() {
        return total_copies;
    }
    public void setTotal_copies(Integer total_copies) {
        this.total_copies = total_copies;
    }
    @NotNull(message = "Введите описание для книги!")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
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
        return "Book {" +
                "book_id: " + id + ", " +
                "added_by: " + user + ", " +
                "title: " + title + ", " +
                "author: " + author + ", " +
                "publisher: " + publisher + ", " +
                "publication_year: " + publication_year + ", " +
                "genre: " + genre + ", " +
                "available_copies: " + available_copies + ", " +
                "total_copies: " + total_copies + ", " +
                "description: " + description + ", " +
                "created: " + created + ", " +
                "modified: " + modified + "}";
    }
}