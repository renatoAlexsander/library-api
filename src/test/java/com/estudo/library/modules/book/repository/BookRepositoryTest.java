package com.estudo.library.modules.book.repository;

import com.estudo.library.modules.book.model.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("should return true when isbn already exists in database")
    void shouldReturnTrueWhenIsbnExistsInDatabase() {
        entityManager.persist(Book.builder()
            .isbn("333-00")
            .build());

        Assertions.assertThat(bookRepository.existsByIsbn("333-00"))
            .isTrue();
    }

    @Test
    @DisplayName("should return false when isbn not exists in database")
    void shouldReturnFalseWhenIsbnNotExistsInDatabase() {
        Assertions.assertThat(bookRepository.existsByIsbn("333-00"))
            .isFalse();
    }
}