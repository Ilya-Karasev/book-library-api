package com.example.musiclibrary.datafetchers.records;

public record SubmittedReservation(String user, String book, String reservation_date, String expiry_date, Boolean is_active) {
}