package com.estudo.library.modules.book.service;

import com.estudo.library.exception.BusinessException;
import com.estudo.library.exception.NotFoundException;
import com.estudo.library.modules.book.dto.BookDto;
import com.estudo.library.modules.book.model.Book;
import com.estudo.library.modules.book.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    public BookDto save(BookDto dto) {
        if (bookRepository.existsByIsbn(dto.getIsbn())) {
            throw new BusinessException("ISBN already register.");
        }

        return modelMapper.map(bookRepository.save(Book.of(dto)), BookDto.class);
    }

    public BookDto getById(Integer id) {
        return bookRepository.findById(id)
            .map(book -> modelMapper.map(book, BookDto.class))
            .orElseThrow(() -> new NotFoundException("Book not found."));
    }

    public void deleteById(Integer id) {
        var book = findById(id);
        bookRepository.delete(book);
    }

    private Book findById(Integer id) {
        return bookRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Book not found."));
    }
}
