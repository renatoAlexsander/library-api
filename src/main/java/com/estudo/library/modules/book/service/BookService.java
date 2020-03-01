package com.estudo.library.modules.book.service;

import com.estudo.library.exception.BusinessException;
import com.estudo.library.modules.book.dto.BookDto;
import com.estudo.library.modules.book.model.Book;
import com.estudo.library.modules.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book save(BookDto dto) {
        if (bookRepository.existsByIsbn(dto.getIsbn())) {
            throw new BusinessException("ISBN already register.");
        }

        return bookRepository.save(Book.of(dto));
    }
}
