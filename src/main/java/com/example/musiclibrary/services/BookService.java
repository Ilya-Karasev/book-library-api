package com.example.musiclibrary.services;

import com.example.musiclibrary.dtos.BookDto;
import com.example.musiclibrary.dtos.show.BookShow;
import java.util.List;
import java.util.Optional;

public interface BookService {
    BookDto addBook(BookDto book, String user) throws InterruptedException;
    Optional<BookShow> findBook(String title) throws InterruptedException;
    Optional<BookDto> findBookDto(String title) throws InterruptedException;
    List<BookShow> getAllBooks() throws InterruptedException;
    Optional <BookDto> editBook(String title, BookDto book) throws InterruptedException;
    void deleteBook(String title) throws InterruptedException;
}
