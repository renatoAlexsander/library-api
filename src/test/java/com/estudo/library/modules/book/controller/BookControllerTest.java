package com.estudo.library.modules.book.controller;

import com.estudo.library.modules.book.dto.BookDto;
import com.estudo.library.utils.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc
class BookControllerTest {

    private static final String BOOK_API = "/api/books";

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("should save a new book")
    void saveBookTest() throws Exception {
        var bookDto = BookDto.builder()
            .isbn("000")
            .author("JK Rowling")
            .name("Harry Pother")
            .build();

        mvc.perform(post(BOOK_API)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(TestUtils.convertObjectToJson(bookDto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("id").value(1))
            .andExpect(jsonPath("isbn").value("000"))
            .andExpect(jsonPath("author").value("JK Rowling"))
            .andExpect(jsonPath("name").value("Harry Pother"));
    }
}