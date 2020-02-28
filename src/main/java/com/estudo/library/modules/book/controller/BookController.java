package com.estudo.library.modules.book.controller;

import com.estudo.library.modules.book.dto.BookDto;
import com.estudo.library.modules.book.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/books")
public class BookController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book save(@RequestBody BookDto bookDto) {
        return Book.of(bookDto);
    }
}
