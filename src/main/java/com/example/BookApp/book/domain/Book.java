package com.example.BookApp.book.domain;

import com.example.BookApp.author.domain.Author;
import com.example.BookApp.book.enums.CoverType;
import com.example.BookApp.common.Audit;
import com.example.BookApp.publisher.domain.Publisher;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
public class Book {

    private Long id;

    private String title;

    private Set<Author> authors;

    private Publisher publisher;

    private String city;

    private String publicationYear;

    private Integer publicationNumber;

    private String isbn;

    private CoverType cover;

    private Integer size;

    private Audit audit;

    private Long version;
}
