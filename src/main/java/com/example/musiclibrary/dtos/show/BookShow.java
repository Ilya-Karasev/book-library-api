package com.example.musiclibrary.dtos.show;
import com.example.musiclibrary.dtos.ActionDto;
import org.springframework.hateoas.RepresentationModel;

import java.time.Year;
import java.util.List;

public class BookShow extends RepresentationModel<BookShow> {
    private String title;
    private String author;
    private String publisher;
    private Integer publication_year;
    private String genre;
    private String description;
    private List<ActionDto> actions;
    private UserShow user;
    public BookShow(String title, String author, String publisher, Integer publication_year, String genre, Integer available_copies, Integer total_copies, String description, UserShow user) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publication_year = publication_year;
        this.genre = genre;
        this.description = description;
        this.user = user;
    }
    public BookShow() {
    }
    public UserShow getUser() {
        return user;
    }
    public void setUser(UserShow user) {
        this.user = user;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public Integer getPublication_year() {
        return publication_year;
    }
    public void setPublication_year(Integer publication_year) {
        this.publication_year = publication_year;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<ActionDto> getActions() {
        return actions;
    }
    public void setActions(List<ActionDto> actions) {
        this.actions = actions;
    }
    @Override
    public String toString() {
        return "Book {" +
                "added_by: " + user + ", " +
                "title: " + title + ", " +
                "author: " + author + ", " +
                "publisher: " + publisher + ", " +
                "publication_year: " + publication_year + ", " +
                "genre: " + genre + ", " +
                "description: " + description + "}";
    }
}