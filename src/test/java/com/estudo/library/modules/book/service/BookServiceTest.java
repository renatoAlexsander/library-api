package com.estudo.library.modules.book.service;

import com.estudo.library.exception.BusinessException;
import com.estudo.library.exception.NotFoundException;
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
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;
    @Mock
    private ModelMapper modelMapper;

    @Test
    @DisplayName("should save a new book")
    void saveNewBook() {
        when(bookRepository.existsByIsbn("333-00"))
            .thenReturn(false);

        when(bookRepository.save(Mockito.any(Book.class)))
            .thenReturn(bookSalved());

        when(modelMapper.map(bookSalved(), BookDto.class))
            .thenReturn(BookDto.builder()
                .id(bookSalved().getId())
                .author(bookSalved().getAuthor())
                .isbn(bookSalved().getIsbn())
                .name(bookSalved().getName())
                .build());

        assertThat(bookService.save(bookDto()))
            .extracting("id", "author", "isbn", "name")
            .containsExactly(1, "Patrick Rothfuss", "333-00", "The name of the wind");
    }

    @Test
    @DisplayName("should throw business exception when to save a book with duplicated isbn")
    void throwBusinessExceptionWhenDuplicatedIsbn() {
        when(bookRepository.existsByIsbn("333-00"))
            .thenReturn(true);

        var exception = catchThrowable(() -> bookService.save(bookDto()));
        assertThat(exception)
            .isInstanceOf(BusinessException.class)
            .hasMessage("ISBN already register.");

        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    @DisplayName("should return a book by id")
    void getById() {
        when(bookRepository.findById(1))
            .thenReturn(Optional.of(bookSalved()));

        when(modelMapper.map(bookSalved(), BookDto.class))
            .thenReturn(bookDto());

        assertThat(bookService.getById(1))
            .isEqualTo(bookDto());
    }

    @Test
    @DisplayName("should throw NotFoundException when id doesn't exists")
    void shouldThrowExceptionWhenIdDoesnotExists() {
        when(bookRepository.findById(1))
            .thenReturn(Optional.empty());

        var exception = catchThrowable(() -> bookService.getById(1));
        assertThat(exception)
            .isInstanceOf(NotFoundException.class)
            .hasMessage("Book not found.");
    }

    @Test
    @DisplayName("should delete a book by id")
    void deleteByIdWhenBookExists() {
        when(bookRepository.findById(1))
            .thenReturn(Optional.of(bookSalved()));

        assertThatCode(() -> bookService.deleteById(1))
            .doesNotThrowAnyException();

        verify(bookRepository).delete(bookSalved());
    }

    @Test
    @DisplayName("should throw NotFoundException when book to delete not exists")
    void deleteByIdWhenBookNotExists() {
        when(bookRepository.findById(1))
            .thenReturn(Optional.empty());

        var exception = catchThrowable(() -> bookService.deleteById(1));
        assertThat(exception)
            .isInstanceOf(NotFoundException.class)
            .hasMessage("Book not found.");

        verify(bookRepository, never()).delete(any(Book.class));
    }

    private BookDto bookDto() {
        return BookDto.builder()
            .author("Patrick Rothfuss")
            .isbn("333-00")
            .name("The name of the wind")
            .build();
    }

    private Book bookSalved() {
        return Book.builder()
            .id(1)
            .author(bookDto().getAuthor())
            .isbn(bookDto().getIsbn())
            .name(bookDto().getName())
            .build();
    }
}