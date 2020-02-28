package com.estudo.library.modules.book.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDto {

    private Integer id;
    private String name;
    private String author;
    private String isbn;

}
