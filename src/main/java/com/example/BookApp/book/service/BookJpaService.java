package com.example.BookApp.book.service;

import com.example.BookApp.book.domain.Book;

public interface BookJpaService {

    Book save(Book book);

    Book findById(Long id);
}
