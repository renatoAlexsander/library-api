package com.estudo.library.modules.book.controller;

import com.estudo.library.exception.MessageException;
import com.estudo.library.modules.book.dto.BookDto;
import com.estudo.library.modules.book.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/books")
@Api("Book API")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Save a book")
    public BookDto save(@RequestBody @Valid BookDto bookDto) {
        return bookService.save(bookDto);
    }

    @GetMapping("{id}")
    @ApiOperation("Obtains a book details")
    public BookDto getById(@PathVariable Integer id) {
        return bookService.getById(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("delete a book")
    public void deleteById(@PathVariable Integer id) {
        bookService.deleteById(id);
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
