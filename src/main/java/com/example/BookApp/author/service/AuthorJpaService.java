package com.example.BookApp.author.service;

import com.example.BookApp.author.domain.Author;

import java.util.List;


public interface AuthorJpaService {

    Author saveAuthor(Author author);

    Author findById(Long id);

    Author updateAuthor(Author author);

    List<Author> findAllAuthors();
}
