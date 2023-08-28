package com.example.musiclibrary.controllers;
class NotFoundException extends RuntimeException {
    NotFoundException(String obj) {
        super("Could not find " + obj);
    }
}