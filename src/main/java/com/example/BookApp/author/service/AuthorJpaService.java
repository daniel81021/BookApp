package com.example.BookApp.author.service;

import com.example.BookApp.author.domain.Author;


public interface AuthorJpaService {

    Author saveAuthor(Author author);
}
