package com.example.musiclibrary.exceptions;

public record StatusResponse(
        String status,
        String message
) {}