package com.estudo.library.modules.book.repository;

import com.estudo.library.modules.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {

    boolean existsByIsbn(String isbn);

}
