package com.example.musiclibrary.datafetchers;

import com.example.musiclibrary.datafetchers.records.SubmittedBook;
import com.example.musiclibrary.dtos.BookDto;
import com.example.musiclibrary.dtos.show.BookShow;
import com.example.musiclibrary.services.BookService;
import com.example.musiclibrary.services.UserService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@DgsComponent
public class BookDataFetcher {
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private ModelMapper modelMapper;

    @DgsQuery
    public BookDto getBook(@InputArgument String title) throws InterruptedException {
        Optional<BookDto> book = bookService.findBookDto(title);
        return book.orElse(null);
    }

    @DgsQuery
    public List<BookShow> getAllBooks() throws InterruptedException {
        return bookService.getAllBooks();
    }

    @DgsMutation
    public BookDto addBook(@InputArgument SubmittedBook input) throws InterruptedException {
        BookDto b = new BookDto();
        b.setTitle(input.title());
        b.setUser(userService.findUserDto(input.user()).orElseThrow());
        b.setPublisher(input.publisher());
        b.setTotal_copies(input.total_copies());
        b.setGenre(input.genre());
        b.setDescription(input.description());
        b.setAuthor(input.author());
        return bookService.addBook(b, b.getUser().getName());
    }

    @DgsMutation
    public BookDto editBook(@InputArgument String title, @InputArgument SubmittedBook input) throws InterruptedException {
        BookDto b = modelMapper.map(bookService.findBook(title), BookDto.class);
        if (input.title() != null && !input.title().isEmpty()) {
            b.setTitle(input.title());
        }
        if (input.publisher() != null && !input.publisher().isEmpty()) {
            b.setPublisher(input.publisher());
        }
        if (input.total_copies() != null) {
            b.setTotal_copies(input.total_copies());
        }
        if (input.genre() != null && !input.genre().isEmpty()) {
            b.setGenre(input.genre());
        }
        if (input.description() != null && !input.description().isEmpty()) {
            b.setDescription(input.description());
        }
        if (input.author() != null && !input.author().isEmpty()) {
            b.setAuthor(input.author());
        }
        return bookService.editBook(title, b).orElse(null);
    }

    @DgsMutation
    public String deleteBook(@InputArgument String title) throws InterruptedException {
        bookService.deleteBook(title);
        return "Книга '" + title + "' удалена";
    }
}