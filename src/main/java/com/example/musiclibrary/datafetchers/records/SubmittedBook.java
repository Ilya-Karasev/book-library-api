package com.example.musiclibrary.datafetchers.records;

public record SubmittedBook(String title, String author, String publisher, Integer publication_year, String genre, Integer available_copies, Integer total_copies, String description, String user) {
}