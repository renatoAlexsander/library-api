package com.estudo.library.modules.book.service;

import com.estudo.library.exception.BusinessException;
import com.estudo.library.modules.book.dto.BookDto;
import com.estudo.library.modules.book.model.Book;
import com.estudo.library.modules.book.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Test
    @DisplayName("should save a new book")
    void saveNewBook() {
        when(bookRepository.existsByIsbn("333-00"))
            .thenReturn(false);

        when(bookRepository.save(Mockito.any(Book.class)))
            .thenReturn(Book.builder()
                .id(1)
                .author("Patrick Rothfuss")
                .isbn("333-00")
                .name("The name of the wind")
                .build());

        assertThat(bookService.save(createBook()))
            .extracting("id", "author", "isbn", "name")
            .containsExactly(1, "Patrick Rothfuss", "333-00", "The name of the wind");
    }

    @Test
    @DisplayName("should throw business exception when to save a book with duplicated isbn")
    void throwBusinessExceptionWhenDuplicatedIsbn() {
        when(bookRepository.existsByIsbn("333-00"))
            .thenReturn(true);

        var exception = catchThrowable(() -> bookService.save(createBook()));
        assertThat(exception)
            .isInstanceOf(BusinessException.class)
            .hasMessage("ISBN already register.");

        verify(bookRepository, never()).save(any(Book.class));
    }

    private BookDto createBook() {
        return BookDto.builder()
            .author("Patrick Rothfuss")
            .isbn("333-00")
            .name("The name of the wind")
            .build();
    }
}