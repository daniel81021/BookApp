package com.example.BookApp.author.service;

import com.example.BookApp.author.domain.Author;
import org.springframework.stereotype.Service;

@Service
public interface AuthorJpaService {

    Author saveAuthor(Author author);
}
