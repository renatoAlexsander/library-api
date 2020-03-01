package com.estudo.library.modules.book.controller;

import com.estudo.library.exception.MessageException;
import com.estudo.library.modules.book.dto.BookDto;
import com.estudo.library.modules.book.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/books")
public class BookController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book save(@RequestBody @Valid BookDto bookDto) {
        return Book.of(bookDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageException methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        var errors = exception.getBindingResult().getAllErrors().stream()
            .map(ObjectError::getDefaultMessage)
            .collect(Collectors.toList());

        return new MessageException(errors);
    }
}
