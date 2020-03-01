package com.estudo.library.modules.book.model;

import com.estudo.library.modules.book.dto.BookDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BOOKS")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String name;
    private String author;
    private String isbn;
    private LocalDateTime registrationDate;

    public static Book of(BookDto bookDto) {
        var book = new Book();
        BeanUtils.copyProperties(bookDto, book);
        book.setId(1);

        return book;
    }
}
