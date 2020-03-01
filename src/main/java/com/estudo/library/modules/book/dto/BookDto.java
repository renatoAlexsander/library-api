package com.estudo.library.modules.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private Integer id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String author;

    @NotEmpty
    private String isbn;

}
