package com.example.musiclibrary.datafetchers.records;

public record SubmittedRental(String rental_date, String due_date, String return_date, Integer extended_times, Boolean is_returned, String user, String book) {
}