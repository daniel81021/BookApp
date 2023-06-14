package com.example.BookApp.author.service.impl;

import com.example.BookApp.author.domain.Author;
import com.example.BookApp.author.domain.AuthorJpa;
import com.example.BookApp.author.mapper.AuthorJpaMapper;
import com.example.BookApp.author.repository.AuthorJpaRepository;
import com.example.BookApp.author.service.AuthorJpaService;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthorJpaServiceImpl implements AuthorJpaService {

    @Autowired
    AuthorJpaRepository authorJpaRepository;

    @Autowired
    AuthorJpaMapper authorJpaMapper;
    @Override
    public Author saveAuthor(Author author) {
        AuthorJpa authorJpa = authorJpaMapper.toAuthorJpa(author);
        AuthorJpa savedAuthor = authorJpaRepository.save(authorJpa);
        return authorJpaMapper.toAuthor(savedAuthor);
    }
}
