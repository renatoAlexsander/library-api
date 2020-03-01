package com.estudo.library.modules.book.controller;

import com.estudo.library.exception.NotFoundException;
import com.estudo.library.modules.book.dto.BookDto;
import com.estudo.library.modules.book.model.Book;
import com.estudo.library.modules.book.service.BookService;
import com.estudo.library.utils.TestUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc
class BookControllerTest {

    private static final String BOOK_API = "/api/books";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @Test
    @DisplayName("should save a new book")
    void saveBookTest() throws Exception {
        var bookDto = createValidBook();

        BDDMockito.given(bookService.save(bookDto))
            .willReturn(BookDto.builder()
                .id(1)
                .isbn(bookDto.getIsbn())
                .author(bookDto.getAuthor())
                .name(bookDto.getName())
                .build());

        mvc.perform(post(BOOK_API)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(TestUtils.convertObjectToJson(bookDto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("id").value(1))
            .andExpect(jsonPath("isbn").value(bookDto.getIsbn()))
            .andExpect(jsonPath("author").value(bookDto.getAuthor()))
            .andExpect(jsonPath("name").value(bookDto.getName()));
    }

    @Test
    @DisplayName("should validate empty dto to save")
    void shouldValidateEmptyDto() throws Exception {
        mvc.perform(post(BOOK_API)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(TestUtils.convertObjectToJson(new BookDto())))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("errors", Matchers.hasSize(3)));

        verify(bookService, never()).save(any(BookDto.class));
    }

    @Test
    @DisplayName("should get a book by id")
    void getById() throws Exception {
        BDDMockito.given(bookService.getById(1))
            .willReturn(BookDto.builder()
                .id(1)
                .isbn(createValidBook().getIsbn())
                .author(createValidBook().getAuthor())
                .name(createValidBook().getName())
                .build());

        mvc.perform(get(BOOK_API.concat("/" + 1))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").value(1))
            .andExpect(jsonPath("isbn").value(createValidBook().getIsbn()))
            .andExpect(jsonPath("author").value(createValidBook().getAuthor()))
            .andExpect(jsonPath("name").value(createValidBook().getName()));
    }

    @Test
    @DisplayName("should delete a book by id")
    void deleteById() throws Exception {
        mvc.perform(delete(BOOK_API.concat("/" + 1))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        verify(bookService).deleteById(1);
    }

    private BookDto createValidBook() {
        return BookDto.builder()
            .isbn("000")
            .author("JK Rowling")
            .name("Harry Pother")
            .build();
    }
}