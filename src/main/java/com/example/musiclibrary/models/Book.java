package com.example.musiclibrary.models;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import java.util.*;
@Entity
@Table(name = "books")
public class Book extends BaseEntity {
    private String title;
    private String author;
    private String publisher;
    private Integer publication_year;
    private String genre;
    private Integer available_copies;
    private Integer total_copies;
    private String description;
    @ManyToOne(optional = false)
    @JoinColumn(name = "added_by", referencedColumnName = "id", nullable=false)
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    private User user;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    @Cascade({org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.REMOVE})
    private Set<Rental> rental;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    @Cascade({org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.REMOVE})
    private Set<Reservation> reservation;
    public Book(String title, String author, String publisher, Integer publication_year, String genre, Integer available_copies, Integer total_copies, String description) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publication_year = publication_year;
        this.genre = genre;
        this.available_copies = available_copies;
        this.total_copies = total_copies;
        this.description = description;
    }
    protected Book(){
    }
    @Column(name = "Title", nullable = false)
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    @Column(name = "Author", nullable = false)
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    @Column(name = "Publisher", nullable = false)
    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    @Column(name = "Publication_Year", nullable = false)
    public Integer getPublication_year() {
        return publication_year;
    }
    public void setPublication_year(Integer publication_year) {
        this.publication_year = publication_year;
    }
    @Column(name = "Genre", nullable = false)
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    @Column(name = "Available Copies", nullable = false)
    public Integer getAvailable_copies() {
        return available_copies;
    }
    public void setAvailable_copies(Integer available_copies) {
        this.available_copies = available_copies;
    }
    @Column(name = "Total Copies", nullable = false)
    public Integer getTotal_copies() {
        return total_copies;
    }
    public void setTotal_copies(Integer total_copies) {
        this.total_copies = total_copies;
    }
    @Column(name = "Description", nullable = false)
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}